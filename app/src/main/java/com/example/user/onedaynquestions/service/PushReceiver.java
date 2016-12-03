package com.example.user.onedaynquestions.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.user.onedaynquestions.R;
import com.example.user.onedaynquestions.view.activity.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class PushReceiver extends FirebaseMessagingService{

    private static final String TAG = "PushReceiver";
    public String pushTitle = "Here comes new Question!";

    public PushReceiver() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remote){

        Log.d(TAG, "notice");
        pushNotification(remote.getData().get("question"));
    }

    private void pushNotification(String msg){
        System.out.println("Received message: " + msg);
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
        /*
        FLAG_UPDATE_CURRENT
        FLAG_CANCEL_CURRENT
        FLAG_NO_CREATE
        FLAG_ONE_SHOT
         */
        PendingIntent pending = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder noti = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.odnq_logo)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.odnq_logo_rect))
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                .setContentTitle(pushTitle)
                .setContentText(msg)
                .setAutoCancel(true)
                .setSound(sound)
                .setLights(050030255, 500, 2000)
                .setContentIntent(pending)
                .setOngoing(true);


        NotificationManager notiM = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakelock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
        wakelock.acquire();

        notiM.notify(0 /* ID of notification */, noti.build());

    }
}
