package com.example.user.onedaynquestions.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.onedaynquestions.R;
import com.example.user.onedaynquestions.controller.PagerAdapter;
import com.example.user.onedaynquestions.model.AsyncResponse;
import com.example.user.onedaynquestions.model.MyCard;
import com.example.user.onedaynquestions.model.MyInfo;
import com.example.user.onedaynquestions.service.FloatingButtonService;
import com.example.user.onedaynquestions.service.MonitoringService;
import com.example.user.onedaynquestions.service.WakefulPushReceiver;
import com.example.user.onedaynquestions.utility.LocalDBController;
import com.example.user.onedaynquestions.utility.PostResponseAsyncTask;
import com.example.user.onedaynquestions.view.fragment.SupportHelpFragment;
import com.example.user.onedaynquestions.view.testactivity.DBLocalTestActivity;
import com.example.user.onedaynquestions.view.testactivity.DBServerTestActivity;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import static com.example.user.onedaynquestions.service.WakefulPushReceiver.ACTION_RECEIVE;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AsyncResponse {


    public static boolean isMainActivityReady = false;
    public static boolean isMonitoringServiceOn = false;

    private static final int REQUEST_CODE_SYSTEM_ALERT_WINDOW = 22;
    public static final int REQUEST_CODE = 59999;
    public static final int REQUEST_REFRESH = 59991;
    public static final int RESULT_REFRESH = 1;

    private static final String TAG = "MainActivityTag";
    private static final String TAG_DB = "MainActivityDBTag";

    /** DATABASE **/
//    public static DatabaseHelper hereDB;
    public static LocalDBController odnqDB;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private static ImageView nav_header_icon;
    private static TextView nav_header_nick;
    private static TextView nav_header_id;
    private static TextView nav_header_name;



    public static int myCardNum;
    public static int myGroupNum;

    public static String token;

    private View header;
    NetworkInfo networkInfo;
    ConnectivityManager connectivityManager;

    private BroadcastReceiver updateListener;
    private IntentFilter filter;

    // Modification

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        isMainActivityReady = true;

        /* Initialize Database */
        odnqDB = new LocalDBController(getApplicationContext());

        if (odnqDB != null) {
            Log.d(TAG_DB, "[Database] LocalDBController is created.");
        }

        /** GLOBAL VARIABLE **/
        //myCardNum = 0;


        /** DB LOAD **/
        if (odnqDB.getMyInfo() != null) {
            countMyCardNum(odnqDB.getMyInfo().getMyInfoId());
            odnqDB.updateMyInfoLoginNum(odnqDB.getMyInfo().getMyInfoId());
        }


        /* Push Notification from Firebase*/
        startFirebaseServices();

        /** NAVIGATION DRAWER & HEADER **/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        header = navigationView.getHeaderView(0);

        /** Initialize Widget First **/
        initWidgets();

        recordUserLog("MainActivity", "onCreate");


        /** TAB LAYOUT & FRAGMENTS **/
        tabLayout = (TabLayout) findViewById(R.id.main_tablayout);

        tabLayout.addTab(tabLayout.newTab().setText("My Achievement"));
        tabLayout.addTab(tabLayout.newTab().setText("Study Note"));
        //tabLayout.addTab(tabLayout.newTab().setText("Group"));
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
                Log.d("MainActivity-TabLayout", "tab.getPosition = " + tab.getPosition());

                recordUserLog("MainActivity", "clickTab[" + tab.getPosition() + "]");

                if (tab.getPosition() == 0) {

                    LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(getApplicationContext());
                    Intent i = new Intent("TAG_REFRESH");
                    lbm.sendBroadcast(i);

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });




        /** FLOATING ACTION BUTTON **/
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //TODO: User login되어 있지 않고 그룹에 속해있지 않으면, Card를 만들 수 없도록 예외처리
                if (odnqDB.getMyInfo() == null) {
                    Snackbar.make(view, "Please register your account first\nSide menu > My Information", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
//                    Intent intent_settingMyInfo = new Intent(getApplicationContext(), SettingMyInfoActivity.class);
//                    startActivity(intent_settingMyInfo);
                } else {
                    Intent intent_newcard = new Intent(getApplicationContext(), NewCardActivity.class);
                    startActivityForResult(intent_newcard, REQUEST_REFRESH);
                }

            }
        });





        checkDrawOverlayPermission();


        filter = new IntentFilter("REFRESH_QUESTION_LIST");
        filter.addAction("com.google.android.c2dm.intent.RECEIVE");


        if (isMonitoringServiceOn = false && odnqDB.getMyInfo() != null) {
            startService(new Intent(this, MonitoringService.class));
            isMonitoringServiceOn = true;
        }


        updateListener = new BroadReceiver();
        registerReceiver(updateListener, filter);

    }
    public void checkDrawOverlayPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(getApplicationContext())) {
                /** if not construct intent to request permission */
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                /** request permission via start activity for result */
                startActivityForResult(intent, REQUEST_CODE);
            }
        } else {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (requestCode == REQUEST_CODE) {
                if (Settings.canDrawOverlays(this)) {
                    Toast.makeText(getApplicationContext(), "Drawing overlay is permitted.", Toast.LENGTH_SHORT).show();
                }
            }
        }

        if (requestCode == REQUEST_REFRESH) {
            if (resultCode == RESULT_REFRESH) {
                viewPager.getAdapter().notifyDataSetChanged();
            }
        }
//        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initWidgets() {

        nav_header_icon = (ImageView) header.findViewById(R.id.nav_header_icon);
        nav_header_nick = (TextView) header.findViewById(R.id.nav_header_usernick);
        nav_header_id = (TextView) header.findViewById(R.id.nav_header_username_id);
        nav_header_name = (TextView) header.findViewById(R.id.nav_header_username_name);

        initMyInfo();
    }

    private void initMyInfo() {

        MyInfo tmpMyInfo = odnqDB.getMyInfo();

        if (tmpMyInfo == null) {
            nav_header_icon.setVisibility(View.INVISIBLE);
            nav_header_nick.setText("Unregistered User");
            nav_header_id.setText("Insert user information");
            nav_header_name.setText(" ");
        } else {
            //TODO: Get user information from server and update local MyInfo

            Log.d(TAG, "initMyInfo() is called");
            Log.d(TAG, "tmpMyInfo.getMyInfoId(): " + tmpMyInfo.getMyInfoId());
            Log.d(TAG, "tmpMyInfo.getMyInfoNick(): " +  tmpMyInfo.getMyInfoNick());
            Log.d(TAG, "tmpMyInfo.getMyInfoName(): " +  tmpMyInfo.getMyInfoName());
            Log.d(TAG, "tmpMyInfo.getMyInfoGender(): " + tmpMyInfo.getMyInfoGender());

            if (tmpMyInfo.getMyInfoGender() == 2) {
                nav_header_icon.setVisibility(View.VISIBLE);
                nav_header_icon.setImageResource(R.drawable.here_character_simple_girl);
            } else {
                nav_header_icon.setVisibility(View.VISIBLE);
                nav_header_icon.setImageResource(R.drawable.here_character_simple_boy);
            }

            nav_header_nick.setText(tmpMyInfo.getMyInfoNick());
            nav_header_id.setText(tmpMyInfo.getMyInfoId());
            nav_header_name.setText(tmpMyInfo.getMyInfoName());

            countMyCardNum(tmpMyInfo.getMyInfoId());
            updateMyInfo(tmpMyInfo.getMyInfoId());
        }
    }

    private void updateMyInfo(String myInfo_id) {
        HashMap postData = new HashMap();
        postData.put("userinfo_id", myInfo_id);

        PostResponseAsyncTask getUserTask =
                new PostResponseAsyncTask(MainActivity.this, postData);

        getUserTask.execute("http://110.76.95.150/get_user.php");
    }

    private void countMyCardNum(String myInfo_id) {
        HashMap postData = new HashMap();
        postData.put("cinfo_maker", myInfo_id);

        PostResponseAsyncTask countMyCardNumTask =
                new PostResponseAsyncTask(MainActivity.this, postData);

        countMyCardNumTask.execute("http://110.76.95.150/get_cardbymaker.php");

//        Toast.makeText(getApplicationContext(), "User's cards are selected", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        Log.d("MainInitWidgets", "onResume() is called");
        initMyInfo();
        stopService(new Intent(this, FloatingButtonService.class));

        /** START SERVICE **/
        if (odnqDB.getMyInfo() != null) {
            startService(new Intent(this, MonitoringService.class));
        }

        /** DB LOAD **/
        if (odnqDB.getMyInfo() != null) {
            countMyCardNum(odnqDB.getMyInfo().getMyInfoId());
            odnqDB.updateMyInfoLoginNum(odnqDB.getMyInfo().getMyInfoId());

            Log.d("LocalDatabase", "[MainActivity] userLoginNum: " + odnqDB.getMyInfo().getMyInfoLoginNum());
        }



        super.onResume();
    }

    @Override
    protected  void onPause(){
        //앱 꺼져 있을때 플로팅 버튼 뜨도록 서비스 on
//        startService(new Intent(this, FloatingButtonService.class));

        if (Build.VERSION.SDK_INT >= 23) {
            if (Settings.canDrawOverlays(this)) {
                startService(new Intent(this, FloatingButtonService.class));
            }
        } else {
            startService(new Intent(this, FloatingButtonService.class));
        }

        super.onPause();
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
            SupportHelpFragment fragment = new SupportHelpFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            //FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            transaction.add(R.id.container, fragment, "instruction");
            transaction.addToBackStack("instruction");

            transaction.commit();
        }
        if (id == R.id.action_request_question) {

            PostResponseAsyncTask loginTask =
                    new PostResponseAsyncTask(MainActivity.this);
            loginTask.execute("http://110.76.95.150/push_notification2.php");
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
            case R.id.nav_newcard:
                PostResponseAsyncTask loginTask =
                        new PostResponseAsyncTask(MainActivity.this);
                loginTask.execute("http://110.76.95.150/push_notification2.php");
                Toast.makeText(getApplicationContext(), "A new card is requested.", Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_dblocal:
                Toast.makeText(getApplicationContext(), "Local DB Test Activity", Toast.LENGTH_SHORT).show();
                Intent intent_dblocal = new Intent(getApplicationContext(), DBLocalTestActivity.class);
                startActivityForResult(intent_dblocal, REQUEST_REFRESH);
                break;
//            case R.id.nav_dbserver:
//                Toast.makeText(getApplicationContext(), "Server DB Test Activity", Toast.LENGTH_SHORT).show();
//                Intent intent_dbserver = new Intent(getApplicationContext(), DBServerTestActivity.class);
//                startActivity(intent_dbserver);
//                break;
//            case R.id.nav_testcardsolving:
//                Toast.makeText(getApplicationContext(), "Test a receivedCard-solving activity", Toast.LENGTH_SHORT).show();
//                Intent intent_cardsolving = new Intent(getApplicationContext(), CardSolvingActivity.class);
//                startActivity(intent_cardsolving);
//                break;
//            case R.id.nav_testcardevaluation:
//                Toast.makeText(getApplicationContext(), "Test a receivedCard-evaluation activity", Toast.LENGTH_SHORT).show();
//                Intent intent_cardanswersheet = new Intent(getApplicationContext(), CardAnswerSheetActivity.class);
//                startActivity(intent_cardanswersheet);
//                break;
//            case R.id.nav_testnewgroup:
//                Toast.makeText(getApplicationContext(), "Test a process to make a new group", Toast.LENGTH_SHORT).show();
//                Intent intent_newgroup = new Intent(getApplicationContext(), NewGroupActivity.class);
//                startActivityForResult(intent_newgroup, REQUEST_REFRESH);
//                break;
//            case R.id.nav_testgraph:
//                Toast.makeText(getApplicationContext(), "Test graph generation", Toast.LENGTH_SHORT).show();
//                Intent intent_testgraph = new Intent(getApplicationContext(), GraphTestActivity.class);
//                startActivity(intent_testgraph);
//                break;
//            case R.id.nav_testwidget:
//                Toast.makeText(getApplicationContext(), "Test widgets", Toast.LENGTH_SHORT).show();
//                Intent intent_testwidget = new Intent(getApplicationContext(), WidgetTestActivity.class);
//                startActivity(intent_testwidget);
//                break;
            //My exercise equipments
            case R.id.nav_mycards:
//                Toast.makeText(getApplicationContext(), "MY EXERCISE EQUIPMENTS", Toast.LENGTH_SHORT).show();
                Intent intent_myequipments = new Intent(getApplicationContext(), MyStudyReview.class);
                startActivityForResult(intent_myequipments, REQUEST_REFRESH);
                break;
//            //My exercise routines
//            case R.id.nav_myroutine:
////                Toast.makeText(getApplicationContext(), "MY EXERCISE ROUTINES", Toast.LENGTH_SHORT).show();
//                Intent intent_myroutines = new Intent(getApplicationContext(), MyAchievements.class);
//                startActivity(intent_myroutines);
//                break;
            //My exercise record
            case R.id.nav_leaderboard:
//                Toast.makeText(getApplicationContext(), "MY EXERCISE RECORDS", Toast.LENGTH_SHORT).show();
                Intent intent_myrecords = new Intent(getApplicationContext(), LeaderboardActivity.class);
                startActivityForResult(intent_myrecords, REQUEST_REFRESH);
                break;
            //My information setting
            case R.id.nav_mng_myinfo:
//                Toast.makeText(getApplicationContext(), "MY INFORMATION SETTING", Toast.LENGTH_SHORT).show();
                Intent intent_settingmyinfo = new Intent(getApplicationContext(), SettingMyInfoActivity.class);
                startActivityForResult(intent_settingmyinfo, REQUEST_REFRESH);
                break;
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

//                MainActivity.hereDB.dropAllTables();
//                insertSampleData();
                Intent intent_supportappinfo = new Intent(getApplicationContext(), SupportAppInfoActivity.class);
                startActivity(intent_supportappinfo);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    private void startFirebaseServices(){
        //awaken when screen is off
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

//        IntentFilter intentfilter = new IntentFilter();
//        intentfilter.addAction(".service.PushReceiver");
        //이 부분을 클라이언트마다 다르게 subscribe하면 가능?
        isOnline();
        if(networkInfo == null){
            showAlert();
        }else {
            if (networkInfo.isConnected()) {
                token = FirebaseInstanceId.getInstance().getToken();
                if(token != null) {
                    Log.d("TOKEN", token);
                }else{

                }
                FirebaseMessaging.getInstance().subscribeToTopic("test");
                Toast.makeText(this, "Connected to Firebase server.", Toast.LENGTH_LONG).show();
            } else {
                showAlert();
            }
        }
    }

    private void showAlert(){

        AlertDialog alert = new AlertDialog.Builder(this).create();
        alert.setTitle("Network is not connected");
        alert.setMessage("Please check your network connection");
        alert.setCancelable(true);


        alert.setButton(AlertDialog.BUTTON_POSITIVE,"OK", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo == null) {
                    startActivity(new Intent(Settings.ACTION_SETTINGS));
                } else {
                    while (!networkInfo.isConnected()) ;
                    if (networkInfo.isConnected()) {
                        token = FirebaseInstanceId.getInstance().getToken();
                        Log.d("TOKEN", token);
                        FirebaseMessaging.getInstance().subscribeToTopic("test");
                        Toast.makeText(MainActivity.this, "Connected to Firebase server.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Please check your network connection.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        alert.setButton(AlertDialog.BUTTON_NEUTRAL, "Later", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
            }
        });
        alert.setButton(AlertDialog.BUTTON_NEGATIVE, "EXIT", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                finish();
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
        alert.show();
    }

    private boolean isOnline(){
        connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null){
            if( networkInfo.isConnectedOrConnecting()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void processFinish(String output) {
        String temp = output.replaceAll("<br>", "\n");
        Log.d("USER_LOG", output);

        //Toast.makeText(getApplicationContext(), temp, Toast.LENGTH_LONG).show();
        if (output.contains("result_userlog")) {
            Log.d("USER_LOG", output);
        }

        // My info
        if (output.contains("{\"result_user\":")) {
            MyInfo myInfoFromServer = odnqDB.getMyInfo();

            String jsonString = output.replace("{\"result_user\":", "");
            jsonString = jsonString.substring(0, jsonString.length() - 1);

            Log.d("MainActivity_getuser", "output: " + output);

            try {
                JSONArray jarray = new JSONArray(jsonString);

                JSONObject jObject = jarray.getJSONObject(0);

                String tmpUserId = jObject.getString("user_id");
                String tmpUserName = jObject.getString("user_name");
                String tmpUserNick = jObject.getString("user_nick");
                int tmpUserAge = jObject.getInt("user_age");
                int tmpUserGender = jObject.getInt("user_gender");
                String tmpUserDeviceId = jObject.getString("user_deviceid");
                String tmpUserToken = jObject.getString("user_token");
                int tmpUserExp = jObject.getInt("user_exp");
                float tmpUserQuality = (float) jObject.getDouble("user_quality");

                Log.d("MainActivity_getuser", "MyInfo from Server");
                Log.d("MainActivity_getuser", " - id: " + tmpUserId);
                Log.d("MainActivity_getuser", " - name: " + tmpUserName);
                Log.d("MainActivity_getuser", " - nick: " + tmpUserNick);
                Log.d("MainActivity_getuser", " - age: " + tmpUserAge);
                Log.d("MainActivity_getuser", " - gender: " + tmpUserGender);
                Log.d("MainActivity_getuser", " - deviceid: " + tmpUserDeviceId);
                Log.d("MainActivity_getuser", " - token: " + tmpUserToken);
                Log.d("MainActivity_getuser", " - exp: " + tmpUserExp);
                Log.d("MainActivity_getuser", " - quality: " + tmpUserQuality);

                myInfoFromServer.setMyInfoId(tmpUserId);
                myInfoFromServer.setMyInfoName(tmpUserName);
                myInfoFromServer.setMyInfoNick(tmpUserNick);
                myInfoFromServer.setMyInfoAge(tmpUserAge);
                myInfoFromServer.setMyInfoGender(tmpUserGender);
                myInfoFromServer.setMyInfoDeviceId(tmpUserDeviceId);
                myInfoFromServer.setMyInfoToken(tmpUserToken);
                myInfoFromServer.setMyInfoExp(tmpUserExp);
                myInfoFromServer.setMyInfoQuality(tmpUserQuality);

                //Update local MyInfo with Server information
                odnqDB.updateMyInfo(myInfoFromServer);

                Log.d("MainActivity_getuser", "MyInfo in odnqDB");
                Log.d("MainActivity_getuser", " - id: " + odnqDB.getMyInfo().getMyInfoId());
                Log.d("MainActivity_getuser", " - name: " + odnqDB.getMyInfo().getMyInfoName());
                Log.d("MainActivity_getuser", " - nick: " + odnqDB.getMyInfo().getMyInfoNick());
                Log.d("MainActivity_getuser", " - age: " + odnqDB.getMyInfo().getMyInfoAge());
                Log.d("MainActivity_getuser", " - gender: " + odnqDB.getMyInfo().getMyInfoGender());
                Log.d("MainActivity_getuser", " - deviceid: " + odnqDB.getMyInfo().getMyInfoDeviceId());
                Log.d("MainActivity_getuser", " - token: " + odnqDB.getMyInfo().getMyInfoToken());
                Log.d("MainActivity_getuser", " - exp: " + odnqDB.getMyInfo().getMyInfoExp());
                Log.d("MainActivity_getuser", " - quality: " + odnqDB.getMyInfo().getMyInfoQuality());
                Log.d("MainActivity_getuser", " - cardnum: " + odnqDB.getMyInfo().getMyInfoCardNum());
                Log.d("MainActivity_getuser", "========================================");


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // My cards
        else if (output.contains("{\"result_userscards\":")) {
            String jsonString = output.replace("{\"result_userscards\":", "");
            jsonString = jsonString.substring(0, jsonString.length() - 1);

            try {
                JSONArray jarray = new JSONArray(jsonString);
                myCardNum = jarray.length();

                odnqDB.updateMyInfoCardNum(odnqDB.getMyInfo().getMyInfoId(), myCardNum);

                //TODO: Insert my cards into (public static) Arraylist
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(updateListener);
        isMainActivityReady = false;
        super.onDestroy();
    }



    public class BroadReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case ACTION_RECEIVE:

                    MyCard tmpMyCard = new MyCard(intent);
                    MainActivity.odnqDB.insertMyCard(tmpMyCard);

                    Log.d(TAG_DB, "[NewCardActivity] A new card is added to local DB.");

                    WakefulPushReceiver.updated = false;

                    String maker = intent.getStringExtra(MyCard.ATTRIBUTE_CARD_ID);
                    if(maker.contains("[system]")) {

                        if (viewPager != null) {
                            viewPager.getAdapter().notifyDataSetChanged();
                        }
                    }

                    break;
                case "NEW_PROBLEM_HAS_COME":
                    int check = 0;
                    break;
                default:
            }

        }
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
                new PostResponseAsyncTask(MainActivity.this, postData);

        createUserLogTask.execute("http://110.76.95.150/create_userlog.php");
        Log.d("USER_LOG", "[" + logTimestamp + "] " + logCurActivity + " - " + logCurEvent);
    }

}
