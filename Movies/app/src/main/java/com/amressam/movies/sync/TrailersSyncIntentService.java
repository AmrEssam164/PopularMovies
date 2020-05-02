package com.amressam.movies.sync;

import android.app.IntentService;
import android.content.Intent;

public class TrailersSyncIntentService extends IntentService {
    public TrailersSyncIntentService() {
        super("TrailersSyncIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        AppSyncTask.syncTrailers(this);
    }
}
