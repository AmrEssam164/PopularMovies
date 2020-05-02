package com.amressam.movies.utilities;

import android.content.ContentValues;
import android.util.Log;

import com.amressam.movies.Cast;
import com.amressam.movies.Reviews;
import com.amressam.movies.Trailer;
import com.amressam.movies.database.CastContract;
import com.amressam.movies.database.MoviesContract;
import com.amressam.movies.database.ReviewsContract;
import com.amressam.movies.database.TrailersContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FetchJsonUtils {

    private static final String TAG = "FetchJsonData";

    public static ContentValues[] getPopularMoviesContentValuesFromJson(String data) {
        if(data!=null){
            try {
                JSONObject jsonData = new JSONObject(data);
                JSONArray itemsArray = jsonData.getJSONArray("results");
                ContentValues[] moviesContentValues = new ContentValues[itemsArray.length()];
                for(int i=0;i<itemsArray.length();i++)
                {
                    JSONObject jsonMovie = itemsArray.getJSONObject(i);
                    String title = jsonMovie.getString("title");
                    String poster_path = jsonMovie.getString("poster_path");
                    String backdrop_path = jsonMovie.getString("backdrop_path");
                    int vote_average = jsonMovie.getInt("vote_average");
                    String release_date = jsonMovie.getString("release_date");
                    String overview = jsonMovie.getString("overview");
                    int id = jsonMovie.getInt("id");
                    int vote_count = jsonMovie.getInt("vote_count");
                    ContentValues moviesValues = new ContentValues();
                    moviesValues.put(MoviesContract.Columns.MOVIE_ID, id);
                    moviesValues.put(MoviesContract.Columns.MOVIE_TITLE, title);
                    moviesValues.put(MoviesContract.Columns.MOVIE_IMAGE, poster_path);
                    moviesValues.put(MoviesContract.Columns.MOVIE_POSTER_IMAGE, backdrop_path);
                    moviesValues.put(MoviesContract.Columns.MOVIE_RELEASEDATE, release_date);
                    moviesValues.put(MoviesContract.Columns.MOVIE_RATE, vote_average);
                    moviesValues.put(MoviesContract.Columns.MOVIE_VOTECOUNT, vote_count);
                    moviesValues.put(MoviesContract.Columns.MOVIE_OVERVIEW, overview);
                    moviesValues.put(MoviesContract.Columns.MOVIE_CATEGORY, 1);
                    moviesValues.put(MoviesContract.Columns.MOVIE_FAVOURITE, "not_fav");

                    moviesContentValues[i] = moviesValues;
                }
                return moviesContentValues;
            } catch(JSONException E){
                E.printStackTrace();
                Log.d(TAG, "onDownloadComplete: Error processing json data " + E.getMessage());
            }
        }
        return null;
    }
    public static ContentValues[] getTopRatedMoviesContentValuesFromJson(String data) {
        if(data!=null){
            try {
                JSONObject jsonData = new JSONObject(data);
                JSONArray itemsArray = jsonData.getJSONArray("results");
                ContentValues[] moviesContentValues = new ContentValues[itemsArray.length()];
                for(int i=0;i<itemsArray.length();i++)
                {
                    JSONObject jsonMovie = itemsArray.getJSONObject(i);
                    String title = jsonMovie.getString("title");
                    String poster_path = jsonMovie.getString("poster_path");
                    String backdrop_path = jsonMovie.getString("backdrop_path");
                    int vote_average = jsonMovie.getInt("vote_average");
                    String release_date = jsonMovie.getString("release_date");
                    String overview = jsonMovie.getString("overview");
                    int id = jsonMovie.getInt("id");
                    int vote_count = jsonMovie.getInt("vote_count");
                    ContentValues moviesValues = new ContentValues();
                    moviesValues.put(MoviesContract.Columns.MOVIE_ID, id);
                    moviesValues.put(MoviesContract.Columns.MOVIE_TITLE, title);
                    moviesValues.put(MoviesContract.Columns.MOVIE_IMAGE, poster_path);
                    moviesValues.put(MoviesContract.Columns.MOVIE_POSTER_IMAGE, backdrop_path);
                    moviesValues.put(MoviesContract.Columns.MOVIE_RELEASEDATE, release_date);
                    moviesValues.put(MoviesContract.Columns.MOVIE_RATE, vote_average);
                    moviesValues.put(MoviesContract.Columns.MOVIE_VOTECOUNT, vote_count);
                    moviesValues.put(MoviesContract.Columns.MOVIE_OVERVIEW, overview);
                    moviesValues.put(MoviesContract.Columns.MOVIE_CATEGORY, 2);
                    moviesValues.put(MoviesContract.Columns.MOVIE_FAVOURITE, "not_fav");

                    moviesContentValues[i] = moviesValues;
                }
                return moviesContentValues;
            } catch(JSONException E){
                E.printStackTrace();
                Log.d(TAG, "onDownloadComplete: Error processing json data " + E.getMessage());
            }
        }
        return null;
    }

    public static ContentValues[] getMoviesCastContentValuesFromJson(String data,String movie_title) {
        if(data!=null){
            try {
                JSONObject jsonData = new JSONObject(data);
                JSONArray itemsArray = jsonData.getJSONArray("cast");
                ContentValues[] CastContentValues = new ContentValues[itemsArray.length()];
                for(int i=0;i<itemsArray.length();i++)
                {
                    JSONObject jsonMovie = itemsArray.getJSONObject(i);
//                    String character = jsonMovie.getString("character");
                    String name = jsonMovie.getString("name");
                    String profile_path = jsonMovie.getString("profile_path");
                    ContentValues castValues = new ContentValues();
                    castValues.put(CastContract.Columns.MOVIE_TITLE, movie_title);
                    castValues.put(CastContract.Columns.CAST_NAME, name);
                    castValues.put(CastContract.Columns.CAST_IMAGE, profile_path);
                    CastContentValues[i] = castValues;
                }
                return CastContentValues;
            } catch(JSONException E){
                E.printStackTrace();
                Log.d(TAG, "onDownloadComplete: Error processing json data " + E.getMessage());
            }
        }
        return null;
    }

    public static ContentValues[] getMoviesTrailersContentValuesFromJson(String data,String movie_title) {
        if(data!=null){
            try {
                JSONObject jsonData = new JSONObject(data);
                JSONArray itemsArray = jsonData.getJSONArray("items");
                ContentValues[] TrailersContentValues = new ContentValues[itemsArray.length()];
                for(int i=0;i<itemsArray.length();i++)
                {
                    JSONObject jsonTrailer = itemsArray.getJSONObject(i);
                    JSONObject jsonId = jsonTrailer.getJSONObject("id");
                    String trailerId = jsonId.getString("videoId");
                    JSONObject jsonSnippet = jsonTrailer.getJSONObject("snippet");
                    String trailerTitle = jsonSnippet.getString("title");
                    JSONObject jsonThumbnails = jsonSnippet.getJSONObject("thumbnails");
                    JSONObject jsonHigh = jsonThumbnails.getJSONObject("high");
                    String trailerImage = jsonHigh.getString("url");
                    ContentValues trailersValues = new ContentValues();
                    trailersValues.put(TrailersContract.Columns.MOVIE_TITLE, movie_title);
                    trailersValues.put(TrailersContract.Columns.TRAILER_TITLE, trailerTitle);
                    trailersValues.put(TrailersContract.Columns.TRAILER_IMAGE, trailerImage);
                    trailersValues.put(TrailersContract.Columns.TRAILER_ID, trailerId);
                    TrailersContentValues[i] = trailersValues;
                }
                return TrailersContentValues;
            } catch(JSONException E){
                E.printStackTrace();
                Log.d(TAG, "onDownloadComplete: Error processing json data " + E.getMessage());
            }
        }
        return null;
    }

    public static ContentValues[] getMoviesReviewsContentValuesFromJson(String data,String movie_title) {
        if(data!=null){
            try {
                JSONObject jsonData = new JSONObject(data);
                JSONArray itemsArray = jsonData.getJSONArray("results");
                ContentValues[] ReviewsContentValues = new ContentValues[itemsArray.length()];
                for(int i=0;i<itemsArray.length();i++)
                {
                    JSONObject jsonMovie = itemsArray.getJSONObject(i);
                    String author = jsonMovie.getString("author");
                    String content = jsonMovie.getString("content");
                    ContentValues reviewsValues = new ContentValues();
                    reviewsValues.put(ReviewsContract.Columns.MOVIE_TITLE, movie_title);
                    reviewsValues.put(ReviewsContract.Columns.REVIEW_AUTHOR, author);
                    reviewsValues.put(ReviewsContract.Columns.REVIEW_TEXT, content);
                    ReviewsContentValues[i] = reviewsValues;
                }
                return ReviewsContentValues;
            } catch(JSONException E){
                E.printStackTrace();
                Log.d(TAG, "onDownloadComplete: Error processing json data " + E.getMessage());
            }
        }
        return null;
    }



    public static ArrayList<Reviews> onDownloadComplete4(String data) {
        if(data!=null){
            ArrayList<Reviews> mReview=new ArrayList<Reviews>();
            try {
                JSONObject jsonData = new JSONObject(data);
                JSONArray itemsArray = jsonData.getJSONArray("results");
                for(int i=0;i<itemsArray.length();i++)
                {
                    JSONObject jsonMovie = itemsArray.getJSONObject(i);
                    String author = jsonMovie.getString("author");
                    String content = jsonMovie.getString("content");
                    Reviews reviewObject = new Reviews(author,content);
                    mReview.add(reviewObject);
                }
                return mReview;
            } catch(JSONException E){
                E.printStackTrace();
                Log.d(TAG, "onDownloadComplete: Error processing json data " + E.getMessage());
            }
        }
        return null;
    }
}
