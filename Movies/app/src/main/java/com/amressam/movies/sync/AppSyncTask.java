package com.amressam.movies.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;

import com.amressam.movies.MoviesActivity;
import com.amressam.movies.database.CastContract;
import com.amressam.movies.database.MoviesContract;
import com.amressam.movies.database.ReviewsContract;
import com.amressam.movies.database.TrailersContract;
import com.amressam.movies.utilities.FetchJsonUtils;
import com.amressam.movies.utilities.NetworkUtils;

import java.net.URL;

public class AppSyncTask {

    public static final String FIRST_TASK = "first";
    public static final String SECOND_TASK = "second";
    public static final String THIRD_TASK = "third";

    synchronized public static void syncMovies(Context context) {

        try {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            int result = sharedPreferences.getInt(FIRST_TASK, -100);
            if (result == -100) {
                sharedPreferences.edit().putInt(FIRST_TASK, 1).apply();
            } else {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                result += 1;
                editor.putInt(FIRST_TASK, result);
                editor.apply();
            }

            URL popularMoviesRequestUrl = NetworkUtils.buildPopularMoviesUrl();

            String jsonPopularMoviesResponse = NetworkUtils.getResponseFromHttpUrl(popularMoviesRequestUrl);

            ContentValues[] popularMoviesValues = FetchJsonUtils
                    .getPopularMoviesContentValuesFromJson(jsonPopularMoviesResponse);

            if (popularMoviesValues != null && popularMoviesValues.length != 0) {

                String selection = MoviesContract.Columns.MOVIE_CATEGORY + " = " + 1;
                ContentResolver contentResolver = context.getContentResolver();
                contentResolver.delete(
                        MoviesContract.CONTENT_URI,
                        selection,
                        null);

                contentResolver.bulkInsert(
                        MoviesContract.CONTENT_URI,
                        popularMoviesValues);

            }

            URL topRatedMoviesRequestUrl = NetworkUtils.buildTopRatedMoviesUrl();

            String jsonTopRatedMoviesResponse = NetworkUtils.getResponseFromHttpUrl(topRatedMoviesRequestUrl);

            ContentValues[] topRatedMoviesValues = FetchJsonUtils
                    .getTopRatedMoviesContentValuesFromJson(jsonTopRatedMoviesResponse);

            if (topRatedMoviesValues != null && topRatedMoviesValues.length != 0) {


                String selection = MoviesContract.Columns.MOVIE_CATEGORY + " = " + 2;
                ContentResolver contentResolver = context.getContentResolver();
                contentResolver.delete(
                        MoviesContract.CONTENT_URI,
                        selection,
                        null);

                contentResolver.bulkInsert(
                        MoviesContract.CONTENT_URI,
                        topRatedMoviesValues);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    synchronized public static void syncCast(Context context) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        int result = sharedPreferences.getInt(SECOND_TASK, -100);
        if (result == -100) {
            sharedPreferences.edit().putInt(SECOND_TASK, 1).apply();
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            result += 1;
            editor.putInt(SECOND_TASK, result);
            editor.apply();
        }

        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(MoviesContract.CONTENT_URI
                , MoviesActivity.PROJECTION
                , null
                , null
                , null);
        if (cursor != null) {
            try {
                boolean check = false;
                cursor.moveToFirst();
                for (int i = 0; i < cursor.getCount(); i++) {
                    if (i == 0) {
                        check = true;
                    }
                    URL castRequestUrl = NetworkUtils.buildCastUrl(String.valueOf(cursor.getInt(MoviesActivity.INDEX_MOVIE_ID)));

                    String jsonCastResponse = NetworkUtils.getResponseFromHttpUrl(castRequestUrl);

                    ContentValues[] castValues = FetchJsonUtils
                            .getMoviesCastContentValuesFromJson(jsonCastResponse, cursor.getString(MoviesActivity.INDEX_MOVIE_TITLE));
                    if (castValues != null && castValues.length != 0) {

                        if (check) {
                            contentResolver.delete(
                                    CastContract.CONTENT_URI,
                                    null,
                                    null);
                            check = false;
                        }
                        contentResolver.bulkInsert(
                                CastContract.CONTENT_URI,
                                castValues);

                    }
                    cursor.moveToNext();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    synchronized public static void syncTrailers(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        int result = sharedPreferences.getInt(THIRD_TASK, -100);
        if (result == -100) {
            sharedPreferences.edit().putInt(THIRD_TASK, 1).apply();
        } else {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            result += 1;
            editor.putInt(THIRD_TASK, result);
            editor.apply();
        }
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(MoviesContract.CONTENT_URI
                , MoviesActivity.PROJECTION
                , null
                , null
                , null);
        if (cursor != null) {
            try {
                boolean check = false;
                cursor.moveToFirst();
                for (int i = 0; i < cursor.getCount(); i++) {
                    if (i == 0) {
                        check = true;
                    }
                    URL trailerRequestUrl = NetworkUtils.buildYoutubeUrl(cursor.getString(MoviesActivity.INDEX_MOVIE_TITLE));

                    String jsonTrailersResponse = NetworkUtils.getResponseFromHttpUrl(trailerRequestUrl);

                    ContentValues[] trailersValues = FetchJsonUtils
                            .getMoviesTrailersContentValuesFromJson(jsonTrailersResponse, cursor.getString(MoviesActivity.INDEX_MOVIE_TITLE));
                    if (trailersValues != null && trailersValues.length != 0) {

                        if (check) {
                            contentResolver.delete(
                                    TrailersContract.CONTENT_URI,
                                    null,
                                    null);
                            check = false;
                        }
                        contentResolver.bulkInsert(
                                TrailersContract.CONTENT_URI,
                                trailersValues);

                    }
                    cursor.moveToNext();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    synchronized public static void syncReviews(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(MoviesContract.CONTENT_URI
                , MoviesActivity.PROJECTION
                , null
                , null
                , null);
        if (cursor != null) {
            try {
                boolean check = false;
                cursor.moveToFirst();
                for (int i = 0; i < cursor.getCount(); i++) {
                    if (i == 0) {
                        check = true;
                    }
                    URL reviewRequestUrl = NetworkUtils.buildReviewsUrl(String.valueOf(cursor.getInt(MoviesActivity.INDEX_MOVIE_ID)));

                    String jsonReviewsResponse = NetworkUtils.getResponseFromHttpUrl(reviewRequestUrl);

                    ContentValues[] reviewsValues = FetchJsonUtils
                            .getMoviesReviewsContentValuesFromJson(jsonReviewsResponse, cursor.getString(MoviesActivity.INDEX_MOVIE_TITLE));
                    if (reviewsValues != null && reviewsValues.length != 0) {

                        if (check) {
                            contentResolver.delete(
                                    ReviewsContract.CONTENT_URI,
                                    null,
                                    null);
                            check = false;
                        }
                        contentResolver.bulkInsert(
                                ReviewsContract.CONTENT_URI,
                                reviewsValues);

                    }
                    cursor.moveToNext();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
