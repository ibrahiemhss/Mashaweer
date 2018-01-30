package com.mashaweer.ibrahim.mashaweer.FirebaseMasseging;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.mashaweer.ibrahim.mashaweer.data.SharedPrefManager;

/**
 * Created by ibrahim on 24/12/17.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        storeToken(refreshedToken);
    }

    private void storeToken(String token) {
       // preferenceUtil=new PreferenceUtil( this );
      //  preferenceUtil.saveDeviceToken( token );
        //saving the token on shared preferences
        SharedPrefManager.getInstance(getApplicationContext()).saveDeviceToken(token);

    }
}