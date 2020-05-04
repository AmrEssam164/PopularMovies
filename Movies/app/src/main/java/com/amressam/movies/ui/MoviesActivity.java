package com.amressam.movies;

import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.amressam.movies.database.MoviesContract;
import com.amressam.movies.databinding.ActivityMoviesBinding;
import com.amressam.movies.sync.AppSyncUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MoviesActivity extends AppCompatActivity {


    public static Resources mResource;
    private static final String TAG = "MainActivity";
    ActivityMoviesBinding mActivityMoviesBinding;
    private com.amressam.movies.MoviesRecyclerViewAdapter mMoviesRecyclerViewAdapter;
    private ArrayList<com.amressam.movies.Movie> movies;
    public static final String[] PROJECTION = {MoviesContract.Columns.MOVIE_ID,
            MoviesContract.Columns.MOVIE_TITLE,
            MoviesContract.Columns.MOVIE_IMAGE,
            MoviesContract.Columns.MOVIE_POSTER_IMAGE,
            MoviesContract.Columns.MOVIE_RELEASEDATE,
            MoviesContract.Columns.MOVIE_RATE,
            MoviesContract.Columns.MOVIE_VOTECOUNT,
            MoviesContract.Columns.MOVIE_OVERVIEW,
            MoviesContract.Columns.MOVIE_CATEGORY};
    public static final int INDEX_MOVIE_ID = 0;
    public static final int INDEX_MOVIE_TITLE = 1;
    public static final int INDEX_MOVIE_IMAGE = 2;
    public static final int INDEX_MOVIE_POSTER_IMAGE=3;
    public static final int INDEX_MOVIE_REALESEDATE = 4;
    public static final int INDEX_MOVIE_RATE = 5;
    public static final int INDEX_MOVIE_VOTECOUNT = 6;
    public static final int INDEX_MOVIE_OVERVIEW = 7;
    public static final int INDEX_MOVIE_CATEGORY = 8;
    public Uri movieQueryUri = MoviesContract.CONTENT_URI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMoviesBinding = DataBindingUtil.setContentView(this, R.layout.activity_movies);

        mResource = getResources();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.colorBluet)));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_view);


        mMoviesRecyclerViewAdapter = new com.amressam.movies.MoviesRecyclerViewAdapter(new ArrayList<com.amressam.movies.Movie>(), this);

//        mActivityMoviesBinding.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mActivityMoviesBinding.recyclerView.setAdapter(mMoviesRecyclerViewAdapter);

        mActivityMoviesBinding.navigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            new queryData().execute();
        } else {
            movies = (ArrayList<com.amressam.movies.Movie>) savedInstanceState.getSerializable("NIDO");
            mMoviesRecyclerViewAdapter.loadNewData(movies);
        }

        AppSyncUtils.initialize(this);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("NIDO", movies);
        super.onSaveInstanceState(outState);
    }

    private ArrayList<com.amressam.movies.Movie> converted_movies_cursor(Cursor cursor) {
        ArrayList<com.amressam.movies.Movie> convertedMovies = new ArrayList<com.amressam.movies.Movie>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            com.amressam.movies.Movie movie = new com.amressam.movies.Movie(cursor.getInt(INDEX_MOVIE_ID)
                    , cursor.getString(INDEX_MOVIE_TITLE)
                    , cursor.getString(INDEX_MOVIE_IMAGE)
                    , cursor.getString(INDEX_MOVIE_POSTER_IMAGE)
                    , cursor.getString(INDEX_MOVIE_REALESEDATE)
                    , cursor.getInt(INDEX_MOVIE_RATE)
                    , cursor.getInt(INDEX_MOVIE_VOTECOUNT)
                    , cursor.getString(INDEX_MOVIE_OVERVIEW)
                    , cursor.getInt(INDEX_MOVIE_CATEGORY));
            convertedMovies.add(movie);
            cursor.moveToNext();
        }
        cursor.close();
        return convertedMovies;
    }

    BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            ContentResolver contentResolver = getContentResolver();
            int id = menuItem.getItemId();
            if (id == R.id.popular) {
                String selection = MoviesContract.Columns.MOVIE_CATEGORY + " = " + 1;
                Cursor cursor = contentResolver.query(movieQueryUri,
                        PROJECTION,
                        selection,
                        null,
                        null);
                if (cursor != null) {
                    movies = converted_movies_cursor(cursor);
                    mMoviesRecyclerViewAdapter.loadNewData(movies);
                }
                return true;
            } else if (id == R.id.top_rated) {
                String selection = MoviesContract.Columns.MOVIE_CATEGORY + " = " + 2;
                Cursor cursor = contentResolver.query(movieQueryUri,
                        PROJECTION,
                        selection,
                        null,
                        null);
                if (cursor != null) {
                    movies = converted_movies_cursor(cursor);
                    mMoviesRecyclerViewAdapter.loadNewData(movies);
                }
                return true;
            } else if (id == R.id.favourites) {
                String selection = MoviesContract.Columns.MOVIE_FAVOURITE + " = ?";
                String[] args = { "fav"};
                Cursor cursor = contentResolver.query(movieQueryUri,
                        PROJECTION,
                        selection,
                        args,
                        null);
                if (cursor != null) {
                    movies = converted_movies_cursor(cursor);
                    mMoviesRecyclerViewAdapter.loadNewData(movies);
                }
                return true;
            }
            return false;
        }
    };

    public class queryData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            mActivityMoviesBinding.loadingIndicator.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String selection = MoviesContract.Columns.MOVIE_CATEGORY + " = " + 1;
            ContentResolver contentResolver = getContentResolver();
            Cursor cursor = contentResolver.query(movieQueryUri,
                    PROJECTION,
                    selection,
                    null,
                    null);
            if (cursor != null) {
                movies = converted_movies_cursor(cursor);
                mMoviesRecyclerViewAdapter.loadNewData(movies);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mActivityMoviesBinding.loadingIndicator.setVisibility(View.INVISIBLE);
            super.onPostExecute(aVoid);
        }
    }

}
