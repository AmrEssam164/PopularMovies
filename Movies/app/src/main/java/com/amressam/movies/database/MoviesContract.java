package com.amressam.movies.database;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import static com.amressam.movies.database.AppProvider.CONTENT_AUTHORITY;
import static com.amressam.movies.database.AppProvider.CONTENT_AUTHORITY_URI;

public class MoviesContract {
    static final String TABLE_NAME = "Movies";

    public static class Columns {
        public static final String MOVIE_TITLE = "title";
        public static final String MOVIE_RATE = "rate";
        public static final String MOVIE_RELEASEDATE = "release_date";
        public static final String MOVIE_IMAGE = "image";
        public static final String MOVIE_POSTER_IMAGE = "poster_image";
        public static final String MOVIE_OVERVIEW = "overview";
        public static final String MOVIE_ID = "id";
        public static final String MOVIE_VOTECOUNT = "vote_count";
        public static final String MOVIE_CATEGORY = "category";
        public static final String MOVIE_FAVOURITE = "favourites";

        private Columns() {
            // private constructor to prevent instantiation
        }
    }
    public static final Uri CONTENT_URI = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME);

    static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;
    static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + CONTENT_AUTHORITY + "." + TABLE_NAME;

    public static Uri buildMovieUri(long movieId) {
        return ContentUris.withAppendedId(CONTENT_URI, movieId);
    }

    static long getMovieId(Uri uri) {
        return ContentUris.parseId(uri);
    }
}
