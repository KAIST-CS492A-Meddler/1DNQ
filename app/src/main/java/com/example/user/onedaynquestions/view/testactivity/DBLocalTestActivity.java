package com.example.user.onedaynquestions.view.testactivity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.onedaynquestions.R;
import com.example.user.onedaynquestions.model.MyCard;
import com.example.user.onedaynquestions.model.MyGroup;
import com.example.user.onedaynquestions.model.AsyncResponse;
import com.example.user.onedaynquestions.model.UnitRecord;
import com.example.user.onedaynquestions.utility.LocalDBController;
import com.example.user.onedaynquestions.utility.PostResponseAsyncTask;
import com.example.user.onedaynquestions.view.activity.MainActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ymbaek on 2016-04-18.
 */
public class DBLocalTestActivity extends AppCompatActivity implements AsyncResponse {

    public static final String TAG = "MyAchievements";
    public static final String TAG_DB = "DatabaseTestDBTag";

    public static LocalDBController odnqDB;

    private Toolbar toolbar;

    TextView dbtest_local_tv_status_myinfo;
    TextView dbtest_local_tv_status_mycard;
    TextView dbtest_local_tv_status_mygroup;
    TextView dbtest_local_tv_status_dailyrecord;

    EditText dbtest_local_et_myinfo_id;
    EditText dbtest_local_et_myinfo_nick;
    EditText dbtest_local_et_myinfo_name;
    EditText dbtest_local_et_myinfo_age;
    EditText dbtest_local_et_myinfo_gender;
    EditText dbtest_local_et_myinfo_deviceid;

    EditText dbtest_local_et_mycard_id;
    EditText dbtest_local_et_mycard_datetime;
    EditText dbtest_local_et_mycard_type;
    EditText dbtest_local_et_mycard_maker;
    EditText dbtest_local_et_mycard_group;
    EditText dbtest_local_et_mycard_question;
    EditText dbtest_local_et_mycard_answer;

    EditText dbtest_local_et_mygroup_id;
    EditText dbtest_local_et_mygroup_name;
    EditText dbtest_local_et_mygroup_regisdate;

    Button dbtest_local_btn_addinfo;
    Button dbtest_local_btn_addcard;
    Button dbtest_local_btn_addgroup;


    private void initWidgets() {
        dbtest_local_tv_status_myinfo = (TextView) findViewById(R.id.dbtest_local_tv_status_myinfo);
        dbtest_local_tv_status_mycard = (TextView) findViewById(R.id.dbtest_local_tv_status_mycard);
        dbtest_local_tv_status_mygroup = (TextView) findViewById(R.id.dbtest_local_tv_status_mygroup);
        dbtest_local_tv_status_dailyrecord = (TextView) findViewById(R.id.dbtest_local_tv_status_mydailyrecord);

        dbtest_local_et_myinfo_id = (EditText) findViewById(R.id.dbtest_local_et_myinfo_id);
        dbtest_local_et_myinfo_nick = (EditText) findViewById(R.id.dbtest_local_et_myinfo_nick);
        dbtest_local_et_myinfo_name = (EditText) findViewById(R.id.dbtest_local_et_myinfo_name);
        dbtest_local_et_myinfo_age = (EditText) findViewById(R.id.dbtest_local_et_myinfo_age);
        dbtest_local_et_myinfo_gender = (EditText) findViewById(R.id.dbtest_local_et_myinfo_gender);
        dbtest_local_et_myinfo_deviceid = (EditText) findViewById(R.id.dbtest_local_et_myinfo_deviceid);

        dbtest_local_et_mycard_id = (EditText) findViewById(R.id.dbtest_local_et_mycard_id);
        dbtest_local_et_mycard_datetime = (EditText) findViewById(R.id.dbtest_local_et_mycard_datetime);
        dbtest_local_et_mycard_type = (EditText) findViewById(R.id.dbtest_local_et_mycard_type);
        dbtest_local_et_mycard_maker = (EditText) findViewById(R.id.dbtest_local_et_mycard_maker);
        dbtest_local_et_mycard_group = (EditText) findViewById(R.id.dbtest_local_et_mycard_group);
        dbtest_local_et_mycard_question = (EditText) findViewById(R.id.dbtest_local_et_mycard_question);
        dbtest_local_et_mycard_answer = (EditText) findViewById(R.id.dbtest_local_et_mycard_answer);

        dbtest_local_et_mygroup_id = (EditText) findViewById(R.id.dbtest_local_et_mygroup_id);
        dbtest_local_et_mygroup_name = (EditText) findViewById(R.id.dbtest_local_et_mygroup_name);
        dbtest_local_et_mygroup_regisdate = (EditText) findViewById(R.id.dbtest_local_et_mygroup_regisdate);

        dbtest_local_btn_addinfo = (Button) findViewById(R.id.dbtest_local_btn_addinfo);
        dbtest_local_btn_addcard = (Button) findViewById(R.id.dbtest_local_btn_addcard);
        dbtest_local_btn_addgroup = (Button) findViewById(R.id.dbtest_local_btn_addgroup);

        initDB();
    }

    private void initDB() {
        dbtest_local_tv_status_myinfo.setText("table MyInfo status: "
                + "\n\t-size: " + MainActivity.odnqDB.countTableMyInfo());
        dbtest_local_tv_status_mycard.setText("table MyCard status: "
                + "\n\t-size: " + MainActivity.odnqDB.countTableMyCard());
        dbtest_local_tv_status_mygroup.setText("table MyGroup status: "
                + "\n\t-size: " + MainActivity.odnqDB.countTableMyGroup());
        dbtest_local_tv_status_dailyrecord.setText("table DailyRecord status: "
                + "\n\t-size: " + MainActivity.odnqDB.countTableDailyRecord());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testdb_local);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("Database Test: Local(Client) Side");
        toolbar.setNavigationIcon(R.mipmap.ic_menu_arrow_back);
        toolbar.setTitleTextColor(Color.WHITE);

        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("Database Test: Local(Client) Side");
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);


        /* DATABASE */
        odnqDB = new LocalDBController(getApplicationContext());

        if (odnqDB != null) {
            Log.d(TAG_DB, "[Database] LocalDBController is created.");
        }

        initWidgets();

    }





//

//

    public void mOnClick(View v) {
        switch (v.getId()) {
            case R.id.dbtest_local_btn_drop_myinfo:
                MainActivity.odnqDB.dropTableMyInfo();
                Log.d(TAG_DB, "MyInfo table is dropped.");
                break;
            case R.id.dbtest_local_btn_drop_mygroup:
                MainActivity.odnqDB.dropTableMyGroup();
                Log.d(TAG_DB, "MyGroup table is dropped.");
                break;
            case R.id.dbtest_local_btn_drop_mycard:
                MainActivity.odnqDB.dropTableMyCard();
                Log.d(TAG_DB, "MyCard table is dropped.");
                break;
            case R.id.dbtest_local_btn_drop_dailyrecord:
                MainActivity.odnqDB.dropTableDailyRecord();
                Log.d(TAG_DB, "DailyRecord table is dropped.");
                break;

            case R.id.dbtest_local_btn_addinfo:

//                MyInfo tmpMyInfo;
//
//                String myinfo_id = dbtest_local_et_myinfo_id.getText().toString();
//                String myinfo_nick = dbtest_local_et_myinfo_nick.getText().toString();
//                String myinfo_name = dbtest_local_et_myinfo_name.getText().toString();
//                int myinfo_age = Integer.parseInt(dbtest_local_et_myinfo_age.getText().toString());
//                int myinfo_gender = Integer.parseInt(dbtest_local_et_myinfo_gender.getText().toString());
//                String myinfo_deviceid = dbtest_local_et_myinfo_deviceid.getText().toString();
//
//                tmpMyInfo = new MyInfo(myinfo_deviceid, myinfo_id, myinfo_nick, myinfo_name, myinfo_age, myinfo_gender);
//
//                MainActivity.odnqDB.insertMyInfo(tmpMyInfo);
//                Log.d(TAG_DB, "[DBLocalTestActivity] mOnClick: MyInfo is added to DB.");
//                Toast.makeText(getApplicationContext(), "Table is updated.", Toast.LENGTH_SHORT).show();
//
//                initDB();

//                EditText dbtest_local_et_myinfo_id;
//                EditText dbtest_local_et_myinfo_nick;
//                EditText dbtest_local_et_myinfo_name;
//                EditText dbtest_local_et_myinfo_age;
//                EditText dbtest_local_et_myinfo_gender;
//                EditText dbtest_local_et_myinfo_deviceid;

//                //Server request
//                HashMap postData = new HashMap();
//                postData.put("myinfo_id", "SampleId");
//                postData.put("myinfo_nick","SampleNick");
//                postData.put("myinfo_name","SampleName");
//                postData.put("myinfo_age",25);
//                postData.put("myinfo_gender",2);
//                postData.put("myinfo_deviceid","SampleDeviceId");
//                postData.put("myinfo_token", "dagEgIerjYo:APA91bFEttcM3VHC0kyltss3HwY1N4PVJ28FSmzFVWzCoqwHYoEmxONfGhesrV23oTLf5bYU18y0PHDoPDy1fhdzV81YayH1R1SESAVE0z6rNXcVspmp0-sb7x1yrOR-uufk2ftTR8-y");
////                postData.put("myinfo_token", "SampleToken");
//
//                PostResponseAsyncTask insertMyInfoTask =
//                        new PostResponseAsyncTask(DBLocalTestActivity.this, postData);
//
//                insertMyInfoTask.execute("http://110.76.95.150/create_user.php");
////                insertMyInfoTask.execute("http://110.76.95.150/create_user_t.php");

                //Server request
                HashMap postData = new HashMap();
                postData.put("sample_id", "SampleId5673");
//                postData.put("myinfo_token", "SampleToken");

                PostResponseAsyncTask insertMyInfoTask =
                        new PostResponseAsyncTask(DBLocalTestActivity.this, postData);

                insertMyInfoTask.execute("http://110.76.95.150/create_sample.php");
//                insertMyInfoTask.execute("http://110.76.95.150/create_user_t.php");


                break;
            case R.id.dbtest_local_btn_addcard:
                MyCard tmpMyCard;

                String mycard_id = dbtest_local_et_mycard_id.getText().toString();
                String mycard_datetime = dbtest_local_et_mycard_datetime.getText().toString();
                int mycard_type = Integer.parseInt(dbtest_local_et_mycard_type.getText().toString());
                String mycard_maker = dbtest_local_et_mycard_maker.getText().toString();
                String mycard_group = dbtest_local_et_mycard_group.getText().toString();
                String mycard_question = dbtest_local_et_mycard_question.getText().toString();
                String mycard_answer = dbtest_local_et_mycard_answer.getText().toString();

                tmpMyCard = new MyCard(mycard_id, mycard_datetime, mycard_type, mycard_maker, mycard_group, mycard_question, mycard_answer);

                MainActivity.odnqDB.insertMyCard(tmpMyCard);
                Log.d(TAG_DB, "[DBLocalTestActivity] mOnClick: MyCard is added to DB.");
                Toast.makeText(getApplicationContext(), "Table is updated.", Toast.LENGTH_SHORT).show();

                initDB();
                break;
            case R.id.dbtest_local_btn_addgroup:
                MyGroup tmpMyGroup;

                String mygroup_id = dbtest_local_et_mygroup_id.getText().toString();
                String mygroup_name = dbtest_local_et_mygroup_name.getText().toString();
                String mygroup_regisdate = dbtest_local_et_mygroup_regisdate.getText().toString();

                tmpMyGroup = new MyGroup(mygroup_id, mygroup_name, mygroup_regisdate);

                MainActivity.odnqDB.insertMyGroup(tmpMyGroup);
                Log.d(TAG_DB, "[DBLocalTestActivity] mOnClick: MyGroup is added to DB.");
                Toast.makeText(getApplicationContext(), "Table is updated.", Toast.LENGTH_SHORT).show();

                initDB();
                break;
            case R.id.dbtest_local_btn_addrecord:
                UnitRecord ur1 = new UnitRecord("2016-12-12 00:00:00", 200, 3, 5);
                UnitRecord ur2 = new UnitRecord("2016-12-12 01:00:00", 240, 1, 2);
                UnitRecord ur3 = new UnitRecord("2016-12-12 02:00:00", 250, 8, 1);
                UnitRecord ur4 = new UnitRecord("2016-12-12 03:00:00", 285, 15, 3);
                UnitRecord ur5 = new UnitRecord("2016-12-12 04:00:00", 360, 4, 7);
                UnitRecord ur6 = new UnitRecord("2016-12-12 05:00:00", 365, 2, 6);

                MainActivity.odnqDB.insertDailyRecord(ur1);
                MainActivity.odnqDB.insertDailyRecord(ur2);
                MainActivity.odnqDB.insertDailyRecord(ur3);
                MainActivity.odnqDB.insertDailyRecord(ur4);
                MainActivity.odnqDB.insertDailyRecord(ur5);
                MainActivity.odnqDB.insertDailyRecord(ur6);

                Log.d(TAG_DB, "Unit Records are added to DB");

                break;
            case R.id.dbtest_local_btn_getrecord:
                ArrayList<UnitRecord> dailyRecords;

                dailyRecords = MainActivity.odnqDB.getMyDailyRecords();

                for(int i = 0; i < dailyRecords.size(); i++) {
                    Log.d(TAG_DB, "[" + i + "] - datetime: " + dailyRecords.get(i).getDailyRecordDateTime());
                    Log.d(TAG_DB, "[" + i + "] - contribution: " + dailyRecords.get(i).getDailyRecordContribution());
                    Log.d(TAG_DB, "[" + i + "] - answerright: " + dailyRecords.get(i).getDailyRecordStudyRight());
                    Log.d(TAG_DB, "[" + i + "] - answerwrong: " + dailyRecords.get(i).getDailyRecordStudyWrong());
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
        Toast.makeText(getApplicationContext(), output, Toast.LENGTH_SHORT).show();
        Log.d("PostResponseAsyncTask", "[DBLocalTestActivity] processFinish(): \n" + output);
    }
}
