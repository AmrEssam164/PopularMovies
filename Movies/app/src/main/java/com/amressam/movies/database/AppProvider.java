package com.amressam.movies.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.amressam.movies.Reviews;

public class AppProvider extends ContentProvider {
    private static final String TAG = "AppProvider";

    private AppDatabase mOpenHelper;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    static final String CONTENT_AUTHORITY = "com.amressam.movies.provider";
    public static final Uri CONTENT_AUTHORITY_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    private static final int MOVIES = 100;
    private static final int MOVIES_ID = 101;

    private static final int CAST = 200;
    private static final int CAST_ID = 201;

    private static final int TRAILER = 300;
    private static final int TRAILER_ID = 301;

    private static final int REVIEWS = 400;
    private static final int REVIEWS_ID = 401;


    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        //  eg. content://com.amressam.movies.provider/MoviesActivity
        matcher.addURI(CONTENT_AUTHORITY, MoviesContract.TABLE_NAME, MOVIES);
        // e.g. content://com.amressam.movies.provider/MoviesActivity/8
        matcher.addURI(CONTENT_AUTHORITY, MoviesContract.TABLE_NAME + "/#", MOVIES_ID);

        //  eg. content://com.amressam.movies.provider/MoviesCast
        matcher.addURI(CONTENT_AUTHORITY, CastContract.TABLE_NAME, CAST);
        // e.g. content://com.amressam.movies.provider/MoviesCast/8
        matcher.addURI(CONTENT_AUTHORITY, CastContract.TABLE_NAME + "/#", CAST_ID);

        //  eg. content://com.amressam.movies.provider/MoviesTrailers
        matcher.addURI(CONTENT_AUTHORITY, TrailersContract.TABLE_NAME, TRAILER);
        // e.g. content://com.amressam.movies.provider/MoviesTrailers/8
        matcher.addURI(CONTENT_AUTHORITY, TrailersContract.TABLE_NAME + "/#", TRAILER_ID);

        //  eg. content://com.amressam.movies.provider/MoviesReviews
        matcher.addURI(CONTENT_AUTHORITY, ReviewsContract.TABLE_NAME, REVIEWS);
        // e.g. content://com.amressam.movies.provider/MoviesReviews/8
        matcher.addURI(CONTENT_AUTHORITY, ReviewsContract.TABLE_NAME + "/#", REVIEWS_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        Log.d(TAG, "onCreate: ana hnaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        mOpenHelper = AppDatabase.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Log.d(TAG, "query: called with URI " + uri);
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "query: match is " + match);

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        switch (match) {
            case MOVIES:
                queryBuilder.setTables(MoviesContract.TABLE_NAME);
                break;

            case MOVIES_ID:
                queryBuilder.setTables(MoviesContract.TABLE_NAME);
                long movieId = MoviesContract.getMovieId(uri);
                queryBuilder.appendWhere(MoviesContract.Columns.MOVIE_ID + " = " + movieId);
                break;

            case CAST:
                queryBuilder.setTables(CastContract.TABLE_NAME);
                break;


            case TRAILER:
                queryBuilder.setTables(TrailersContract.TABLE_NAME);
                break;

            case REVIEWS:
                queryBuilder.setTables(ReviewsContract.TABLE_NAME);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);

        }

        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        return queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case MOVIES:
                return MoviesContract.CONTENT_TYPE;

            case MOVIES_ID:
                return MoviesContract.CONTENT_ITEM_TYPE;

            case CAST:
                return CastContract.CONTENT_TYPE;

            case CAST_ID:
                return CastContract.CONTENT_ITEM_TYPE;

            case TRAILER:
                return TrailersContract.CONTENT_TYPE;

            case TRAILER_ID:
                return TrailersContract.CONTENT_ITEM_TYPE;

            case REVIEWS:
                return ReviewsContract.CONTENT_TYPE;

            case REVIEWS_ID:
                return ReviewsContract.CONTENT_ITEM_TYPE;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);

        }
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        Log.d(TAG, "insert: called with URI " + uri);
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "insert: match is " + match);

        final SQLiteDatabase db;
        long recordId;
        int rowsInserted = 0;
        switch (match) {
            case MOVIES:
                db = mOpenHelper.getWritableDatabase();
                for (ContentValues value : values) {
                    recordId = db.insert(MoviesContract.TABLE_NAME, null, value);
                    if (recordId < 0) {
                        throw new android.database.SQLException("Failed to insert into " + uri.toString());
                    }
                    rowsInserted++;
                }
                break;

            case CAST:
                db = mOpenHelper.getWritableDatabase();
                for (ContentValues value : values) {
                    recordId = db.insert(CastContract.TABLE_NAME, null, value);
                    if (recordId < 0) {
                        throw new android.database.SQLException("Failed to insert into " + uri.toString());
                    }
                    rowsInserted++;
                }
                break;

            case TRAILER:
                db = mOpenHelper.getWritableDatabase();
                for (ContentValues value : values) {
                    recordId = db.insert(TrailersContract.TABLE_NAME, null, value);
                    if (recordId < 0) {
                        throw new android.database.SQLException("Failed to insert into " + uri.toString());
                    }
                    rowsInserted++;
                }
                break;

            case REVIEWS:
                db = mOpenHelper.getWritableDatabase();
                for (ContentValues value : values) {
                    recordId = db.insert(ReviewsContract.TABLE_NAME, null, value);
                    if (recordId < 0) {
                        throw new android.database.SQLException("Failed to insert into " + uri.toString());
                    }
                    rowsInserted++;
                }
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        return rowsInserted;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.d(TAG, "insert: called with URI " + uri);
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "insert: match is " + match);

        final SQLiteDatabase db;
        long recordId;
        Uri returnUri;
        switch (match) {
            case MOVIES:
                db = mOpenHelper.getWritableDatabase();
                recordId = db.insert(MoviesContract.TABLE_NAME, null, values);
                if (recordId >= 0) {
                    returnUri = MoviesContract.buildMovieUri(recordId);
                } else {
                    throw new android.database.SQLException("Failed to insert into " + uri.toString());
                }
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        return returnUri;

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d(TAG, "delete: called with URI " + uri);
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "delete: match is " + match);

        final SQLiteDatabase db;
        int count;

        String selectionCriteria;

        switch(match) {
            case MOVIES:
                db = mOpenHelper.getWritableDatabase();
                count = db.delete(MoviesContract.TABLE_NAME, selection, selectionArgs);
                break;

            case MOVIES_ID:
                db = mOpenHelper.getWritableDatabase();
                long movieId = MoviesContract.getMovieId(uri);
                selectionCriteria = MoviesContract.Columns.MOVIE_ID + " = " +movieId;

                if((selection != null) && (selection.length()>0)) {
                    selectionCriteria += " AND (" + selection + ")";
                }
                count = db.delete(MoviesContract.TABLE_NAME, selectionCriteria, selectionArgs);
                break;

            case CAST:
                db = mOpenHelper.getWritableDatabase();
                count = db.delete(CastContract.TABLE_NAME, selection, selectionArgs);
                break;



            case TRAILER:
                db = mOpenHelper.getWritableDatabase();
                count = db.delete(TrailersContract.TABLE_NAME, selection, selectionArgs);
                break;

            case REVIEWS:
                db = mOpenHelper.getWritableDatabase();
                count = db.delete(ReviewsContract.TABLE_NAME, selection, selectionArgs);
                break;



            default:
                throw new IllegalArgumentException("Unknown uri: " + uri);
        }
        Log.d(TAG, "Exiting update, returning " + count);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.d(TAG, "update: called with URI " + uri);
        final int match = sUriMatcher.match(uri);
        Log.d(TAG, "update: match is " + match);

        final SQLiteDatabase db;
        int count;

        String selectionCriteria;

        switch(match) {
            case MOVIES:
                db = mOpenHelper.getWritableDatabase();
                count = db.update(MoviesContract.TABLE_NAME, values, selection, selectionArgs);
                break;

            case MOVIES_ID:
                db = mOpenHelper.getWritableDatabase();
                long movieId = MoviesContract.getMovieId(uri);
                selectionCriteria = MoviesContract.Columns.MOVIE_ID + " = " +movieId;

                if((selection != null) && (selection.length()>0)) {
                    selectionCriteria += " AND (" + selection + ")";
                }
                count = db.update(MoviesContract.TABLE_NAME, values, selectionCriteria, selectionArgs);
                break;

            case TRAILER:
                db = mOpenHelper.getWritableDatabase();
                count = db.delete(TrailersContract.TABLE_NAME, selection, selectionArgs);
                break;

            case REVIEWS:
                db = mOpenHelper.getWritableDatabase();
                count = db.delete(ReviewsContract.TABLE_NAME, selection, selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown uri: " + uri);
        }
        Log.d(TAG, "Exiting update, returning " + count);
        return count;
    }
}
