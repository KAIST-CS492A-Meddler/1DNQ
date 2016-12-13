package com.example.user.onedaynquestions.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.onedaynquestions.R;
import com.example.user.onedaynquestions.archive.MyInformation;
import com.example.user.onedaynquestions.model.AsyncResponse;
import com.example.user.onedaynquestions.model.MyInfo;
import com.example.user.onedaynquestions.service.FloatingButtonService;
import com.example.user.onedaynquestions.service.MonitoringService;
import com.example.user.onedaynquestions.utility.PostResponseAsyncTask;
import com.example.user.onedaynquestions.view.testactivity.DBLocalTestActivity;
import com.example.user.onedaynquestions.view.testactivity.DBServerTestActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;

/**
 * Created by ymbaek on 2016-04-18.
 */
public class SettingMyInfoActivity extends AppCompatActivity implements AsyncResponse {

    public static final String TAG = "SettingMyInfoActivity";
    public static final String TAG_DB = "DatabaseTestDBTag";

    private Toolbar toolbar;

    ImageView settingMyInfo_iv_img;

    TextView settingMyInfo_tv_nick;
    TextView settingMyInfo_tv_id;
    TextView settingMyInfo_tv_name;

    EditText settingMyInfo_et_id;
    EditText settingMyInfo_et_nick;
    EditText settingMyInfo_et_name;
    EditText settingMyInfo_et_age;

    Spinner settingMyInfo_spinner_sex;
    TextView settingMyInfo_tv_deviceid;
    Button settingMyInfo_btn_save;

    MyInfo myInfo;

    boolean isUserInfoInServer;

    //    MyInformation myInformation;
    String android_id;

    private ArrayAdapter<String> mSpinnerAdapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_myinfo);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("My Information");
        toolbar.setNavigationIcon(R.mipmap.ic_menu_arrow_back);
        toolbar.setTitleTextColor(Color.WHITE);

        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("My Information");
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);

        isUserInfoInServer = false;

        initWidgets();

        initWidgetValues();
    }


    private void initWidgets() {
        settingMyInfo_iv_img = (ImageView) findViewById(R.id.setting_myinfo_iv_character);

        settingMyInfo_tv_nick = (TextView) findViewById(R.id.setting_myinfo_tv_nick);
        settingMyInfo_tv_id = (TextView) findViewById(R.id.setting_myinfo_tv_id);
        settingMyInfo_tv_name = (TextView) findViewById(R.id.setting_myinfo_tv_name);

        settingMyInfo_et_id = (EditText) findViewById(R.id.setting_myinfo_et_id);
        settingMyInfo_et_nick = (EditText) findViewById(R.id.setting_myinfo_et_nick);
        settingMyInfo_et_name = (EditText) findViewById(R.id.setting_myinfo_et_name);
        settingMyInfo_et_age = (EditText) findViewById(R.id.setting_myinfo_et_age);
        settingMyInfo_spinner_sex = (Spinner) findViewById(R.id.setting_myinfo_spinner_gender);

        settingMyInfo_tv_deviceid = (TextView) findViewById(R.id.setting_myinfo_tv_deviceid);

        settingMyInfo_btn_save = (Button) findViewById(R.id.setting_myinfo_btn_save);
    }

    private void initWidgetValues() {
        //Image src
//        String imgSrc_man = "here_character_simple_boy.png";
//        String imgSrc_man = "here_logo.png";


        //Android device id
        android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        String imgSrc_uri_man = "@drawable/here_character_simple_boy";
        String imgSrc_uri_woman = "@drawable/here_character_simple_girl";

        int img_id_man = getResources().getIdentifier(imgSrc_uri_man, null, getPackageName());
        int img_id_woman = getResources().getIdentifier(imgSrc_uri_woman, null, getPackageName());

        mSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                (String[])getResources().getStringArray(R.array.spinner_gender));
        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        settingMyInfo_spinner_sex.setAdapter(mSpinnerAdapter);

        settingMyInfo_tv_deviceid.setText(android_id);

        myInfo = MainActivity.odnqDB.getMyInfo();

        if (myInfo == null) {
            Log.d(TAG_DB, "[SettingMyInfoActivity] There is no my information.");
            settingMyInfo_iv_img.setImageResource(img_id_man);
            settingMyInfo_spinner_sex.setSelection(0);
        } else {
            settingMyInfo_tv_nick.setText(myInfo.getMyInfoNick());
            settingMyInfo_tv_id.setText(myInfo.getMyInfoId());
            settingMyInfo_tv_name.setText(myInfo.getMyInfoName());

            settingMyInfo_et_id.setText(myInfo.getMyInfoId());
            settingMyInfo_et_id.setEnabled(false);
            settingMyInfo_et_nick.setText(myInfo.getMyInfoNick());
            settingMyInfo_et_name.setText(myInfo.getMyInfoName());
            settingMyInfo_et_age.setText(myInfo.getMyInfoAge() + "");

            if (myInfo.getMyInfoGender() == 0) {
                settingMyInfo_spinner_sex.setSelection(0);
                settingMyInfo_iv_img.setImageResource(img_id_man);
            } else {
                settingMyInfo_spinner_sex.setSelection(1);
                settingMyInfo_iv_img.setImageResource(img_id_woman);
            }

            settingMyInfo_tv_deviceid.setText(android_id);
        }

    }



    private void clearEditText() {
        settingMyInfo_et_id.setText("");
        settingMyInfo_et_nick.setText("");
        settingMyInfo_et_name.setText("");
        settingMyInfo_et_age.setText("");
        settingMyInfo_spinner_sex.setSelection(0);
    }




    public void mOnClick(View v) {
        switch (v.getId()) {
            case R.id.setting_myinfo_btn_save:


                String curMyInfo_id = "";
                String curMyInfo_nick = "";
                String curMyInfo_name = "";
                int curMyInfo_age = -1;
                int curMyInfo_gender = 1;
                String curMyInfo_deviceid = "";

                curMyInfo_id = settingMyInfo_et_id.getText().toString();
                curMyInfo_nick = settingMyInfo_et_nick.getText().toString();
                curMyInfo_name = settingMyInfo_et_name.getText().toString();
                if (settingMyInfo_et_age.getText().toString().length() != 0)
                    curMyInfo_age = Integer.parseInt(settingMyInfo_et_age.getText().toString());
                curMyInfo_gender = settingMyInfo_spinner_sex.getSelectedItemPosition();
                curMyInfo_deviceid = settingMyInfo_tv_deviceid.getText().toString();


                if (curMyInfo_id.length() != 0 && curMyInfo_nick.length() != 0 && curMyInfo_name.length() != 0) {

                    /** SERVER DB */

                    //TODO: Server DB에 User information(user_id)이 있는지 검사하는 branch
                    HashMap getPostData = new HashMap();
                    getPostData.put("userinfo_id", curMyInfo_id);

                    PostResponseAsyncTask getUserTask =
                            new PostResponseAsyncTask(SettingMyInfoActivity.this, getPostData);

                    getUserTask.execute("http://110.76.95.150/get_user.php");

                    if (!isUserInfoInServer) {
                        //Server request
                        HashMap postData = new HashMap();

                        postData.put("myinfo_id", curMyInfo_id);
                        postData.put("myinfo_nick", curMyInfo_nick);
                        postData.put("myinfo_name", curMyInfo_name);
                        postData.put("myinfo_age", curMyInfo_age + "");
                        postData.put("myinfo_gender", curMyInfo_gender + "");
                        postData.put("myinfo_deviceid", curMyInfo_deviceid);
                        postData.put("myinfo_token", MainActivity.token);

                        PostResponseAsyncTask insertMyInfoTask =
                                new PostResponseAsyncTask(SettingMyInfoActivity.this, postData);

                        insertMyInfoTask.execute("http://110.76.95.150/create_user2.php");
                    } else {
                        //Server request
                        HashMap postData = new HashMap();

                        postData.put("myinfo_id", curMyInfo_id);
                        postData.put("myinfo_nick", curMyInfo_nick);
                        postData.put("myinfo_name", curMyInfo_name);
                        postData.put("myinfo_age", curMyInfo_age + "");
                        postData.put("myinfo_gender", curMyInfo_gender + "");
                        postData.put("myinfo_deviceid", curMyInfo_deviceid);
                        postData.put("myinfo_token", MainActivity.token);

                        PostResponseAsyncTask updateMyInfoTask =
                                new PostResponseAsyncTask(SettingMyInfoActivity.this, postData);

                        updateMyInfoTask.execute("http://110.76.95.150/update_user.php");
                    }




                    /** LOCAL DB */

                    MyInfo tmpMyInfo = new MyInfo();

                    tmpMyInfo.setMyInfoId(curMyInfo_id);
                    tmpMyInfo.setMyInfoNick(curMyInfo_nick);
                    tmpMyInfo.setMyInfoName(curMyInfo_name);
                    tmpMyInfo.setMyInfoAge(curMyInfo_age);
                    tmpMyInfo.setMyInfoGender(curMyInfo_gender);
                    tmpMyInfo.setMyInfoDeviceId(curMyInfo_deviceid);
                    tmpMyInfo.setMyInfoToken(MainActivity.token);

                    if (MainActivity.odnqDB.getMyInfo() != null) {
                        Log.d(TAG_DB, "[SettingMyInfoActivity] User information already exists in DB.");
                        Log.d(TAG_DB, "[SettingMyInfoActivity] User information is updated");

                        MainActivity.odnqDB.updateMyInfo(tmpMyInfo);
                    } else {
                        Log.d(TAG_DB, "[SettingMyInfoActivity] User information is added into DB.");
                        MainActivity.odnqDB.insertMyInfo(tmpMyInfo);
                        Log.d(TAG_DB, "[SettingMyInfoActivity] User information is added into DB.");

                        if (!MainActivity.isMonitoringServiceOn) {
                            startService(new Intent(this, MonitoringService.class));
                            Log.d(TAG_DB, "[SettingMyInfoActivity] Monitoring service is started.");
                        }

                    }

                    initWidgetValues();

                } else {
                    Toast.makeText(getApplicationContext(), "Please fill out mandatory information", Toast.LENGTH_SHORT).show();
                }


                break;
        }
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
        String temp = output.replaceAll("<br>", "\n");
        Log.d("JSONParser", "[DBServerTestActivity] output: " + output);

        // PARSE USER
        if (output.contains("{\"result_user\":")) {
            String jsonString = output.replace("{\"result_user\":", "");
            jsonString = jsonString.substring(0, jsonString.length() - 1);

            try {
                JSONArray jarray = new JSONArray(jsonString);

                if (jarray.length() != 0) {
                    Log.d("JSONParser", "[DBServerTestActivity] There is a user with the same user_id.");
                    isUserInfoInServer = true;
                } else {
                    Log.d("JSONParser", "[DBServerTestActivity] This user_id is available.");
                    isUserInfoInServer = false;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (output.contains("Account created")) {
            Log.d("JSONParser", "[DBServerTestActivity] (processFinish) Account created");
            Toast.makeText(getApplicationContext(), "An account is created.", Toast.LENGTH_SHORT).show();
        } else if (output.contains("Account updated")) {
            Log.d("JSONParser", "[DBServerTestActivity] (processFinish) Account updated");
            Toast.makeText(getApplicationContext(), "An account is updated.", Toast.LENGTH_SHORT).show();
        }


//        Toast.makeText(getApplicationContext(), "Information is updated.", Toast.LENGTH_SHORT).show();
//        Toast.makeText(getApplicationContext(), "temp.length(): " + temp.length(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(getApplicationContext(), temp, Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onResume() {
        stopService(new Intent(this, FloatingButtonService.class));

        super.onResume();
    }
}
