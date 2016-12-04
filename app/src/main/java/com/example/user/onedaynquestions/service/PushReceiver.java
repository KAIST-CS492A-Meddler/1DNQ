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
import com.example.user.onedaynquestions.view.testactivity.DBServerTestActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class PushReceiver extends FirebaseMessagingService{

    private static final String TAG = "PushReceiver";
    private static final String pushTitle = "Here comes new Question!";

    private String question;
    private String answer;
    public PushReceiver() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remote){

        Log.d(TAG, "notice");
        question = remote.getData().get("question");
        answer = remote.getData().get("answer");
        Intent intent = new Intent(".service.PushReceiver");
        intent.putExtra("question", question);
        intent.putExtra("answer", answer);
        sendBroadcast(intent);
        pushNotification(question);
    }

    private void pushNotification(String msg){
        System.out.println("Received message: " + msg);
        Intent intent = new Intent(this, DBServerTestActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
        /*
        FLAG_UPDATE_CURRENT
        FLAG_CANCEL_CURRENT
        FLAG_NO_CREATE
        FLAG_ONE_SHOT
         */
        PendingIntent pending = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder noti = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.odnq_app_icon_bulb)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.odnq_app_icon))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentTitle(pushTitle)
                .setContentText(msg)
                .setAutoCancel(true)
                .setSound(sound)
                .setLights(050030255, 500, 2000)
                .setContentIntent(pending);


        NotificationManager notiM = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakelock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
        wakelock.acquire();

        notiM.notify(0 /* ID of notification */, noti.build());

    }
}
