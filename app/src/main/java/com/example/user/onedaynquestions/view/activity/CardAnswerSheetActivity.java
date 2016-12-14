package com.example.user.onedaynquestions.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.onedaynquestions.R;
import com.example.user.onedaynquestions.model.AsyncResponse;
import com.example.user.onedaynquestions.service.FloatingButtonService;
import com.example.user.onedaynquestions.utility.PostResponseAsyncTask;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by user on 2016-06-07.
 */
public class CardAnswerSheetActivity extends AppCompatActivity implements AsyncResponse {


    public static Activity thisActivity;

    private TextView cardsolving_tv_type;
    private TextView cardsolving_tv_question;
    private TextView cardsolving_tv_answer;
    private TextView cardsolving_tv_myanswer;

    private Button cardsolving_btn_right;
    private Button cardsolving_btn_wrong;

    Context mContext;

    private String card_id;
    private int card_type;
    private String card_question;
    private String card_answer;
    private String my_answer;

    int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        card_id = intent.getExtras().getString("card_id");
        card_type = intent.getExtras().getInt("card_type");
        card_question = intent.getExtras().getString("card_question");
        card_answer = intent.getExtras().getString("card_answer");
        my_answer = intent.getExtras().getString("my_answer");

//        Toast.makeText(getApplicationContext(), "- card_type: " + card_type +
//                "\n- card_question: " + card_question +
//                "\n- card_answer: " + card_answer +
//                "\n- my_answer: " + my_answer +
//                "\n- card_id: " + card_id, Toast.LENGTH_SHORT).show();

        count = 0;
        thisActivity = this;
        setContentView(R.layout.activity_cardanswersheet);

        initWidget();
        initWidgetValue(card_type, card_question, card_answer, my_answer);

    }

    public void initWidget() {
        cardsolving_tv_type = (TextView) findViewById(R.id.cardsolving_tv_type);
        cardsolving_tv_question = (TextView) findViewById(R.id.cardsolving_tv_question);
        cardsolving_tv_answer = (TextView) findViewById(R.id.cardsolving_tv_answer);
        cardsolving_tv_myanswer = (TextView) findViewById(R.id.cardsolving_tv_myanswer);

        cardsolving_btn_right = (Button) findViewById(R.id.cardanswer_btn_right);
        cardsolving_btn_wrong = (Button) findViewById(R.id.cardanswer_btn_wrong);
    }

    public void initWidgetValue(int cardType, String cardQuestion, String cardAnswer, String myAnswer) {

        switch (cardType) {
            case 1:
                cardsolving_tv_type.setText("The word");
            default:
                break;
        }

        cardsolving_tv_question.setText(cardQuestion);
        cardsolving_tv_answer.setText(cardAnswer);
        cardsolving_tv_myanswer.setText(myAnswer);

        if (myAnswer.equals("TIME OVER")) {
            cardsolving_btn_right.setEnabled(false);
            cardsolving_btn_right.setBackgroundColor(Color.LTGRAY);
        }
    }

    @Override
    protected void onResume() {
        stopService(new Intent(this, FloatingButtonService.class));

        super.onResume();
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    public void mOnClick(View v) {
        Intent intent_goeval = new Intent(getApplicationContext(), CardEvaluationActivity.class);
        intent_goeval.putExtra("card_id", card_id);

        switch (v.getId()) {
            case R.id.cardanswer_btn_wrong:
                MainActivity.odnqDB.updateMyInfoCardAnswer(MainActivity.odnqDB.getMyInfo().getMyInfoId(), 0);

                Log.d("CardAnswerUpdate", "# of wrong: " + MainActivity.odnqDB.getMyInfo().getMyInfoAnswerWrong());

                intent_goeval.putExtra("self_eval", 0);
                startActivity(intent_goeval);
                break;
            case R.id.cardanswer_btn_right:
                MainActivity.odnqDB.updateMyInfoCardAnswer(MainActivity.odnqDB.getMyInfo().getMyInfoId(), 1);

                Log.d("CardAnswerUpdate", "# of right: " + MainActivity.odnqDB.getMyInfo().getMyInfoAnswerRight());

                intent_goeval.putExtra("self_eval", 1);
                startActivity(intent_goeval);
                break;
        }
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
                new PostResponseAsyncTask(CardAnswerSheetActivity.this, postData);

        createUserLogTask.execute("http://110.76.95.150/create_userlog.php");
        Log.d("USER_LOG", "[" + logTimestamp + "] " + logCurActivity + " - " + logCurEvent);
    }
}
