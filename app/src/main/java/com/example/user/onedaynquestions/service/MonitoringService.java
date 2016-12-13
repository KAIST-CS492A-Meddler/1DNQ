package com.example.user.onedaynquestions.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import com.example.user.onedaynquestions.model.AsyncResponse;
import com.example.user.onedaynquestions.model.MyInfo;
import com.example.user.onedaynquestions.model.UnitRecord;
import com.example.user.onedaynquestions.utility.PostResponseAsyncTask;
import com.example.user.onedaynquestions.view.activity.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

public class MonitoringService extends Service implements AsyncResponse {
    private int dt = 5000, totalTime = 180 * 60 * 1000; //millisecond
    private long counter = 0;

    public MonitoringService() {
    }

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub

        CountDownTimer watchDog = new CountDownTimer(totalTime, dt) {
            @Override
            public void onTick(long l) {
//                Log.d("Watch Dog","" + counter++);
                Log.d("MonitoringService", "Monitoring" + counter++);

                //TODO: Make a DB-insert statement (ymbaek)

                if (MainActivity.odnqDB != null) {
                    if (MainActivity.odnqDB.getMyInfo() != null) {
                        HashMap postData = new HashMap();
                        postData.put("userinfo_id", MainActivity.odnqDB.getMyInfo().getMyInfoId());

                        PostResponseAsyncTask getUserTask =
                                new PostResponseAsyncTask(MonitoringService.this, postData);

                        getUserTask.execute("http://110.76.95.150/get_user.php");

                    } else {
                        onFinish();
                    }

                }
            }

            @Override
            public void onFinish() {
                counter = 0;
                this.start();
            }
        }.start();

        // service will be restarted automamtically
        //https://developer.android.com/reference/android/app/Service.html#START_STICKY
        return START_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        // TODO Auto-generated method stub
        Intent restartService = new Intent(getApplicationContext(),
                this.getClass());
        restartService.setPackage(getPackageName());
        PendingIntent restartServicePI = PendingIntent.getService(
                getApplicationContext(), 1, restartService,
                PendingIntent.FLAG_ONE_SHOT);

        //Restart the service once it has been killed android


        AlarmManager alarmService = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmService.set(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 100, restartServicePI);

    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();

        //start a separate thread and start listening to your network object
    }

    @Override
    public void processFinish(String output) {
        if (output.contains("{\"result_user\":")) {
//            MyInfo myInfoFromServer = odnqDB.getMyInfo();

            String jsonString = output.replace("{\"result_user\":", "");
            jsonString = jsonString.substring(0, jsonString.length() - 1);

            Log.d("MainActivity_getuser", "output: " + output);

            UnitRecord unitRecord = new UnitRecord();
            DateFormat dateFormat;
            JSONArray jarray = null;
            try {
                jarray = new JSONArray(jsonString);
                JSONObject jObject = jarray.getJSONObject(0);

                dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());



                Date date = new Date();

                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DATE, (-7 + (int)counter));


                if (MainActivity.odnqDB.getMyInfo() != null) {
                    Random random = new Random();

                    unitRecord.setDailyRecordDateTime(dateFormat.format(calendar.getTime()));
//                    unitRecord.setDailyRecordContribution(jObject.getInt("user_exp"));
                    int random400 = random.nextInt(400);
                    unitRecord.setDailyRecordContribution(random400);
                    unitRecord.setDailyRecordStudyRight(MainActivity.odnqDB.getMyInfo().getMyInfoAnswerRight());
                    unitRecord.setDailyRecordStudyWrong(MainActivity.odnqDB.getMyInfo().getMyInfoAnswerWrong());

                    MainActivity.odnqDB.insertDailyRecord(unitRecord);
                    Log.d("DailyRecord", "A unit record is inserted into DB.");
                    Log.d("DailyRecord", "- Date: " + dateFormat.format(date));
                    Log.d("DailyRecord", "- Contribution: " + random400);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}