package com.example.user.onedaynquestions.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.user.onedaynquestions.R;
import com.example.user.onedaynquestions.archive.MyRoutine;
import com.example.user.onedaynquestions.model.AsyncResponse;
import com.example.user.onedaynquestions.service.FloatingButtonService;
import com.example.user.onedaynquestions.utility.PostResponseAsyncTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Created by user on 2016-06-07.
 */
public class NewGroupActivity extends AppCompatActivity implements AsyncResponse {

    MyRoutine selectedRoutine;

    public static Activity thisActivity;
//    ImageView startexercise_iv_girl;
//    ImageView startexercise_iv_start;
//
//    TextView tv_routine_name;
//    TextView tv_routine_eq1;
//    TextView tv_routine_eq2;
//    TextView tv_routine_eq3;
//    TextView tv_routine_eq4;
//    TextView tv_routine_eq5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newgroup);

        Intent intent = getIntent();
        selectedRoutine = (MyRoutine) intent.getSerializableExtra("selectedRoutine");

        recordUserLog("NewGroupActivity", "onCreate");


        initWidgets();

        if (selectedRoutine == null) {
            Toast.makeText(getApplicationContext(), "Routine is not set.", Toast.LENGTH_SHORT).show();
        }

        initWidetValues();

        thisActivity = this;


    }


    private void initWidgets() {

    }

    private void initWidetValues() {

    }


    public void mOnClick(View v) {
    }

    @Override
    protected void onResume() {
        stopService(new Intent(this, FloatingButtonService.class));

        super.onResume();
    }

    @Override
    public void processFinish(String output) {

    }

    public void recordUserLog(String argActivity, String argEvent) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
//        Toast.makeText(getApplicationContext(), "current time: " + dateFormat.format(calendar.getTime()), Toast.LENGTH_SHORT).show();

        String logTimestamp = dateFormat.format(calendar.getTime());
        int logDuration = 0;
        String logCurActivity = "";
        if (argActivity == null || argActivity.equals("")) {
            logCurActivity = "unknown";
        } else {
            logCurActivity = argActivity;
        }
        String logCurEvent = "";
        if (argEvent == null || argEvent.equals("")) {
            logCurEvent = "unknown";
        } else {
            logCurEvent = argEvent;
        }

        HashMap postData = new HashMap();

        if (MainActivity.odnqDB != null) {
            if (MainActivity.odnqDB.getMyInfo() != null) {
                postData.put("userinfo_id", MainActivity.odnqDB.getMyInfo().getMyInfoId());
            } else {
                postData.put("userinfo_id", "unknown");
            }
        } else {
            postData.put("userinfo_id", "unknown");
        }
        postData.put("log_timestamp", logTimestamp);
        postData.put("log_duration", logDuration+"");
        postData.put("log_curactivity", logCurActivity);
        postData.put("log_curevent", logCurEvent);

//        postData.put("userinfo_id", "ididididid");
//        postData.put("log_timestamp", "timetime");
//        postData.put("log_duration", "duration");
//        postData.put("log_curactivity", "activity");
//        postData.put("log_curevent", "event");

        PostResponseAsyncTask createUserLogTask =
                new PostResponseAsyncTask(NewGroupActivity.this, postData);

        createUserLogTask.execute("http://110.76.95.150/create_userlog.php");
        Log.d("USER_LOG", "[" + logTimestamp + "] " + logCurActivity + " - " + logCurEvent);
    }
}
