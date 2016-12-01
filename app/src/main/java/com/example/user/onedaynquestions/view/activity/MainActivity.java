package com.example.user.onedaynquestions.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.onedaynquestions.R;
import com.example.user.onedaynquestions.controller.PagerAdapter;
import com.example.user.onedaynquestions.model.MyHereAgent;
import com.example.user.onedaynquestions.model.MyInformation;
import com.example.user.onedaynquestions.model.MyRecord;
import com.example.user.onedaynquestions.model.MyRoutine;
import com.example.user.onedaynquestions.utility.DatabaseHelper;
import com.example.user.onedaynquestions.view.fragment.SupportHelpFragment;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int REQUEST_CODE_LOCATION = 22;


    private static final String TAG = "MainActivityTag";
    private static final String TAG_DB = "MainActivityDBTag";

    public static DatabaseHelper hereDB;

    //Global variables
    public static ArrayList<MyHereAgent> myConnectedAgents; //My connected agents
    public static MyHereAgent mySelectedAgent;              //My currently selected agent (exercising)
    public static MyRoutine mySelectedRoutine;              //My currently selected routine
    public static MyRecord myCurrentRecord;                   //My today's record (start-end)

    public static boolean achievePrizeP;
    public static boolean achievePrizeE;
    public static boolean achievePrizeD;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private ImageView nav_header_icon;
    private TextView nav_header_nick;
    private TextView nav_header_name_id;

    private View header;

    // Modification

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /* Initialize global variables */
        myConnectedAgents = null;       //My connected agents
        mySelectedAgent = null;         //My currently selected agent (exercising)
        mySelectedRoutine = null;       //My currently selected routine
        myCurrentRecord = null;         //My today's record (start-end)

        achievePrizeP = false;
        achievePrizeD = false;
        achievePrizeE = false;

        /* Initialize Database */
        hereDB = new DatabaseHelper(getApplicationContext());

        if (hereDB != null) {
            Log.d(TAG_DB, "[Database] DatabaseHelper is created.");
        }

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "Please permit location access\nfor ", Toast.LENGTH_SHORT).show();

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_CODE_LOCATION);
            //return;
        } else {
            Toast.makeText(getApplicationContext(), "Granted", Toast.LENGTH_SHORT).show();
        }


        tabLayout = (TabLayout) findViewById(R.id.main_tablayout);

        tabLayout.addTab(tabLayout.newTab().setText("Review"));
        tabLayout.addTab(tabLayout.newTab().setText("Achievement"));
        tabLayout.addTab(tabLayout.newTab().setText("Group"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager = (ViewPager) findViewById(R.id.main_viewpager);
        final PagerAdapter pagerAdapter = new PagerAdapter(
                getSupportFragmentManager(), tabLayout.getTabCount());

        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        System.out.println("Modification test");

        System.out.print("GOOD");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mySelectedRoutine == null) {
                    Snackbar.make(view, "Please select your routine first", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    if (viewPager != null) {
                        viewPager.setCurrentItem(1);
                    }
                } else {
                    //Send a selected routine by serializing MyRoutine object
                    Intent intent_startexercise = new Intent(getApplicationContext(), StartExerciseActivity.class);
                    intent_startexercise.putExtra("selectedRoutine", mySelectedRoutine);
                    startActivity(intent_startexercise);
                }


            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        header = navigationView.getHeaderView(0);

        initWidgets();


    }


    private void initWidgets() {

        nav_header_icon = (ImageView) header.findViewById(R.id.nav_header_icon);
        nav_header_nick = (TextView) header.findViewById(R.id.nav_header_usernick);
        nav_header_name_id = (TextView) header.findViewById(R.id.nav_header_username_id);

        initMyInfo();
    }

    private void initMyInfo() {
        MyInformation tmpMyInformation = hereDB.getMyInformation();
        if (tmpMyInformation == null) {
            nav_header_icon.setVisibility(View.INVISIBLE);
            nav_header_nick.setText("User");
            nav_header_name_id.setText("Insert user information");
        } else {
            Log.d("MainInitWidgets", "initMyInfo() is called");
            Log.d("MainInitWidgets", "user_sex: " + tmpMyInformation.getUserSex());
            Log.d("MainInitWidgets", "user_name: " + tmpMyInformation.getUserName());
            Log.d("MainInitWidgets", "user_nick: " + tmpMyInformation.getUserNick());

            if (tmpMyInformation.getUserSex() == 2) {
                nav_header_icon.setVisibility(View.VISIBLE);
                nav_header_icon.setImageResource(R.drawable.here_character_simple_girl);
            } else {
                nav_header_icon.setVisibility(View.VISIBLE);
                nav_header_icon.setImageResource(R.drawable.here_character_simple_boy);
            }

            nav_header_nick.setText(tmpMyInformation.getUserNick());
            String name_id = "";
            name_id = tmpMyInformation.getUserName() + " (" + tmpMyInformation.getUserId() + ")";
            nav_header_name_id.setText(name_id);
        }
    }

    @Override
    protected void onResume() {
        Log.d("MainInitWidgets", "onResume() is called");
        initMyInfo();
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_help) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch (item.getItemId()) {
//            //Test page
//            case R.id.nav_dbtest:
//                //Move to test page
//                Intent dbtestIntent = new Intent(this, DatabaseTestActivity.class);
//                startActivity(dbtestIntent);
//                break;
//            //Test page
//            case R.id.nav_testpage:
//                //Move to test page
//                Intent testIntent = new Intent(this, WorkoutRecords.class);
//                startActivity(testIntent);
//                break;
            //My exercise equipments
            case R.id.nav_myequipments:
//                Toast.makeText(getApplicationContext(), "MY EXERCISE EQUIPMENTS", Toast.LENGTH_SHORT).show();
                Intent intent_myequipments = new Intent(getApplicationContext(), MyEquipmentsActivity.class);
                startActivity(intent_myequipments);
                break;
            //My exercise routines
            case R.id.nav_myroutine:
//                Toast.makeText(getApplicationContext(), "MY EXERCISE ROUTINES", Toast.LENGTH_SHORT).show();
                Intent intent_myroutines = new Intent(getApplicationContext(), MyRoutinesActivity.class);
                startActivity(intent_myroutines);
                break;
            //My exercise record
            case R.id.nav_myrecord:
//                Toast.makeText(getApplicationContext(), "MY EXERCISE RECORDS", Toast.LENGTH_SHORT).show();
                Intent intent_myrecords = new Intent(getApplicationContext(), MyRecordsActivity.class);
                startActivity(intent_myrecords);
                break;
            //My information setting
            case R.id.nav_mng_myinfo:
//                Toast.makeText(getApplicationContext(), "MY INFORMATION SETTING", Toast.LENGTH_SHORT).show();
                Intent intent_settingmyinfo = new Intent(getApplicationContext(), SettingMyInfoActivity.class);
                startActivity(intent_settingmyinfo);
                break;
            //App setting
//            case R.id.nav_mng_appsetting:
////                Toast.makeText(getApplicationContext(), "APPLICATION SETTING", Toast.LENGTH_SHORT).show();
//                Intent intent_settingappinfo = new Intent(getApplicationContext(), SettingAppInfoActivity.class);
//                startActivity(intent_settingappinfo);
//                break;
            //Help
            case R.id.nav_mng_showhelp:
//                Toast.makeText(getApplicationContext(), "SHOW HELP DIALOG", Toast.LENGTH_SHORT).show();

                SupportHelpFragment fragment = new SupportHelpFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                //FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                transaction.add(R.id.container, fragment, "instruction");
                transaction.addToBackStack("instruction");

                transaction.commit();

//                Intent intent_supporthelp = new Intent(getApplicationContext(), SupportHelpActivity.class);
//                startActivity(intent_supporthelp);
                break;
            //Application information
            case R.id.nav_mng_showappinfo:
//                Toast.makeText(getApplicationContext(), "SHOW APP INFO DIALOG", Toast.LENGTH_SHORT).show();

                MainActivity.hereDB.dropAllTables();
                insertSampleData();
                Intent intent_supportappinfo = new Intent(getApplicationContext(), SupportAppInfoActivity.class);
                startActivity(intent_supportappinfo);
                break;
//            case R.id.nav_db_add_myinformation:
//                //Android device id
//                String android_deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(),
//                        Settings.Secure.ANDROID_ID);
//
//                MyInformation tmpMyInformation = new MyInformation();
//
//                tmpMyInformation.setUserId("ThisIsAnId");
//                tmpMyInformation.setUserName("UserName");
//                tmpMyInformation.setUserNick("Nickname");
//                tmpMyInformation.setUserAge(25);
//                tmpMyInformation.setUserSex(1);
//                tmpMyInformation.setUserHeight(180);
//                tmpMyInformation.setUserWeight(75);
//                tmpMyInformation.setUserRegistered(1);
//                tmpMyInformation.setUserDeviceId(android_deviceId);
//
//                hereDB.insertMyInformation(tmpMyInformation);
//                Toast.makeText(getApplicationContext(), "MyInformation is added into DB.", Toast.LENGTH_SHORT).show();
//
//                initMyInfo();
//                break;
//            case R.id.nav_db_add_myagents:
//                MyHereAgent tmpMyHereAgent1 = new MyHereAgent();
//                tmpMyHereAgent1.setMyeqMacId("11:22:33:44:55:66");
//                tmpMyHereAgent1.setMyeqName("아령");
//                tmpMyHereAgent1.setMyeqType(1);
//                tmpMyHereAgent1.setMyeqBeaconMajorId("Major1");
//                tmpMyHereAgent1.setMyeqBeaconMinorId("Minor1");
//
//                MyHereAgent tmpMyHereAgent2 = new MyHereAgent();
//                tmpMyHereAgent2.setMyeqMacId("22:33:44:55:66:77");
//                tmpMyHereAgent2.setMyeqName("푸쉬업바");
//                tmpMyHereAgent2.setMyeqType(2);
//                tmpMyHereAgent2.setMyeqBeaconMajorId("Major2");
//                tmpMyHereAgent2.setMyeqBeaconMinorId("Minor2");
//
//                MyHereAgent tmpMyHereAgent3 = new MyHereAgent();
//                tmpMyHereAgent3.setMyeqMacId("33:44:55:66:77:88");
//                tmpMyHereAgent3.setMyeqName("훌라후프");
//                tmpMyHereAgent3.setMyeqType(4);
//                tmpMyHereAgent3.setMyeqBeaconMajorId("Major3");
//                tmpMyHereAgent3.setMyeqBeaconMinorId("Minor3");
//
//                hereDB.insertHereAgent(tmpMyHereAgent1);
//                hereDB.insertHereAgent(tmpMyHereAgent2);
//                hereDB.insertHereAgent(tmpMyHereAgent3);
//                Toast.makeText(getApplicationContext(), "Three agents are added into DB.", Toast.LENGTH_SHORT).show();
//
//                break;
//            case R.id.nav_db_add_myroutines:
//                MyRoutine tmpMyRoutine1 = new MyRoutine();
//                tmpMyRoutine1.setRoutineId("ROUTINE01");
//                tmpMyRoutine1.setRoutineName("아령푸쉬업훌라후프");
//                tmpMyRoutine1.setRoutineEq1Id("11:22:33:44:55:66");
//                tmpMyRoutine1.setRoutineEq1Goal("3|15|-1");
//                tmpMyRoutine1.setRoutineEq2Id("22:33:44:55:66:77");
//                tmpMyRoutine1.setRoutineEq2Goal("2|20|-1");
//                tmpMyRoutine1.setRoutineEq3Id("33:44:55:66:77:88");
//                tmpMyRoutine1.setRoutineEq3Goal("5|-1|60");
//
//                MyRoutine tmpMyRoutine2 = new MyRoutine();
//                tmpMyRoutine2.setRoutineId("ROUTINE02");
//                tmpMyRoutine2.setRoutineName("푸쉬업훌라후프");
//                tmpMyRoutine2.setRoutineEq1Id("22:33:44:55:66:77");
//                tmpMyRoutine2.setRoutineEq1Goal("2|20|-1");
//                tmpMyRoutine2.setRoutineEq2Id("33:44:55:66:77:88");
//                tmpMyRoutine2.setRoutineEq2Goal("1|50|-1");
//
//                hereDB.insertRoutine(tmpMyRoutine1);
//                hereDB.insertRoutine(tmpMyRoutine2);
//                Toast.makeText(getApplicationContext(), "Two routines are added into DB.", Toast.LENGTH_SHORT).show();
//
//                break;
//            case R.id.nav_db_add_myrecords:
//                //3 Days, 6 Records,
//                MyRecord tmpMyRecord1 = new MyRecord();
//                tmpMyRecord1.setRecordId("RECORD01");   // 3 steps
//                tmpMyRecord1.setRecordName("This is a record01");
//                tmpMyRecord1.setRecordDateTime("2016-06-06 20:20:20");
//                tmpMyRecord1.setRecordEq1Id("11:22:33:44:55:66");
//                tmpMyRecord1.setRecordEq1Done(350);
//                tmpMyRecord1.setRecordEq2Id("22:33:44:55:66:77");
//                tmpMyRecord1.setRecordEq2Done(120);
//                tmpMyRecord1.setRecordEq3Id("33:44:55:66:77:88");
//                tmpMyRecord1.setRecordEq3Done(50);
//
//                MyRecord tmpMyRecord2 = new MyRecord();
//                tmpMyRecord2.setRecordId("RECORD02");   // 2 steps (5)
//                tmpMyRecord2.setRecordName("This is a record02");
//                tmpMyRecord2.setRecordDateTime("2016-06-06 23:20:20");
//                tmpMyRecord2.setRecordEq1Id("22:33:44:55:66:77");
//                tmpMyRecord2.setRecordEq1Done(120);
//                tmpMyRecord2.setRecordEq2Id("33:44:55:66:77:88");
//                tmpMyRecord2.setRecordEq2Done(50);
//
//                MyRecord tmpMyRecord3 = new MyRecord();
//                tmpMyRecord3.setRecordId("RECORD03");   // 4 steps (9)
//                tmpMyRecord3.setRecordName("This is a record03");
//                tmpMyRecord3.setRecordDateTime("2016-06-07 20:20:20");
//                tmpMyRecord3.setRecordEq1Id("11:22:33:44:55:66");
//                tmpMyRecord3.setRecordEq1Done(350);
//                tmpMyRecord3.setRecordEq2Id("22:33:44:55:66:77");
//                tmpMyRecord3.setRecordEq2Done(120);
//                tmpMyRecord3.setRecordEq3Id("33:44:55:66:77:88");
//                tmpMyRecord3.setRecordEq3Done(50);
//                tmpMyRecord3.setRecordEq4Id("22:33:44:55:66:77");
//                tmpMyRecord3.setRecordEq4Done(120);
//
//                MyRecord tmpMyRecord4 = new MyRecord();
//                tmpMyRecord4.setRecordId("RECORD04");   // 5 steps (14)
//                tmpMyRecord4.setRecordName("This is a record04");
//                tmpMyRecord4.setRecordDateTime("2016-06-08 20:20:20");
//                tmpMyRecord4.setRecordEq1Id("11:22:33:44:55:66");
//                tmpMyRecord4.setRecordEq1Done(350);
//                tmpMyRecord4.setRecordEq2Id("22:33:44:55:66:77");
//                tmpMyRecord4.setRecordEq2Done(120);
//                tmpMyRecord4.setRecordEq3Id("33:44:55:66:77:88");
//                tmpMyRecord4.setRecordEq3Done(50);
//                tmpMyRecord4.setRecordEq4Id("33:44:55:66:77:88");
//                tmpMyRecord4.setRecordEq4Done(50);
//                tmpMyRecord4.setRecordEq5Id("33:44:55:66:77:88");
//                tmpMyRecord4.setRecordEq5Done(50);
//
//                MyRecord tmpMyRecord5 = new MyRecord();
//                tmpMyRecord5.setRecordId("RECORD05");   // 1 step (15)
//                tmpMyRecord5.setRecordName("This is a record05");
//                tmpMyRecord5.setRecordDateTime("2016-06-08 20:20:20");
//                tmpMyRecord5.setRecordEq1Id("22:33:44:55:66:77");
//                tmpMyRecord5.setRecordEq1Done(350);
//
//                MyRecord tmpMyRecord6 = new MyRecord();
//                tmpMyRecord6.setRecordId("RECORD06");   // 3 steps (18)
//                tmpMyRecord6.setRecordName("This is a record06");
//                tmpMyRecord6.setRecordDateTime("2016-06-09 23:20:20");
//                tmpMyRecord6.setRecordEq1Id("11:22:33:44:55:66");
//                tmpMyRecord6.setRecordEq1Done(350);
//                tmpMyRecord6.setRecordEq2Id("22:33:44:55:66:77");
//                tmpMyRecord6.setRecordEq2Done(120);
//                tmpMyRecord6.setRecordEq3Id("33:44:55:66:77:88");
//                tmpMyRecord6.setRecordEq3Done(50);
//
//                hereDB.insertRecord(tmpMyRecord1);
//                hereDB.insertRecord(tmpMyRecord2);
//                hereDB.insertRecord(tmpMyRecord3);
//                hereDB.insertRecord(tmpMyRecord4);
//                hereDB.insertRecord(tmpMyRecord5);
//                hereDB.insertRecord(tmpMyRecord6);
//
//                Toast.makeText(getApplicationContext(), "Six records are added into DB.", Toast.LENGTH_SHORT).show();
//
//                break;
        }



//
//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void insertSampleData() {

        hereDB.dropAllTables();

        //Android device id
        String android_deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        MyInformation tmpMyInformation = new MyInformation();

        tmpMyInformation.setUserId("ymbaek");
        tmpMyInformation.setUserName("Youngmin Baek");
        tmpMyInformation.setUserNick("BaekHorang");
        tmpMyInformation.setUserAge(28);
        tmpMyInformation.setUserSex(1);
        tmpMyInformation.setUserHeight(182);
        tmpMyInformation.setUserWeight(75);
        tmpMyInformation.setUserRegistered(1);
        tmpMyInformation.setUserDeviceId(android_deviceId);

        hereDB.insertMyInformation(tmpMyInformation);


        MyHereAgent tmpMyHereAgent1 = new MyHereAgent();
        tmpMyHereAgent1.setMyeqMacId("88:C2:55:12:31:4F");
        tmpMyHereAgent1.setMyeqName("Dumbbells");
        tmpMyHereAgent1.setMyeqType(1);
        tmpMyHereAgent1.setMyeqBeaconMajorId("HERE");
        tmpMyHereAgent1.setMyeqBeaconMinorId("DB");

        MyHereAgent tmpMyHereAgent2 = new MyHereAgent();
        tmpMyHereAgent2.setMyeqMacId("88:C2:55:12:1E:FA");
        tmpMyHereAgent2.setMyeqName("PushupBars");
        tmpMyHereAgent2.setMyeqType(2);
        tmpMyHereAgent2.setMyeqBeaconMajorId("HERE");
        tmpMyHereAgent2.setMyeqBeaconMinorId("PU");

        MyHereAgent tmpMyHereAgent3 = new MyHereAgent();
        tmpMyHereAgent3.setMyeqMacId("88:C2:55:10:24:AE");
        tmpMyHereAgent3.setMyeqName("HulaHoop");
        tmpMyHereAgent3.setMyeqType(4);
        tmpMyHereAgent3.setMyeqBeaconMajorId("HERE");
        tmpMyHereAgent3.setMyeqBeaconMinorId("HH");

        hereDB.insertHereAgent(tmpMyHereAgent1);
        hereDB.insertHereAgent(tmpMyHereAgent2);
        hereDB.insertHereAgent(tmpMyHereAgent3);


        MyRoutine tmpMyRoutine1 = new MyRoutine();
        tmpMyRoutine1.setRoutineId("ROUTINE1");
        tmpMyRoutine1.setRoutineName("Routine for Monday");
        tmpMyRoutine1.setRoutineEq1Id("88:C2:55:12:31:4F");
        tmpMyRoutine1.setRoutineEq1Goal("3|15|-1");
        tmpMyRoutine1.setRoutineEq2Id("88:C2:55:10:24:AE");
        tmpMyRoutine1.setRoutineEq2Goal("2|20|-1");
        tmpMyRoutine1.setRoutineEq3Id("88:C2:55:12:1E:FA");
        tmpMyRoutine1.setRoutineEq3Goal("5|-1|60");
        tmpMyRoutine1.setRoutineEq1Id("88:C2:55:12:31:4F");
        tmpMyRoutine1.setRoutineEq1Goal("5|10|-1");

        MyRoutine tmpMyRoutine2 = new MyRoutine();
        tmpMyRoutine2.setRoutineId("ROUTINE2");
        tmpMyRoutine2.setRoutineName("Relax Routine");
        tmpMyRoutine2.setRoutineEq1Id("88:C2:55:10:24:AE");
        tmpMyRoutine2.setRoutineEq1Goal("2|20|-1");
        tmpMyRoutine2.setRoutineEq2Id("88:C2:55:12:31:4F");
        tmpMyRoutine2.setRoutineEq2Goal("1|50|-1");

        MyRoutine tmpMyRoutine3 = new MyRoutine();
        tmpMyRoutine3.setRoutineId("ROUTINE3");
        tmpMyRoutine3.setRoutineName("Very Tough Routine");
        tmpMyRoutine3.setRoutineEq1Id("88:C2:55:12:31:4F");
        tmpMyRoutine3.setRoutineEq1Goal("10|20|-1");
        tmpMyRoutine3.setRoutineEq2Id("88:C2:55:10:24:AE");
        tmpMyRoutine3.setRoutineEq2Goal("3|-1|360");
        tmpMyRoutine3.setRoutineEq3Id("88:C2:55:12:1E:FA");
        tmpMyRoutine3.setRoutineEq3Goal("10|20|-1");

        hereDB.insertRoutine(tmpMyRoutine1);
        hereDB.insertRoutine(tmpMyRoutine2);
        hereDB.insertRoutine(tmpMyRoutine3);


        //3 Days, 6 Records,
        MyRecord tmpMyRecord1 = new MyRecord();
        tmpMyRecord1.setRecordId("RECORD01");   // 3 steps
        tmpMyRecord1.setRecordName("What a tough exercise!");
        tmpMyRecord1.setRecordDateTime("2016-06-11 20:20:20");
        tmpMyRecord1.setRecordEq1Id("88:C2:55:12:31:4F");
        tmpMyRecord1.setRecordEq1Done(350);
        tmpMyRecord1.setRecordEq2Id("88:C2:55:12:1E:FA");
        tmpMyRecord1.setRecordEq2Done(120);
        tmpMyRecord1.setRecordEq3Id("88:C2:55:10:24:AE");
        tmpMyRecord1.setRecordEq3Done(50);

        MyRecord tmpMyRecord2 = new MyRecord();
        tmpMyRecord2.setRecordId("RECORD02");   // 2 steps (5)
        tmpMyRecord2.setRecordName("It was valuable enough for me");
        tmpMyRecord2.setRecordDateTime("2016-06-12 23:20:20");
        tmpMyRecord2.setRecordEq1Id("88:C2:55:12:1E:FA");
        tmpMyRecord2.setRecordEq1Done(120);
        tmpMyRecord2.setRecordEq2Id("88:C2:55:12:31:4F");
        tmpMyRecord2.setRecordEq2Done(50);

        MyRecord tmpMyRecord3 = new MyRecord();
        tmpMyRecord3.setRecordId("RECORD03");   // 4 steps (9)
        tmpMyRecord3.setRecordName("Please do more tomorrow T.T");
        tmpMyRecord3.setRecordDateTime("2016-06-13 20:20:20");
        tmpMyRecord3.setRecordEq1Id("88:C2:55:12:31:4F");
        tmpMyRecord3.setRecordEq1Done(350);
        tmpMyRecord3.setRecordEq2Id("88:C2:55:12:1E:FA");
        tmpMyRecord3.setRecordEq2Done(120);
        tmpMyRecord3.setRecordEq3Id("88:C2:55:12:31:4F");
        tmpMyRecord3.setRecordEq3Done(50);
        tmpMyRecord3.setRecordEq4Id("88:C2:55:10:24:AE");
        tmpMyRecord3.setRecordEq4Done(120);

        MyRecord tmpMyRecord4 = new MyRecord();
        tmpMyRecord4.setRecordId("RECORD04");   // 5 steps (14)
        tmpMyRecord4.setRecordName("Totally screwed up");
        tmpMyRecord4.setRecordDateTime("2016-06-14 20:20:20");
        tmpMyRecord4.setRecordEq1Id("88:C2:55:12:31:4F");
        tmpMyRecord4.setRecordEq1Done(350);
        tmpMyRecord4.setRecordEq2Id("88:C2:55:10:24:AE");
        tmpMyRecord4.setRecordEq2Done(120);
        tmpMyRecord4.setRecordEq3Id("88:C2:55:12:31:4F");
        tmpMyRecord4.setRecordEq3Done(150);
        tmpMyRecord4.setRecordEq4Id("88:C2:55:12:1E:FA");
        tmpMyRecord4.setRecordEq4Done(150);
        tmpMyRecord4.setRecordEq5Id("88:C2:55:12:31:4F");
        tmpMyRecord4.setRecordEq5Done(150);

        MyRecord tmpMyRecord5 = new MyRecord();
        tmpMyRecord5.setRecordId("RECORD05");   // 1 step (15)
        tmpMyRecord5.setRecordName("Pretty good activity today");
        tmpMyRecord5.setRecordDateTime("2016-06-15 20:20:20");
        tmpMyRecord5.setRecordEq1Id("88:C2:55:10:24:AE");
        tmpMyRecord5.setRecordEq1Done(350);

        MyRecord tmpMyRecord6 = new MyRecord();
        tmpMyRecord6.setRecordId("RECORD06");   // 3 steps (18)
        tmpMyRecord6.setRecordName("I can do better next week!");
        tmpMyRecord6.setRecordDateTime("2016-06-16 23:20:20");
        tmpMyRecord6.setRecordEq1Id("88:C2:55:12:31:4F");
        tmpMyRecord6.setRecordEq1Done(350);
        tmpMyRecord6.setRecordEq2Id("88:C2:55:10:24:AE");
        tmpMyRecord6.setRecordEq2Done(120);
        tmpMyRecord6.setRecordEq3Id("88:C2:55:12:1E:FA");
        tmpMyRecord6.setRecordEq3Done(50);

        hereDB.insertRecord(tmpMyRecord1);
        hereDB.insertRecord(tmpMyRecord2);
        hereDB.insertRecord(tmpMyRecord3);
        hereDB.insertRecord(tmpMyRecord4);
        hereDB.insertRecord(tmpMyRecord5);
        hereDB.insertRecord(tmpMyRecord6);

    }
}
