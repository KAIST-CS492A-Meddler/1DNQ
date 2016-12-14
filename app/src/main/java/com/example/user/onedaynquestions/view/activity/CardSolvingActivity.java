package com.example.user.onedaynquestions.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.onedaynquestions.R;
import com.example.user.onedaynquestions.model.AsyncResponse;
import com.example.user.onedaynquestions.model.MyCard;
import com.example.user.onedaynquestions.service.FloatingButtonService;
import com.example.user.onedaynquestions.service.WakefulPushReceiver;
import com.example.user.onedaynquestions.utility.PostResponseAsyncTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by user on 2016-06-07.
 */
public class CardSolvingActivity extends AppCompatActivity implements AsyncResponse {

    public static final String TAG_DB = "LocalDatabase";


    public static Activity thisActivity;

    TextView examiner, group, timer, type, question,  hint;
    EditText answer;
    Button showHint, submit;

    final int TIME_LIMIT = 15 + 1;
    int remainTime = TIME_LIMIT;
    CountDownTimer timeChecker;

    MyCard receivedCard;

    boolean isHintShown = false;

    int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        recordUserLog("CardSolvingActivity", "onCreate");


        count = 0;
        thisActivity = this;
        setContentView(R.layout.activity_cardsolving);
        examiner = (TextView)findViewById(R.id.cardsolving_tv_examiner);
        group = (TextView)findViewById(R.id.cardsolving_tv_group);
        timer = (TextView)findViewById(R.id.cardsolving_tv_timer);
        type = (TextView)findViewById(R.id.cardsolving_tv_type);
        question = (TextView)findViewById(R.id.cardsolving_tv_question);
        hint = (TextView)findViewById(R.id.cardsolving_tv_hint);

        answer = (EditText)findViewById(R.id.cardsolving_et_answer);

        submit = (Button)findViewById(R.id.cardsolving_btn_submit) ;

//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setCard();
//            }
//        });

        showHint = (Button)findViewById(R.id.cardsolving_btn_showhint);

        timeChecker = new CountDownTimer(TIME_LIMIT * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainTime--;

                switch (remainTime) {
                    case 7:
                        timer.setTextColor(Color.parseColor("#ffaa00"));
                    case 6:
                    case 5:
                    case 4:
                        break;
                    case 3:
                        timer.setTextColor(Color.RED);
                    case 2:
                    case 1:
                        break;
                }

                timer.setText("" + remainTime);
                timer.postInvalidate();
            }

            @Override
            public void onFinish() {
                remainTime--;
                timer.setText("" + remainTime);
                timer.postInvalidate();
//                Toast.makeText
//                        (thisActivity, "Time Over!", Toast.LENGTH_SHORT).show();

                answer.setEnabled(false);
                answer.setText("TIME OVER");
                answer.setTextColor(Color.RED);

                recordUserLog("CardSolvingActivity", "timeOver");


                goAnswerSheet();

            }
        };

        setCard();

    }



    public void setCard(){
        timeChecker.cancel();
        Intent intent = getIntent();

        if(intent != null) {
            receivedCard = new MyCard(intent);
        }
        else if(!WakefulPushReceiver.isEmpty()){
            //open from push notification
            receivedCard = WakefulPushReceiver.consumeReceivedQuestions();
        }else{
            receivedCard = new MyCard();
        }
        examiner.setText(receivedCard.getMyCardMaker());
        examiner.postInvalidate();
        group.setText("from [" + receivedCard.getMyCardGroup()+ "] Group");
        group.postInvalidate();
        remainTime = TIME_LIMIT;
        timer.setText("" + remainTime);
        timer.postInvalidate();

        //Modified
        String cardType = "";
        if (receivedCard.getMyCardType() == 1) {
            cardType = "What is the meaning\n of this word?";
        } else {
            cardType = "What is the meaning\n of this question?";
        }

        type.setText(""+cardType);
        type.postInvalidate();
        question.setText(receivedCard.getMyCardQuestion());
        question.postInvalidate();
        hint.setText(receivedCard.getMyCardHint());
        hint.postInvalidate();



        //receivedCard = null;
        timeChecker.start();
    }

    @Override
    protected void onResume() {
        stopService(new Intent(this, FloatingButtonService.class));

        super.onResume();
    }
    @Override
    public void onPause(){
        startService(new Intent(this, FloatingButtonService.class));
        timeChecker.cancel();
        super.onPause();
    }

    public void mOnClick(View v) {
        switch (v.getId()) {
            case R.id.cardsolving_btn_showhint:
                recordUserLog("CardSolvingActivity", "see/hideHint");

                if (isHintShown) {
                    hint.setVisibility(View.GONE);
                    showHint.setText("SHOW HINT");
                    isHintShown = false;
                } else {
                    hint.setVisibility(View.VISIBLE);
                    showHint.setText("HIDE HINT");
                    isHintShown = true;
                }
                break;
            case R.id.cardsolving_btn_submit:
                recordUserLog("CardSolvingActivity", "submitAnswer");

                if (receivedCard == null) {
                    Log.d("CardSolvingActivity", "receivedCard is null");
                } else {

                    goAnswerSheet();
                }
                break;
        }
    }

    private void goAnswerSheet() {
        recordUserLog("CardSolvingActivity", "goAnswerSheet");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Intent intent_goanswer = new Intent(getApplicationContext(), CardAnswerSheetActivity.class);
        intent_goanswer.putExtra("card_id", receivedCard.getMyCardId());
        intent_goanswer.putExtra("card_type", receivedCard.getMyCardType());
        intent_goanswer.putExtra("card_question", receivedCard.getMyCardQuestion());
        intent_goanswer.putExtra("card_answer", receivedCard.getMyCardAnswer());
        intent_goanswer.putExtra("my_answer", answer.getText().toString());
        startActivity(intent_goanswer);
    }

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
                new PostResponseAsyncTask(CardSolvingActivity.this, postData);

        createUserLogTask.execute("http://110.76.95.150/create_userlog.php");
        Log.d("USER_LOG", "[" + logTimestamp + "] " + logCurActivity + " - " + logCurEvent);
    }
}
