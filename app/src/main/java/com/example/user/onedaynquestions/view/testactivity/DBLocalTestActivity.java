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
import com.example.user.onedaynquestions.model.MyInfo;
import com.example.user.onedaynquestions.utility.DatabaseController;
import com.example.user.onedaynquestions.view.activity.MainActivity;

/**
 * Created by ymbaek on 2016-04-18.
 */
public class DBLocalTestActivity extends AppCompatActivity {

    public static final String TAG = "MyAchievements";
    public static final String TAG_DB = "DBLocalTestActivity";

    public static DatabaseController odnqDB;

    private Toolbar toolbar;

    TextView dbtest_local_tv_status_myinfo;
    TextView dbtest_local_tv_status_mycard;
    TextView dbtest_local_tv_status_mygroup;

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
        odnqDB = new DatabaseController(getApplicationContext());

        if (odnqDB != null) {
            Log.d(TAG_DB, "[Database] DatabaseController is created.");
        }

        initWidgets();

    }





//

//

    public void mOnClick(View v) {
        switch (v.getId()) {
            case R.id.dbtest_local_btn_addinfo:

                MyInfo tmpMyInfo;

                String myinfo_id = dbtest_local_et_myinfo_id.getText().toString();
                String myinfo_nick = dbtest_local_et_myinfo_nick.getText().toString();
                String myinfo_name = dbtest_local_et_myinfo_name.getText().toString();
                int myinfo_age = Integer.parseInt(dbtest_local_et_myinfo_age.getText().toString());
                int myinfo_gender = Integer.parseInt(dbtest_local_et_myinfo_gender.getText().toString());
                String myinfo_deviceid = dbtest_local_et_myinfo_deviceid.getText().toString();

                tmpMyInfo = new MyInfo(myinfo_deviceid, myinfo_id, myinfo_nick, myinfo_name, myinfo_age, myinfo_gender);

                MainActivity.odnqDB.insertMyInfo(tmpMyInfo);
                Log.d(TAG_DB, "[DBLocalTestActivity] mOnClick: MyInfo is added to DB.");
                Toast.makeText(getApplicationContext(), "Table is updated.", Toast.LENGTH_SHORT).show();

                initDB();
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
