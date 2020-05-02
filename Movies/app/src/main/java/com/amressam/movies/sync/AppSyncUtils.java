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
package com.amressam.movies.sync;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.amressam.movies.Reviews;
import com.amressam.movies.database.CastContract;
import com.amressam.movies.database.MoviesContract;
import com.amressam.movies.database.ReviewsContract;
import com.amressam.movies.database.TrailersContract;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.TimeUnit;

public class AppSyncUtils {

    private static final int SYNC_INTERVAL_HOURS = 12;
    private static final int SYNC_INTERVAL_SECONDS = (int) TimeUnit.HOURS.toSeconds(SYNC_INTERVAL_HOURS);
    private static final int SYNC_FLEXTIME_SECONDS = SYNC_INTERVAL_SECONDS / 3;

//    private static final int SYNC_INTERVAL_HOURS_TRAILERS = 12;
//    private static final int SYNC_INTERVAL_SECONDS_TRAILERS = (int) TimeUnit.HOURS.toSeconds(SYNC_INTERVAL_HOURS_TRAILERS);
//    private static final int SYNC_FLEXTIME_SECONDS_TRAILERS = SYNC_INTERVAL_SECONDS_TRAILERS / 3;


    private static final String MOVIES_SYNC_TAG = "movies-sync";
    private static final String CAST_SYNC_TAG = "cast-sync";
    private static final String TRAILERS_SYNC_TAG = "trailers-sync";
    private static final String REVIEWS_SYNC_TAG = "reviews-sync";

    private static final String TAG = "AppSyncUtils";
    private static boolean sInitialized;

    static void scheduleFirebaseJobDispatcherSyncForMovies(@NonNull final Context context) {

        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        /* Create the Job to periodically sync Sunshine */
        Job syncSunshineJob = dispatcher.newJobBuilder()
                .setService(MoviesFirebaseJobService.class)
                .setTag(MOVIES_SYNC_TAG)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(
                        SYNC_INTERVAL_SECONDS,
                        SYNC_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS))
                .setReplaceCurrent(true)
                .build();

        dispatcher.schedule(syncSunshineJob);
    }

    static void scheduleFirebaseJobDispatcherSyncForCast(@NonNull final Context context) {

        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        /* Create the Job to periodically sync Sunshine */
        Job syncSunshineJob = dispatcher.newJobBuilder()
                .setService(CastFirebaseJobService.class)
                .setTag(CAST_SYNC_TAG)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(
                        SYNC_INTERVAL_SECONDS,
                        SYNC_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS))
                .setReplaceCurrent(true)
                .build();

        dispatcher.schedule(syncSunshineJob);
    }

    static void scheduleFirebaseJobDispatcherSyncForTrailers(@NonNull final Context context) {

        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        /* Create the Job to periodically sync Sunshine */
        Job syncSunshineJob = dispatcher.newJobBuilder()
                .setService(TrailersFirebaseJobService.class)
                .setTag(TRAILERS_SYNC_TAG)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(
                        SYNC_INTERVAL_SECONDS,
                        SYNC_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS))
                .setReplaceCurrent(true)
                .build();

        dispatcher.schedule(syncSunshineJob);
    }

    static void scheduleFirebaseJobDispatcherSyncForReviews(@NonNull final Context context) {

        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        /* Create the Job to periodically sync Sunshine */
        Job syncSunshineJob = dispatcher.newJobBuilder()
                .setService(ReviewsFirebaseJobService.class)
                .setTag(REVIEWS_SYNC_TAG)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(
                        SYNC_INTERVAL_SECONDS,
                        SYNC_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS))
                .setReplaceCurrent(true)
                .build();

        dispatcher.schedule(syncSunshineJob);
    }

    synchronized public static void initialize(@NonNull final Context context) {
        Log.d(TAG, "initialize: bd2na initialize");
        if (sInitialized) 
        {
            Log.d(TAG, "initialize: already downloaded");
            return;
        }

        Log.d(TAG, "initialize: first time");
        sInitialized = true;

        scheduleFirebaseJobDispatcherSyncForMovies(context);
        scheduleFirebaseJobDispatcherSyncForCast(context);
        scheduleFirebaseJobDispatcherSyncForTrailers(context);
        scheduleFirebaseJobDispatcherSyncForReviews(context);

        new AsyncTask<Void, Void, Void>() {
            @Override
            public Void doInBackground( Void... voids ) {

                Uri QueryUri = MoviesContract.CONTENT_URI;

                String[] projectionMoviesColumns = {MoviesContract.Columns.MOVIE_ID};

                Cursor cursor = context.getContentResolver().query(
                        QueryUri,
                        projectionMoviesColumns,
                        null,
                        null,
                        null);

                if (null == cursor || cursor.getCount() == 0) {
                    Log.d(TAG, "doInBackground: movies table is empty");
                    startImmediateSyncForMovies(context);
                }
                else{
                    Log.d(TAG, "doInBackground: movies table isn't empty");
                }

                cursor.close();

                QueryUri = CastContract.CONTENT_URI;

                String[] projectionCastColumns = {CastContract.Columns.MOVIE_TITLE};

                cursor = context.getContentResolver().query(
                        QueryUri,
                        projectionCastColumns,
                        null,
                        null,
                        null);

                if (null == cursor || cursor.getCount() == 0) {
                    Log.d(TAG, "doInBackground: cast table is empty");
                    startImmediateSyncForCast(context);
                }
                else{
                    Log.d(TAG, "doInBackground: cast table isn't empty");
                }

                cursor.close();
                QueryUri = TrailersContract.CONTENT_URI;

                String[] projectionTrailersColumns = {TrailersContract.Columns.MOVIE_TITLE};

                cursor = context.getContentResolver().query(
                        QueryUri,
                        projectionTrailersColumns,
                        null,
                        null,
                        null);

                if (null == cursor || cursor.getCount() == 0) {
                    Log.d(TAG, "doInBackground: cast table is empty");
                    startImmediateSyncForTrailers(context);
                }
                else{
                    Log.d(TAG, "doInBackground: cast table isn't empty");
                }

                QueryUri = ReviewsContract.CONTENT_URI;

                String[] projectionReviewsColumns = {ReviewsContract.Columns.MOVIE_TITLE};

                cursor = context.getContentResolver().query(
                        QueryUri,
                        projectionReviewsColumns,
                        null,
                        null,
                        null);

                if (null == cursor || cursor.getCount() == 0) {
                    Log.d(TAG, "doInBackground: cast table is empty");
                    startImmediateSyncForReviews(context);
                }
                else{
                    Log.d(TAG, "doInBackground: cast table isn't empty");
                }

                cursor.close();
                return null;
            }
        }.execute();

    }

    public static void startImmediateSyncForMovies(@NonNull final Context context) {
        Intent intentToSyncImmediately = new Intent(context, MoviesSyncIntentService.class);
        context.startService(intentToSyncImmediately);
    }

    public static void startImmediateSyncForCast(@NonNull final Context context) {
        Intent intentToSyncImmediately = new Intent(context, CastSyncIntentService.class);
        context.startService(intentToSyncImmediately);
    }

    public static void startImmediateSyncForTrailers(@NonNull final Context context) {
        Intent intentToSyncImmediately = new Intent(context, TrailersSyncIntentService.class);
        context.startService(intentToSyncImmediately);
    }

    public static void startImmediateSyncForReviews(@NonNull final Context context) {
        Intent intentToSyncImmediately = new Intent(context, ReviewsSyncIntentService.class);
        context.startService(intentToSyncImmediately);
    }
}