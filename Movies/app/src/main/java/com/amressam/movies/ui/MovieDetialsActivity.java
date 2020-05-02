package com.amressam.movies;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.amressam.movies.adapters.SectionsPagerAdapter;
import com.amressam.movies.database.CastContract;
import com.amressam.movies.database.MoviesContract;
import com.amressam.movies.database.ReviewsContract;
import com.amressam.movies.database.TrailersContract;
import com.amressam.movies.databinding.ActivityDetailsBinding;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

public class MovieDetialsActivity extends AppCompatActivity{

    ActivityDetailsBinding mActivityDetailsBinding;
    public static ArrayList<Cast> cast = null;
    public static ArrayList<Trailer> trailers = null;
    public static ArrayList<Reviews> reviews = null;
    private static final String TAG = "MovieDetialsActivity";
    public static String selectedReview;
    boolean favourite;
    ContentResolver contentResolver;
    SectionsPagerAdapter sectionsPagerAdapter;
    public static final String CAST_STATE = "cast";
    public static final String TRAILERS_STATE = "trailers";
    public static final String REVIEWS_STATE = "reviews";
    public static final String FAVOURITE_STATE = "favourite";

    public static String[] CAST_PROJECTION = {CastContract.Columns.MOVIE_TITLE,
            CastContract.Columns.CAST_NAME,
            CastContract.Columns.CAST_IMAGE};
    public static final int INDEX_MOVIE_TITLE = 0;
    public static final int INDEX_CAST_NAME = 1;
    public static final int INDEX_CAST_IMAGE = 2;

    public static String[] TRAILERS_PROJECTION = {TrailersContract.Columns.MOVIE_TITLE,
            TrailersContract.Columns.TRAILER_TITLE,
            TrailersContract.Columns.TRAILER_ID,
            TrailersContract.Columns.TRAILER_IMAGE};
    public static final int INDEX_TRAILER_NAME = 1;
    public static final int INDEX_TRAILER_ID = 2;
    public static final int INDEX_TRAILER_IMAGE = 3;

    public static String[] REVIEWS_PROJECTION = {ReviewsContract.Columns.MOVIE_TITLE,
            ReviewsContract.Columns.REVIEW_AUTHOR,
            ReviewsContract.Columns.REVIEW_TEXT};
    public static final int INDEX_REVIEW_AUTHOR = 1;
    public static final int INDEX_REVIEW_TEXT = 2;
    public static Movie movieItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_details);

        setIntent();

        setToolbar();
        toolbarTitleBehaviour();

        setViewPagerAdapter();

        contentResolver = getContentResolver();

        mActivityDetailsBinding.movieDetailsFav.setOnClickListener(fabOnClickListener);

        if(savedInstanceState==null){
            setFavourite();
            new queryData().execute();
        } else {
            cast = (ArrayList<Cast>) savedInstanceState.getSerializable(CAST_STATE);
            trailers = (ArrayList<Trailer>) savedInstanceState.getSerializable(TRAILERS_STATE);
            reviews = (ArrayList<Reviews>) savedInstanceState.getSerializable(REVIEWS_STATE);
            favourite = savedInstanceState.getBoolean(FAVOURITE_STATE);
        }


    }

    View.OnClickListener fabOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ContentValues values = new ContentValues();
            if (favourite) {
                mActivityDetailsBinding.movieDetailsFav.setImageResource(R.drawable.ic_star_border);
                favourite = false;
                values.put(MoviesContract.Columns.MOVIE_FAVOURITE, "un_fav");
                int count = contentResolver.update(MoviesContract.buildMovieUri(movieItem.getMovie_Id()), values, null, null);
                Snackbar.make(v, "Movie deleted from favourites", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } else {
                mActivityDetailsBinding.movieDetailsFav.setImageResource(R.drawable.ic_star);
                favourite = true;
                values.put(MoviesContract.Columns.MOVIE_FAVOURITE, "fav");
                int count = contentResolver.update(MoviesContract.buildMovieUri(movieItem.getMovie_Id()), values, null, null);
                Snackbar.make(v, "Movie added to favourites", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }
    };

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(CAST_STATE,(Serializable) cast);
        outState.putSerializable(TRAILERS_STATE,(Serializable) trailers);
        outState.putSerializable(REVIEWS_STATE,(Serializable) reviews);
        outState.putBoolean(FAVOURITE_STATE,favourite);
        super.onSaveInstanceState(outState);
    }

    private void setViewPagerAdapter(){
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mActivityDetailsBinding.movieDetailsViewpager.setAdapter(sectionsPagerAdapter);
        mActivityDetailsBinding.movieDetailsTabs.setupWithViewPager(mActivityDetailsBinding.movieDetailsViewpager);
    }

    private void setIntent(){
        Intent intent = getIntent();
        movieItem = (Movie) intent.getSerializableExtra("NIDO");
        loadMovieDetails(movieItem);
    }

    private void setFavourite(){
        String[] projection = {MoviesContract.Columns.MOVIE_FAVOURITE};
        String selection = MoviesContract.Columns.MOVIE_TITLE + " = ?";
        String[] args = {movieItem.getTitle()};
        Cursor cursor = contentResolver.query(MoviesContract.CONTENT_URI,
                projection,
                selection,
                args,
                null);
        if (cursor != null) {
            cursor.moveToFirst();
            if (cursor.getString(0).equals("fav")) {
                favourite = true;
                mActivityDetailsBinding.movieDetailsFav.setImageResource(R.drawable.ic_star);
            } else if (cursor.getString(0).equals("un_fav")) {
                favourite = false;
            }
        } else {
            favourite = false;
        }
    }

    private void setToolbar() {
        setSupportActionBar(mActivityDetailsBinding.movieDetailsToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void toolbarTitleBehaviour() {
        mActivityDetailsBinding.movieDetailsAppbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (Math.abs(verticalOffset) - appBarLayout.getTotalScrollRange() == 0) {
                    mActivityDetailsBinding.movieDetailsFav.hide();
                    MovieDetialsActivity.this.getSupportActionBar().setDisplayShowTitleEnabled(true);
                    getSupportActionBar().setTitle(movieItem.getTitle());
                } else {
                    mActivityDetailsBinding.movieDetailsFav.show();
                    MovieDetialsActivity.this.getSupportActionBar().setDisplayShowTitleEnabled(false);
                    getSupportActionBar().setTitle("");
                }
            }
        });
        mActivityDetailsBinding.movieDetailsCollapsingToolbar.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
    }

    private void loadMovieDetails(Movie movieItem) {
        String image = movieItem.getImage();

        Picasso.get().load("http://image.tmdb.org/t/p/w500/" + image)
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(mActivityDetailsBinding.movieDetailsPosterPath);

        image = movieItem.getPosterImage();
        Picasso.get().load("http://image.tmdb.org/t/p/w500/" + image)
                .error(R.drawable.placeholder)
                .placeholder(R.drawable.placeholder)
                .into(mActivityDetailsBinding.movieDetailBackdrop);
        mActivityDetailsBinding.detailsMovieTitle.setText(movieItem.getTitle());
    }



    public class queryData extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            String selection = TrailersContract.Columns.MOVIE_TITLE + " = ?";
            String[] args = {movieItem.getTitle()};
            Cursor cursor = contentResolver.query(CastContract.CONTENT_URI
                    , MovieDetialsActivity.CAST_PROJECTION
                    , selection
                    , args
                    , null);
            if (cursor != null) {
                cast = converted_cast_cursor(cursor);

            }
            cursor = contentResolver.query(TrailersContract.CONTENT_URI
                    , TRAILERS_PROJECTION
                    , selection
                    , args
                    , null);
            if (cursor != null) {
                trailers = converted_trailer_cursor(cursor);

            }
            cursor = contentResolver.query(ReviewsContract.CONTENT_URI
                    , MovieDetialsActivity.REVIEWS_PROJECTION
                    , selection
                    , args
                    , null);
            if (cursor != null) {
                reviews = converted_review_cursor(cursor);

            }

            return null;
        }
    }

    private ArrayList<Cast> converted_cast_cursor(Cursor cursor) {
        ArrayList<Cast> convertedCast = new ArrayList<Cast>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Cast cast = new Cast(cursor.getString(MovieDetialsActivity.INDEX_CAST_NAME)
                    , cursor.getString(MovieDetialsActivity.INDEX_CAST_IMAGE));
            convertedCast.add(cast);
            cursor.moveToNext();
        }
        cursor.close();
        return convertedCast;
    }

    private ArrayList<Reviews> converted_review_cursor(Cursor cursor) {
        ArrayList<Reviews> convertedReviews = new ArrayList<Reviews>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Reviews reviews = new Reviews(cursor.getString(MovieDetialsActivity.INDEX_REVIEW_AUTHOR)
                    , cursor.getString(MovieDetialsActivity.INDEX_REVIEW_TEXT));
            convertedReviews.add(reviews);
            cursor.moveToNext();
        }
        cursor.close();
        return convertedReviews;
    }

    private ArrayList<Trailer> converted_trailer_cursor(Cursor cursor) {
        ArrayList<Trailer> convertedTrailers = new ArrayList<Trailer>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Trailer trailer = new Trailer(cursor.getString(MovieDetialsActivity.INDEX_TRAILER_NAME)
                    , cursor.getString(MovieDetialsActivity.INDEX_TRAILER_IMAGE)
                    , cursor.getString(MovieDetialsActivity.INDEX_TRAILER_ID));
            convertedTrailers.add(trailer);
            cursor.moveToNext();
        }
        cursor.close();
        return convertedTrailers;
    }

}
