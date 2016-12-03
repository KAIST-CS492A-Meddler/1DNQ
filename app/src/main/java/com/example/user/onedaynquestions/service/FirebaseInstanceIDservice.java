package com.example.user.onedaynquestions.service;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class FirebaseInstanceIDservice extends FirebaseInstanceIdService {
    private final static String TAG = "FirebaseInstanceID";

    public FirebaseInstanceIDservice() {
    }

    @Override
    public void onTokenRefresh(){
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed Token: " + token);
    }
}
