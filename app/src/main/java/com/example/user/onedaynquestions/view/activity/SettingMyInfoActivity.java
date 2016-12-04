package com.example.user.onedaynquestions.view.activity;

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

/**
 * Created by ymbaek on 2016-04-18.
 */
public class SettingMyInfoActivity extends AppCompatActivity {

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
//    EditText settingMyInfo_et_sex;
    EditText settingMyInfo_et_height;
    EditText settingMyInfo_et_weight;

    Spinner settingMyInfo_spinner_sex;

    TextView settingMyInfo_tv_deviceid;

    Button settingMyInfo_btn_save;

    MyInformation myInformation;
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
//        settingMyInfo_et_sex = (EditText) findViewById(R.id.setting_myinfo_et_sex);
        settingMyInfo_spinner_sex = (Spinner) findViewById(R.id.setting_myinfo_spinner_sex);
        settingMyInfo_et_height = (EditText) findViewById(R.id.setting_myinfo_et_height);
        settingMyInfo_et_weight = (EditText) findViewById(R.id.setting_myinfo_et_weight);

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
                (String[])getResources().getStringArray(R.array.spinner_list));
        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        settingMyInfo_spinner_sex.setAdapter(mSpinnerAdapter);

        //Toast.makeText(getApplicationContext(), "img_id: " + img_id, Toast.LENGTH_SHORT).show();

        myInformation = MainActivity.hereDB.getMyInformation();

        if (myInformation == null) {
            Toast.makeText(getApplicationContext(), "There is no my information", Toast.LENGTH_SHORT).show();
            settingMyInfo_iv_img.setImageResource(img_id_man);
            settingMyInfo_spinner_sex.setSelection(0);

        } else {
            settingMyInfo_tv_nick.setText(myInformation.getUserNick());
            settingMyInfo_tv_id.setText(myInformation.getUserId());
            settingMyInfo_tv_name.setText(myInformation.getUserName());

            settingMyInfo_et_id.setText(myInformation.getUserId());
            settingMyInfo_et_id.setEnabled(false);
            settingMyInfo_et_nick.setText(myInformation.getUserNick());
            settingMyInfo_et_name.setText(myInformation.getUserName());
            settingMyInfo_et_age.setText(String.valueOf(myInformation.getUserAge()));
//            settingMyInfo_et_sex.setText(String.valueOf(myInformation.getUserSex()));

            settingMyInfo_spinner_sex.setSelection(0);



            if (myInformation.getUserSex() == 1) {
                settingMyInfo_spinner_sex.setSelection(0);
            } else {
                settingMyInfo_spinner_sex.setSelection(1);
            }


            settingMyInfo_et_height.setText(String.valueOf(myInformation.getUserHeight()));
            settingMyInfo_et_weight.setText(String.valueOf(myInformation.getUserWeight()));

            settingMyInfo_tv_deviceid.setText(android_id);

            if (myInformation.getUserSex() == 2) {
                settingMyInfo_iv_img.setImageResource(img_id_woman);
            } else {
                settingMyInfo_iv_img.setImageResource(img_id_man);
            }
        }

        //Toast.makeText(getApplicationContext(), "android_id: " + android_id, Toast.LENGTH_SHORT).show();

    }



    private void clearEditText() {
        settingMyInfo_et_id.setText("");
        settingMyInfo_et_nick.setText("");
        settingMyInfo_et_name.setText("");
        settingMyInfo_et_age.setText("");
//        settingMyInfo_et_sex.setText("");
        settingMyInfo_et_height.setText("");
        settingMyInfo_et_weight.setText("");
    }


    public void mOnClick(View v) {
        switch (v.getId()) {
            case R.id.setting_myinfo_btn_save:
                MyInformation myInfo = new MyInformation();

                //TODO: Integer.parseInt에서 "" 처리
                String userId = settingMyInfo_et_id.getText().toString();
                String userNick = settingMyInfo_et_nick.getText().toString();
                String userName = settingMyInfo_et_name.getText().toString();

                int userAge;
                int userSex;
                int userHeight;
                int userWeight;

                if (!settingMyInfo_et_age.getText().toString().equals("")) {
                    userAge = Integer.parseInt(settingMyInfo_et_age.getText().toString());
                } else {
                    userAge = 20;
                }

                //settingMyInfo_spinner_sex.isSelected()
                if (settingMyInfo_spinner_sex.getSelectedItemPosition() == 0) {
                    userSex = 1;
                } else {
                    userSex = 2;
                }

//                if (!settingMyInfo_et_sex.getText().toString().equals("")) {
//                    userSex = Integer.parseInt(settingMyInfo_et_sex.getText().toString());
//                } else {
//                    userSex = 1;
//                }

                if (!settingMyInfo_et_height.getText().toString().equals("")) {
                    userHeight = Integer.parseInt(settingMyInfo_et_height.getText().toString());
                } else {
                    userHeight = 170;
                }

                if (!settingMyInfo_et_weight.getText().toString().equals("")) {
                    userWeight = Integer.parseInt(settingMyInfo_et_weight.getText().toString());
                } else {
                    userWeight = 65;
                }


                myInfo.setUserId(userId);
                myInfo.setUserNick(userNick);
                myInfo.setUserName(userName);
                myInfo.setUserAge(userAge);
                myInfo.setUserSex(userSex);
                myInfo.setUserHeight(userHeight);
                myInfo.setUserWeight(userWeight);
                myInfo.setUserRegistered(1);
                myInfo.setUserDeviceId(android_id);

                //Update database
                if (MainActivity.hereDB.getMyInformation() != null) {
                    Log.d(TAG_DB, "[DatabaseTest] User information already exists in DB.");
                    Log.d(TAG_DB, "[DatabaseTest] User information is updated.");
                    MainActivity.hereDB.updateMyInformation(myInfo);
                } else {
                    Log.d(TAG_DB, "[DatabaseTest] User information is added into DB.");
                    MainActivity.hereDB.insertMyInformation(myInfo);
                }


                initWidgetValues();

                Toast.makeText(getApplicationContext(), "My information is updated.", Toast.LENGTH_SHORT).show();


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
}
