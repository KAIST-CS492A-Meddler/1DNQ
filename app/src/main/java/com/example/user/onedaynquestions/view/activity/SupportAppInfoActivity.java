package com.example.user.onedaynquestions.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.user.onedaynquestions.R;
import com.example.user.onedaynquestions.model.AsyncResponse;
import com.example.user.onedaynquestions.utility.PostResponseAsyncTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by ymbaek on 2016-04-18.
 */
public class SupportAppInfoActivity extends AppCompatActivity implements AsyncResponse {

    public static final String TAG = "SupportAppInfoActivity";

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_appinfo);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("App Information");
        toolbar.setNavigationIcon(R.mipmap.ic_menu_arrow_back);
        toolbar.setTitleTextColor(Color.WHITE);

        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("App Information");
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);

        recordUserLog("SupportAppInfoActivity", "onCreate");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
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
                new PostResponseAsyncTask(SupportAppInfoActivity.this, postData);

        createUserLogTask.execute("http://110.76.95.150/create_userlog.php");
        Log.d("USER_LOG", "[" + logTimestamp + "] " + logCurActivity + " - " + logCurEvent);
    }
}
