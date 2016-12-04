package com.example.user.onedaynquestions.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by PCPC on 2016-12-04.
 */

public class BroadReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {

        String name = intent.getAction();

        if(name.equals(".service.PushReceiver")){
            Toast.makeText
                    (context, intent.getDataString(), Toast.LENGTH_SHORT).show();
        }
    }

}
