package com.amressam.movies.database;

import android.content.ContentUris;
import android.net.Uri;

import static com.amressam.movies.database.AppProvider.CONTENT_AUTHORITY;
import static com.amressam.movies.database.AppProvider.CONTENT_AUTHORITY_URI;

public class CastContract {
    static final String TABLE_NAME = "MoviesCast";

    public static class Columns {
        public static final String MOVIE_TITLE = "movie_title";
        public static final String CAST_NAME = "name";
        public static final String CAST_IMAGE = "image";

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
