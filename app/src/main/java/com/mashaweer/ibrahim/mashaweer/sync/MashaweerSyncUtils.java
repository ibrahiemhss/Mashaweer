package com.mashaweer.ibrahim.mashaweer.sync;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.android.volley.RequestQueue;

import java.util.concurrent.TimeUnit;

/**
 * Created by ibrahim on 29/12/17.
 */
//TODO 4 from class HealthCareSyncIntentService
public class MashaweerSyncUtils {


    private static final int SYNC_INTERVAL_HOURS = 3;
    private static final int SYNC_INTERVAL_SECONDS = (int) TimeUnit.HOURS.toSeconds( SYNC_INTERVAL_HOURS );
    private static final int SYNC_FLEXTIME_SECONDS = SYNC_INTERVAL_SECONDS / 3;
    private static final String SUNSHINE_SYNC_TAG = "Mashaweer_sync";
    private static RequestQueue requestQueue;
    private static boolean sInitialized;

    //TODO 5
    synchronized public static void initialize(@NonNull final Context context) {


        if (sInitialized) return;

        sInitialized = true;

        Thread checkForEmpty = new Thread( new Runnable() {
            @Override
            public void run() {
                startImmediateSync( context );


            }
        } );

        checkForEmpty.start();
    }

    public static void startImmediateSync(@NonNull final Context context) {
        Intent intentToSyncImmediately = new Intent( context, MashaweerSyncIntentService.class );
        context.startService( intentToSyncImmediately );
    }
}
