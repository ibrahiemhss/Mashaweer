package com.mashaweer.ibrahim.mashaweer.sync;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by ibrahim on 29/12/17.
 */
public class MashaweerSyncIntentService extends IntentService {


    public MashaweerSyncIntentService() {
        super( "MashaweerSyncIntentService" );
    }

    @Override
    protected void onHandleIntent(Intent intent) {
//TODO 3 HealthCareSyncData من  syncSpinner هذا كلاس السيرفس ييلخذ الان من الميثود

        MashaweerSyncData.syncSpinner( this );


    }
}
