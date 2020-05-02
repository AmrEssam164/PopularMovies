package com.amressam.movies.sync;

import android.app.IntentService;
import android.content.Intent;

public class CastSyncIntentService extends IntentService {
    public CastSyncIntentService() {
        super("CastSyncIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        AppSyncTask.syncCast(this);
    }
}
