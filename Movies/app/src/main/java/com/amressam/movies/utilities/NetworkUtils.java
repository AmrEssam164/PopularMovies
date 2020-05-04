/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.amressam.movies.utilities;

import android.net.Uri;
import android.util.Log;

import com.amressam.movies.MoviesActivity;
import com.amressam.movies.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public final class NetworkUtils {

    private static final String TAG = "Networking";

    private static final String YOUTUBE_URL =
            "https://www.googleapis.com/youtube/v3/search";

    final static String QUERY_PARAM = "q";

    final static String PART_PARAM = "part";
    private static final String part = "snippet";

    final static String MAX_PARAM = "maxResults";
    private static final String max_num = "3";

    final static String YOUTUBE_API_KEY = "key";
    private static final String youtube_key= MoviesActivity.mResource.getString(R.string.YOUTUBE_API_KEY);

    private static final String BASE_MOVIE_URL =
            "http://api.themoviedb.org/3/movie";

    private static final String POPULAR_MOVIES_URL =
            "http://api.themoviedb.org/3/movie/popular";

    private static final String TOP_RATED_MOVIES_URL =
            "http://api.themoviedb.org/3/movie/top_rated";

    final static String MOVIES_API_key = "api_key";
    private static final String movies_key=MoviesActivity.mResource.getString(R.string.MOVIES_API_KEY);


    public static URL buildYoutubeUrl(String movie_title) {
        String movieTitle = movie_title+" "+"trailer";
        Uri builtUri = Uri.parse(YOUTUBE_URL).buildUpon()
                .appendQueryParameter(PART_PARAM, part)
                .appendQueryParameter(MAX_PARAM, max_num)
                .appendQueryParameter(QUERY_PARAM, movieTitle)
                .appendQueryParameter(YOUTUBE_API_KEY, youtube_key)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildPopularMoviesUrl() {
        Uri builtUri = Uri.parse(BASE_MOVIE_URL).buildUpon()
                .appendPath("popular")
                .appendQueryParameter(MOVIES_API_key, movies_key)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildTopRatedMoviesUrl() {
        Uri builtUri = Uri.parse(BASE_MOVIE_URL).buildUpon()
                .appendPath("top_rated")
                .appendQueryParameter(MOVIES_API_key, movies_key)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildCastUrl(String id) {
        Uri builtUri = Uri.parse(BASE_MOVIE_URL).buildUpon()
                .appendPath(id)
                .appendPath("credits")
                .appendQueryParameter(MOVIES_API_key, movies_key)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildReviewsUrl(String id) {
        Uri builtUri = Uri.parse(BASE_MOVIE_URL).buildUpon()
                .appendPath(id)
                .appendPath("reviews")
                .appendQueryParameter(MOVIES_API_key, movies_key)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }


    public static String getResponseFromHttpUrl(URL url) {
        HttpURLConnection connection = null;
        BufferedReader bufferedReader = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int response = connection.getResponseCode();
            Log.d(TAG, "doInBackground: The response code was " + response);

            StringBuilder result = new StringBuilder();

            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            for (String line = bufferedReader.readLine(); line != null; line = bufferedReader.readLine()) {
                result.append(line).append("\n");
            }
            return result.toString();

        } catch (MalformedURLException e) {
            Log.e(TAG, "doInBackground: Invalid URL " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "doInBackground: IO Exception reading data " + e.getMessage());
        } catch (SecurityException e) {
            Log.e(TAG, "doInBackground: Security Exception. Needs permission? " + e.getMessage());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    Log.d(TAG, "doInBackground: Error closing stream" + e.getMessage());
                }
            }

        }
        return null;
    }
}