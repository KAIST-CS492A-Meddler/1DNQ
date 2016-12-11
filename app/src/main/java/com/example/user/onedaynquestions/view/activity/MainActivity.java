package com.example.user.onedaynquestions.view.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
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
import com.example.user.onedaynquestions.model.MyInfo;
import com.example.user.onedaynquestions.service.FloatingButtonService;
import com.example.user.onedaynquestions.service.WakefulPushReceiver;
import com.example.user.onedaynquestions.utility.LocalDBController;
import com.example.user.onedaynquestions.utility.DatabaseHelper;
import com.example.user.onedaynquestions.utility.PostResponseAsyncTask;
import com.example.user.onedaynquestions.view.fragment.MyRecordsFragment;
import com.example.user.onedaynquestions.view.fragment.SupportHelpFragment;
import com.example.user.onedaynquestions.view.testactivity.DBLocalTestActivity;
import com.example.user.onedaynquestions.view.testactivity.DBServerTestActivity;
import com.example.user.onedaynquestions.view.testactivity.GraphTestActivity;
import com.example.user.onedaynquestions.view.testactivity.WidgetTestActivity;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import static com.example.user.onedaynquestions.service.WakefulPushReceiver.ACTION_RECEIVE;
import static com.example.user.onedaynquestions.service.WakefulPushReceiver.ACTION_REGISTRATION;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AsyncResponse {

    public class BroadReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action) {
                case ACTION_RECEIVE:
                    WakefulPushReceiver.updated = false;
                    viewPager.getAdapter().notifyDataSetChanged();
                    break;
                case "NEW_PROBLEM_HAS_COME":
                    int check = 0;
                    break;
                default:
            }
        }
    }

    private static final int REQUEST_CODE_SYSTEM_ALERT_WINDOW = 22;
    public static final int REQUEST_CODE = 59999;


    private static final String TAG = "MainActivityTag";
    private static final String TAG_DB = "MainActivityDBTag";

    public static DatabaseHelper hereDB;
    public static LocalDBController odnqDB;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private ImageView nav_header_icon;
    private TextView nav_header_nick;
    private TextView nav_header_id;
    private TextView nav_header_name;

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


        /* Initialize Database */
        odnqDB = new LocalDBController(getApplicationContext());

        if (odnqDB != null) {
            Log.d(TAG_DB, "[Database] LocalDBController is created.");
        }



        /* Push Notification from Firebase*/
        startFirebaseServices();


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

                Intent intent_newcard = new Intent(getApplicationContext(), NewCardActivity.class);
                startActivity(intent_newcard);

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

        checkDrawOverlayPermission();


        filter = new IntentFilter("REFRESH_QUESTION_LIST");
        filter.addAction("com.google.android.c2dm.intent.RECEIVE");

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
        }
    }

    @Override
    protected void onResume() {
        Log.d("MainInitWidgets", "onResume() is called");
        initMyInfo();
        stopService(new Intent(this, FloatingButtonService.class));

        updateListener = new BroadReceiver();
        registerReceiver(updateListener, filter);
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

            case R.id.nav_dblocal:
                Toast.makeText(getApplicationContext(), "Local DB Test Activity", Toast.LENGTH_SHORT).show();
                Intent intent_dblocal = new Intent(getApplicationContext(), DBLocalTestActivity.class);
                startActivity(intent_dblocal);
                break;
            case R.id.nav_dbserver:
                Toast.makeText(getApplicationContext(), "Server DB Test Activity", Toast.LENGTH_SHORT).show();
                Intent intent_dbserver = new Intent(getApplicationContext(), DBServerTestActivity.class);
                startActivity(intent_dbserver);
                break;
            case R.id.nav_testcardsolving:
                Toast.makeText(getApplicationContext(), "Test a card-solving activity", Toast.LENGTH_SHORT).show();
                Intent intent_cardsolving = new Intent(getApplicationContext(), CardSolvingActivity.class);
                startActivity(intent_cardsolving);
                break;
            case R.id.nav_testcardevaluation:
                Toast.makeText(getApplicationContext(), "Test a card-evaluation activity", Toast.LENGTH_SHORT).show();
                Intent intent_cardanswersheet = new Intent(getApplicationContext(), CardAnswerSheetActivity.class);
                startActivity(intent_cardanswersheet);
                break;
            case R.id.nav_testnewgroup:
                Toast.makeText(getApplicationContext(), "Test a process to make a new group", Toast.LENGTH_SHORT).show();
                Intent intent_newgroup = new Intent(getApplicationContext(), NewGroupActivity.class);
                startActivity(intent_newgroup);
                break;
            case R.id.nav_testgraph:
                Toast.makeText(getApplicationContext(), "Test graph generation", Toast.LENGTH_SHORT).show();
                Intent intent_testgraph = new Intent(getApplicationContext(), GraphTestActivity.class);
                startActivity(intent_testgraph);
                break;
            case R.id.nav_testwidget:
                Toast.makeText(getApplicationContext(), "Test widgets", Toast.LENGTH_SHORT).show();
                Intent intent_testwidget = new Intent(getApplicationContext(), WidgetTestActivity.class);
                startActivity(intent_testwidget);
                break;
            //My exercise equipments
            case R.id.nav_myequipments:
//                Toast.makeText(getApplicationContext(), "MY EXERCISE EQUIPMENTS", Toast.LENGTH_SHORT).show();
                Intent intent_myequipments = new Intent(getApplicationContext(), MyStudyReview.class);
                startActivity(intent_myequipments);
                break;
            //My exercise routines
            case R.id.nav_myroutine:
//                Toast.makeText(getApplicationContext(), "MY EXERCISE ROUTINES", Toast.LENGTH_SHORT).show();
                Intent intent_myroutines = new Intent(getApplicationContext(), MyAchievements.class);
                startActivity(intent_myroutines);
                break;
            //My exercise record
            case R.id.nav_myrecord:
//                Toast.makeText(getApplicationContext(), "MY EXERCISE RECORDS", Toast.LENGTH_SHORT).show();
                Intent intent_myrecords = new Intent(getApplicationContext(), MyStudyGroups.class);
                startActivity(intent_myrecords);
                break;
            //My information setting
            case R.id.nav_mng_myinfo:
//                Toast.makeText(getApplicationContext(), "MY INFORMATION SETTING", Toast.LENGTH_SHORT).show();
                Intent intent_settingmyinfo = new Intent(getApplicationContext(), SettingMyInfoActivity.class);
                startActivity(intent_settingmyinfo);
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
                Log.d("TOKEN", token);
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
        //Toast.makeText(getApplicationContext(), temp, Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(updateListener);
        super.onDestroy();
    }

}
