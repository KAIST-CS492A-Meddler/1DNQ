package com.example.user.onedaynquestions.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.onedaynquestions.R;
import com.example.user.onedaynquestions.model.MyCard;
import com.example.user.onedaynquestions.service.TemporalStorage;

/**
 * Created by user on 2016-06-07.
 */
public class CardSolvingActivity extends AppCompatActivity {


    public static Activity thisActivity;

    TextView examiner, group, timer, type, question,  hint;
    EditText answer;
    Button showHint, submit;

    final int TIME_LIMIT = 15;
    int remainTime = TIME_LIMIT;
    CountDownTimer timeChecker;

    int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        showHint = (Button)findViewById(R.id.cardsolving_btn_showhint);

        timeChecker = new CountDownTimer(TIME_LIMIT * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainTime--;
                timer.setText("" + remainTime);
                timer.postInvalidate();
            }

            @Override
            public void onFinish() {
                remainTime--;
                timer.setText("" + remainTime);
                timer.postInvalidate();
                Toast.makeText
                        (thisActivity, "Time Over!", Toast.LENGTH_SHORT).show();

            }
        };

        setCard();


    }

    public void setCard(){
        if(!TemporalStorage.isEmpty()){
            //MyCard card  = TemporalStorage.consumeReceivedQuestions();
            MyCard card  = TemporalStorage.getReceivedQuestion();
            if(card == null) return;
            examiner.setText(card.getMyCardMaker());
            examiner.postInvalidate();
            group.setText("from [" + card.getMyCardGroup()+ "] Group");
            group.postInvalidate();
            remainTime = TIME_LIMIT;
            timer.setText("" + remainTime);
            timer.postInvalidate();
            type.setText(""+card.getMyCardType());
            type.postInvalidate();
            question.setText(card.getMyCardQuestion());
            question.postInvalidate();

            card = null;
        }

        timeChecker.start();
    }




    public void mOnClick(View v) {

        if(remainTime < 0) {
        }
        setCard();
    }

}
