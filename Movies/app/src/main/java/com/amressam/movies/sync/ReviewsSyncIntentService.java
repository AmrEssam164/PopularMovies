package com.amressam.movies.sync;

import android.app.IntentService;
import android.content.Intent;

public class ReviewsSyncIntentService extends IntentService {
    public ReviewsSyncIntentService() {
        super("ReviewsSyncIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        AppSyncTask.syncReviews(this);
    }
}
