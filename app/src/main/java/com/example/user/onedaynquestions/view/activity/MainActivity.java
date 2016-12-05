package com.example.user.onedaynquestions.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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
import com.example.user.onedaynquestions.utility.DatabaseController;
import com.example.user.onedaynquestions.utility.DatabaseHelper;
import com.example.user.onedaynquestions.view.fragment.SupportHelpFragment;
import com.example.user.onedaynquestions.view.testactivity.DBLocalTestActivity;
import com.example.user.onedaynquestions.view.testactivity.DBServerTestActivity;
import com.example.user.onedaynquestions.view.testactivity.GraphTestActivity;
import com.example.user.onedaynquestions.view.testactivity.WidgetTestActivity;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int REQUEST_CODE_LOCATION = 22;


    private static final String TAG = "MainActivityTag";
    private static final String TAG_DB = "MainActivityDBTag";

    public static DatabaseHelper hereDB;
    public static DatabaseController odnqDB;

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

        /* Push Notification from Firebase*/
        startFirebaseServices();

        /* Initialize Database */
        odnqDB = new DatabaseController(getApplicationContext());

        if (odnqDB != null) {
            Log.d(TAG_DB, "[Database] DatabaseController is created.");
        }



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


    }


    private void initWidgets() {

        nav_header_icon = (ImageView) header.findViewById(R.id.nav_header_icon);
        nav_header_nick = (TextView) header.findViewById(R.id.nav_header_usernick);
        nav_header_name_id = (TextView) header.findViewById(R.id.nav_header_username_id);

        initMyInfo();
    }

    private void initMyInfo() {
//        MyInformation tmpMyInformation = hereDB.getMyInformation();
//        if (tmpMyInformation == null) {
//            nav_header_icon.setVisibility(View.INVISIBLE);
//            nav_header_nick.setText("User");
//            nav_header_name_id.setText("Insert user information");
//        } else {
//            Log.d("MainInitWidgets", "initMyInfo() is called");
//            Log.d("MainInitWidgets", "user_sex: " + tmpMyInformation.getUserSex());
//            Log.d("MainInitWidgets", "user_name: " + tmpMyInformation.getUserName());
//            Log.d("MainInitWidgets", "user_nick: " + tmpMyInformation.getUserNick());
//
//            if (tmpMyInformation.getUserSex() == 2) {
//                nav_header_icon.setVisibility(View.VISIBLE);
//                nav_header_icon.setImageResource(R.drawable.here_character_simple_girl);
//            } else {
//                nav_header_icon.setVisibility(View.VISIBLE);
//                nav_header_icon.setImageResource(R.drawable.here_character_simple_boy);
//            }
//
//            nav_header_nick.setText(tmpMyInformation.getUserNick());
//            String name_id = "";
//            name_id = tmpMyInformation.getUserName() + " (" + tmpMyInformation.getUserId() + ")";
//            nav_header_name_id.setText(name_id);
//        }
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
        FirebaseMessaging.getInstance().subscribeToTopic("test");
        Log.d("TOKEN", FirebaseInstanceId.getInstance().getToken());


    }
}
