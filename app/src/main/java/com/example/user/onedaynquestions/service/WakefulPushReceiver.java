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
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.widget.Toast;

import com.example.user.onedaynquestions.R;
import com.example.user.onedaynquestions.model.MyCard;
import com.example.user.onedaynquestions.view.activity.CardSolvingActivity;

import java.util.ArrayList;

//To handle FCM in manual way
public class WakefulPushReceiver  extends WakefulBroadcastReceiver {
    private static ArrayList<MyCard> receivedQuestions = new ArrayList<>();
    private static int count = 0;

    private static final String TAG = "WakefulPushReceiver";
    private static final String pushTitle = "Here comes new Question!";

    private static final String ACTION_REGISTRATION
            = "com.google.android.c2dm.intent.REGISTRATION";
    private static final String ACTION_RECEIVE
            = "com.google.android.c2dm.intent.RECEIVE";


    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        for (String key : intent.getExtras().keySet()) {
            //get all extras in the intent
        }
        switch (action) {
            case ACTION_REGISTRATION:
                break;

            case ACTION_RECEIVE:
                onMessageReceived(context, intent);
                break;

            default:
        }
        abortBroadcast();
    }


    public void onMessageReceived(Context context, Intent intent){
        String id = intent.getStringExtra("card_id");
        Log.d(TAG, "notice");
        if(id != null) {
            if (id.compareTo("1") == 0) {
                WakefulPushReceiver.addReceivedQuestion(intent);
                pushNotification(context, intent.getStringExtra("question"));
                Toast.makeText
                        (context, "A new Question card has arrived!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void pushNotification(Context context, String msg){
        System.out.println("Received message: " + msg);
        Intent intent = new Intent(context, CardSolvingActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
        /*
        FLAG_UPDATE_CURRENT
        FLAG_CANCEL_CURRENT
        FLAG_NO_CREATE
        FLAG_ONE_SHOT
         */
        PendingIntent pending = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder noti = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.odnq_app_icon_bulb)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.odnq_app_icon))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentTitle(pushTitle)
                .setContentText(msg)
                .setAutoCancel(true)
                .setSound(sound)
                .setLights(050030255, 500, 2000)
                .setContentIntent(pending);


        NotificationManager notiM = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakelock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
        wakelock.acquire();

        notiM.notify(0 /* ID of notification */, noti.build());

    }


    public static void addReceivedQuestion(Intent intent){
        //MyCard(String myCardId, String myCardDateTime, int myCardType, String myCardMaker, String myCardGroup, String myCardQuestion, String myCardAnswer) {
        receivedQuestions.add(new MyCard(intent));
    }

    public static void clearReceivedQuestions(){
        receivedQuestions.clear();
    }

    public static boolean isEmpty(){
        return receivedQuestions.isEmpty();
    }
    public static MyCard consumeReceivedQuestions(){
        MyCard temp =null;
        if(!receivedQuestions.isEmpty()){
            temp = receivedQuestions.remove(0);
        }
        return temp;
    }
    public static MyCard getReceivedQuestions(int id){
        MyCard temp =null;
        if(receivedQuestions.size() > id){
            temp = receivedQuestions.get(id);
        }
        return temp;
    }
    public static MyCard getReceivedQuestion(){
        MyCard temp =null;
        if(receivedQuestions.isEmpty()){
            return null;
        }else {
            if (receivedQuestions.size() > count) {
                temp = receivedQuestions.get(count++);
            } else {
                count = 0;
                temp = receivedQuestions.get(count++);
            }
            return temp;
        }
    }
    public static ArrayList<MyCard> getAllReceivedQuestions(){
        return receivedQuestions;
    }

    public static int numReceivedQuestions(){
        return receivedQuestions.size();
    }
}