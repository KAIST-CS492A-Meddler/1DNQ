package com.example.user.onedaynquestions.view.testactivity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.user.onedaynquestions.R;
import com.example.user.onedaynquestions.controller.QuestionListAdapter;
import com.example.user.onedaynquestions.model.MyCard;
import com.example.user.onedaynquestions.service.WakefulPushReceiver;

import java.util.ArrayList;

/**
 * Created by ymbaek on 2016-04-18.
 */
public class DBServerTestActivity extends AppCompatActivity {

    public static final String TAG = "MyAchievements";

    private Toolbar toolbar;
    private ListView lvQuestionList;

    private ArrayList<MyCard> questionList;
    private QuestionListAdapter questionListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testdb_server);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("Database Test: Server Side");
        toolbar.setNavigationIcon(R.mipmap.ic_menu_arrow_back);
        toolbar.setTitleTextColor(Color.WHITE);

        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("Database Test: Server Side");
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        questionList = new ArrayList<>();
        questionListAdapter = new QuestionListAdapter(this, questionList);

        lvQuestionList = (ListView) findViewById(R.id.questionlistTest);

        lvQuestionList.setAdapter(questionListAdapter);

//        Question eq1 = new Question("Q01", "hi", "안녕", "2016-11-18");
//        Question eq2 = new Question("Q02", "hello", "안녕", "2016-11-18");
//        Question eq3 = new Question("Q03", "ah", "아", "2016-11-15");
//        Question eq4 = new Question("Q04", "good night", "잘자", "2016-11-16");

//        questionList.add(eq1);
//        questionList.add(eq2);
//        questionList.add(eq3);
//        questionList.add(eq4);

//        while(!TemporalStorage.isEmpty()){
//            questionList.add(TemporalStorage.consumeReceivedQuestions());
//        }
        questionList.addAll(WakefulPushReceiver.getAllReceivedQuestions());

        questionListAdapter.notifyDataSetChanged();


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

}
