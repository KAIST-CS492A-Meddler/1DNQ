package com.example.user.onedaynquestions.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.example.user.onedaynquestions.R;
import com.example.user.onedaynquestions.controller.CardAdapter;
import com.example.user.onedaynquestions.model.AsyncResponse;
import com.example.user.onedaynquestions.model.MyCard;
import com.example.user.onedaynquestions.service.FloatingButtonService;
import com.example.user.onedaynquestions.utility.PostResponseAsyncTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by ymbaek on 2016-04-18.
 */
public class MyStudyReview extends AppCompatActivity implements AsyncResponse {

    public static final int WRONGANSWER = 0;
    public static final int DAILY = 1;
    public static final int MYCARD = 3;

    public static final String TAG = "MyStudyReview";
    public static final String TAG_DB = "MyEquipmentsDBTag";

    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<MyCard> wrongAnswerList, dailyRecordList, myCardList;
    private float dist;
    private int prevX, prevY;
    private final int distThreshold = 100;
    private CountDownTimer cdt;

    private CardAdapter wrongAnswerListAdapter,dailyRecordListAdapter, myCardListAdapter;
    private RecyclerView wrongAnswerListView, dailyRecordListView, myCardListView;

//    public List<MyHereAgent> myHereAgents;
//    private HERE_DeviceListAdapter equipListAdapter;
//    private BluetoothAdapter mBluetoothAdapter;
//    private ListViewAdapter adapter;
//    private ArrayList<BluetoothDevice> mLEdeviceList;
//    private boolean mScanning;
//    private Handler mHandler;


//    private MyHereAgent selectedNewAgent;
//
//    private ListView lvEquipList;
//
//    private ArrayList<MyHereAgent> pairedEquipList;
//
//    private static final int REQUEST_ENABLE_BT = 1;
//    // Stops scanning after 10 seconds.
//    private static final long SCAN_PERIOD = 10000;

    //Toolbar
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mystudyreview);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("My Study Review");
        toolbar.setNavigationIcon(R.mipmap.ic_menu_arrow_back);
        toolbar.setTitleTextColor(Color.WHITE);

        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();

        initQuestionList();
        appendQuestion(DAILY, new MyCard());
        appendQuestion(WRONGANSWER, new MyCard());

        recordUserLog("MyStudyReview", "onCreate");

        initCards();
    }


    public void initCards() {
        if (MainActivity.odnqDB != null) {

            if (MainActivity.odnqDB.getMyInfo() == null) {
                Log.d("AppendCardList", "[MyStudyReview] No user information");

            } else {
                String myInfoId = MainActivity.odnqDB.getMyInfo().getMyInfoId();
                Log.d("AppendCardList", "[MyStudyReview] MyCard table size: " + MainActivity.odnqDB.countTableMyCard());

                clearAllCardList();

                ArrayList<MyCard> allCardList;
                ArrayList<MyCard> wrongCardList;
                ArrayList<MyCard> myCardList;

                /** ADD ALL CARD LIST **/
                allCardList = MainActivity.odnqDB.getMyCards(1, myInfoId);
                if (allCardList != null){
                    Log.d("AppendCardList", "[MyStudyReview] allCardList - size: " + allCardList.size());

                    for (int i = 0; i < allCardList.size(); i++) {
                        appendQuestion(DAILY, allCardList.get(i));
                    }
                }

                /** ADD WRONG CARD LIST **/
                wrongCardList = MainActivity.odnqDB.getMyCards(5, myInfoId);
                if (wrongCardList != null){
                    Log.d("AppendCardList", "[MyStudyReview] wrongCardList - size: " + wrongCardList.size());

                    for (int i = 0; i < wrongCardList.size(); i++) {
                        appendQuestion(WRONGANSWER, wrongCardList.get(i));
                    }
                }

                /** ADD MY CARD LIST **/
                myCardList = MainActivity.odnqDB.getMyCards(3, myInfoId);
                if (myCardList != null){
                    Log.d("AppendCardList", "[MyStudyReview] wrongCardList - size: " + myCardList.size());

                    for (int i = 0; i < wrongCardList.size(); i++) {
                        appendQuestion(MYCARD, myCardList.get(i));
                    }
                }

            }
        }
    }
    private void clearAllCardList() {
        dailyRecordList.clear();
        wrongAnswerList.clear();
        myCardList.clear();
    }


    public void mOnClick(View v) {
        switch (v.getId()) {
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
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    public void onResume() {
        stopService(new Intent(this, FloatingButtonService.class));
        super.onResume();
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    public void onRestart(){
        super.onRestart();
    }


    public void initQuestionList(){
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);


        dailyRecordListView = (RecyclerView) findViewById(R.id.daily_study_lv);
        dailyRecordListView.setLayoutManager(mLayoutManager);
        dailyRecordListView.setHasFixedSize(true);
        dailyRecordList = new ArrayList<>();
        dailyRecordListAdapter = new CardAdapter(dailyRecordList);
        dailyRecordListView.setAdapter(dailyRecordListAdapter);
        dailyRecordListView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                switch (e.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        dist = 0;
                        prevX = (int)e.getX();
                        prevY = (int)e.getY();

                        break;
                    case MotionEvent.ACTION_MOVE:
                        int tempX = prevX - (int)e.getX();
                        int tempY = prevY - (int)e.getY();
                        dist += Math.sqrt(tempX * tempX + tempY * tempY);
                        prevX = (int)e.getX();
                        prevY = (int)e.getY();

                        break;
                    case MotionEvent.ACTION_UP:
                        if(dist < distThreshold) {
                            recordUserLog("MyStudyReview", "solveCard");
                            int id = dailyRecordListView.getChildAdapterPosition(dailyRecordListView.findChildViewUnder(e.getX(), e.getY()));
                            startActivityForResult(new Intent(dailyRecordList.get(id).getCardSolvingIntent(MyStudyReview.this)), MainActivity.REQUEST_REFRESH);
                        }

                        break;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });




        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        wrongAnswerListView = (RecyclerView) findViewById(R.id.frequently_wrong_question_onReview_lv);
        wrongAnswerListView.setLayoutManager(mLayoutManager);
        wrongAnswerListView.setHasFixedSize(true);
        wrongAnswerList = new ArrayList<>();
        wrongAnswerListAdapter = new CardAdapter(wrongAnswerList);
        wrongAnswerListView.setAdapter(wrongAnswerListAdapter);
        wrongAnswerListView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                switch (e.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        dist = 0;
                        prevX = (int)e.getX();
                        prevY = (int)e.getY();

                        break;
                    case MotionEvent.ACTION_MOVE:
                        int tempX = prevX - (int)e.getX();
                        int tempY = prevY - (int)e.getY();
                        dist += Math.sqrt(tempX * tempX + tempY * tempY);
                        prevX = (int)e.getX();
                        prevY = (int)e.getY();

                        break;
                    case MotionEvent.ACTION_UP:
                        if(dist < distThreshold) {
                            recordUserLog("MyStudyReview", "solveCard");
                            int id = wrongAnswerListView.getChildAdapterPosition(wrongAnswerListView.findChildViewUnder(e.getX(), e.getY()));
                            startActivityForResult(new Intent(wrongAnswerList.get(id).getCardSolvingIntent(MyStudyReview.this)), MainActivity.REQUEST_REFRESH);
                        }

                        break;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        /** MYCARD **/
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        myCardListView = (RecyclerView) findViewById(R.id.mycard_lv);
        myCardListView.setLayoutManager(mLayoutManager);
        myCardListView.setHasFixedSize(true);
        myCardList = new ArrayList<>();
        myCardListAdapter = new CardAdapter(myCardList);
        myCardListView.setAdapter(myCardListAdapter);
        myCardListView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                switch (e.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        dist = 0;
                        prevX = (int)e.getX();
                        prevY = (int)e.getY();

                        break;
                    case MotionEvent.ACTION_MOVE:
                        int tempX = prevX - (int)e.getX();
                        int tempY = prevY - (int)e.getY();
                        dist += Math.sqrt(tempX * tempX + tempY * tempY);
                        prevX = (int)e.getX();
                        prevY = (int)e.getY();

                        break;
                    case MotionEvent.ACTION_UP:
                        if(dist < distThreshold) {
                            recordUserLog("MyStudyReview", "solveCard");
                            int id = myCardListView.getChildAdapterPosition(myCardListView.findChildViewUnder(e.getX(), e.getY()));
                            startActivity(new Intent(myCardList.get(id).getCardSolvingIntent(MyStudyReview.this)));
                        }

                        break;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == MainActivity.REQUEST_REFRESH){
            //if(resultCode == MainActivity.RESULT_REFRESH){
                    cdt = new CountDownTimer(2000, 100) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            if (MainActivity.odnqDB != null) {
                                if (wrongAnswerList != null) {
                                    if (MainActivity.odnqDB.countWrongCardNum() != wrongAnswerList.size()) {
                                        initCards();
                                        wrongAnswerListAdapter.notifyDataSetChanged();
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFinish() {

                        }
                    }.start();
            //}
        }
    }

    public boolean setQuestion(int where, int position, MyCard question){
        switch (where){
            case DAILY:
                dailyRecordList.add(position, question);
                break;
            case WRONGANSWER:
                wrongAnswerList.add(position, question);
                break;
            default:
                return false;
        }
        return true;
    };

    public boolean appendQuestion(int where, MyCard question){
        switch (where){
            case DAILY:
                dailyRecordList.add(question);
                break;
            case WRONGANSWER:
                wrongAnswerList.add(question);
                break;
            case MYCARD:
                myCardList.add(question);
                break;
            default:
                return false;
        }

        return true;
    };

    public boolean deleteQuestion(int where, int position){
        switch (where){
            case DAILY:
                dailyRecordList.remove(position);
                dailyRecordListAdapter.notifyDataSetChanged();
                break;
            case WRONGANSWER:
                wrongAnswerList.remove(position);
                wrongAnswerListAdapter.notifyDataSetChanged();
                break;
            default:
                return false;
        }
        return true;
    };


    public boolean setQuestions(int where, ArrayList<MyCard> list){
        switch (where){
            case DAILY:
                dailyRecordList.clear();
                dailyRecordList.addAll(list);
                dailyRecordListAdapter.notifyDataSetChanged();
                break;
            case WRONGANSWER:
                wrongAnswerList.clear();
                wrongAnswerList.addAll(list);
                wrongAnswerListAdapter.notifyDataSetChanged();
                break;
            default:
                return false;
        }
        return true;
    };

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
                new PostResponseAsyncTask(MyStudyReview.this, postData);

        createUserLogTask.execute("http://110.76.95.150/create_userlog.php");
        Log.d("USER_LOG", "[" + logTimestamp + "] " + logCurActivity + " - " + logCurEvent);
    }
}
