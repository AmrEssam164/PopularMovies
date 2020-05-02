package com.amressam.movies.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.amressam.movies.Trailer;

public class AppDatabase extends SQLiteOpenHelper {

    private static final String TAG = "AppDatabase";
    public static final String DATABASE_NAME = "MoviesDatabase";
    public static final int DATABASE_VERSION = 19;
    private static AppDatabase instance = null;

    private AppDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "AppDatabase: constructor");
    }

    static AppDatabase getInstance(Context context) {
        if (instance == null) {
            Log.d(TAG, "getInstance: creating new instance");
            instance = new AppDatabase(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d(TAG, "onCreate: starts");
        db.execSQL("CREATE TABLE " + MoviesContract.TABLE_NAME + " ("
                + MoviesContract.Columns.MOVIE_ID + " INTEGER NOT NULL, "
                + MoviesContract.Columns.MOVIE_TITLE + " TEXT NOT NULL, "
                + MoviesContract.Columns.MOVIE_IMAGE + " TEXT NOT NULL, "
                + MoviesContract.Columns.MOVIE_POSTER_IMAGE + " TEXT NOT NULL, "
                + MoviesContract.Columns.MOVIE_RELEASEDATE + " TEXT NOT NULL, "
                + MoviesContract.Columns.MOVIE_RATE + " INTEGER NOT NULL, "
                + MoviesContract.Columns.MOVIE_VOTECOUNT + " INTEGER NOT NULL, "
                + MoviesContract.Columns.MOVIE_OVERVIEW + " TEXT NOT NULL, "
                + MoviesContract.Columns.MOVIE_CATEGORY + " INTEGER NOT NULL, "
                + MoviesContract.Columns.MOVIE_FAVOURITE + " TEXT NOT NULL)");

        db.execSQL("CREATE TABLE " + CastContract.TABLE_NAME + " ("
                + CastContract.Columns.MOVIE_TITLE + " TEXT NOT NULL, "
                + CastContract.Columns.CAST_NAME + " TEXT NOT NULL, "
                + CastContract.Columns.CAST_IMAGE + " TEXT NOT NULL )");

        db.execSQL("CREATE TABLE " + TrailersContract.TABLE_NAME + " ("
                + TrailersContract.Columns.MOVIE_TITLE + " TEXT NOT NULL, "
                + TrailersContract.Columns.TRAILER_TITLE + " TEXT NOT NULL, "
                + TrailersContract.Columns.TRAILER_IMAGE + " TEXT NOT NULL, "
                + TrailersContract.Columns.TRAILER_ID + " TEXT NOT NULL )");

        db.execSQL("CREATE TABLE " + ReviewsContract.TABLE_NAME + " ("
                + ReviewsContract.Columns.MOVIE_TITLE + " TEXT NOT NULL, "
                + ReviewsContract.Columns.REVIEW_AUTHOR + " TEXT NOT NULL, "
                + ReviewsContract.Columns.REVIEW_TEXT + " TEXT NOT NULL )");

//        ContentValues row = new ContentValues();
//        row.put(TrailersContract.Columns.TRAILER_ID, "Brad Pit");
//        row.put(TrailersContract.Columns.MOVIE_TITLE, "Ad Astra");
//        row.put(TrailersContract.Columns.TRAILER_IMAGE, "xBHvZcjRiWyobQ9kxBhO6B2dtRI.jpg");
//        row.put(TrailersContract.Columns.TRAILER_TITLE, "xBHvZcjRiWyobQ9kxBhO6B2dtRI.jpg");


//        ContentValues row = new ContentValues();
//        row.put(CastContract.Columns.CAST_NAME, "Brad Pit");
//        row.put(CastContract.Columns.MOVIE_TITLE, "Ad Astra");
//        row.put(CastContract.Columns.CAST_IMAGE, "xBHvZcjRiWyobQ9kxBhO6B2dtRI.jpg");

//        ContentValues row = new ContentValues();
//        row.put(MoviesContract.Columns.MOVIE_ID, 419704);
//        row.put(MoviesContract.Columns.MOVIE_TITLE, "Ad Astra");
//        row.put(MoviesContract.Columns.MOVIE_IMAGE, "xBHvZcjRiWyobQ9kxBhO6B2dtRI.jpg");
//        row.put(MoviesContract.Columns.MOVIE_RELEASEDATE, "2019-09-17");
//        row.put(MoviesContract.Columns.MOVIE_RATE, 6);
//        row.put(MoviesContract.Columns.MOVIE_VOTECOUNT, 2853);
//        row.put(MoviesContract.Columns.MOVIE_OVERVIEW, "The near future, a time when both hope and hardships drive humanity to look to the stars and beyond. While a mysterious phenomenon menaces to destroy life on planet Earth, astronaut Roy McBride undertakes a mission across the immensity of space and its many perils to uncover the truth about a lost expedition that decades before boldly faced emptiness and silence in search of the unknown.");
//        row.put(MoviesContract.Columns.MOVIE_CATEGORY,1);
//        db.insert(MoviesContract.TABLE_NAME, null, row);
//
//        Log.d(TAG, "onCreate: ends");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: starts");
        db.execSQL("DROP TABLE IF EXISTS " + MoviesContract.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CastContract.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TrailersContract.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ReviewsContract.TABLE_NAME);
        onCreate(db);
        Log.d(TAG, "onUpgrade: ends");
    }
}
