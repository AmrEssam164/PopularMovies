package com.amressam.movies.database;

import android.content.ContentUris;
import android.net.Uri;

import static com.amressam.movies.database.AppProvider.CONTENT_AUTHORITY;
import static com.amressam.movies.database.AppProvider.CONTENT_AUTHORITY_URI;

public class ReviewsContract {
    static final String TABLE_NAME = "MoviesReviews";

    public static class Columns {
        public static final String MOVIE_TITLE = "movie_title";
        public static final String REVIEW_AUTHOR = "author";
        public static final String REVIEW_TEXT = "review";

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
