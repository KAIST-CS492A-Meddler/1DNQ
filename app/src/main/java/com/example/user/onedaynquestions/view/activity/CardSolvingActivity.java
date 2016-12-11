package com.example.user.onedaynquestions.view.activity;

import android.app.Activity;
import android.content.Intent;
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
import com.example.user.onedaynquestions.model.MyCard;
import com.example.user.onedaynquestions.service.WakefulPushReceiver;

/**
 * Created by user on 2016-06-07.
 */
public class CardSolvingActivity extends AppCompatActivity {


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


        //TODO: Floating button 없애기

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
        timeChecker.cancel();
        if(!WakefulPushReceiver.isEmpty()){
            //MyCard receivedCard  = TemporalStorage.consumeReceivedQuestions();
            receivedCard = WakefulPushReceiver.getReceivedQuestion();

            if(receivedCard == null){
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

    }

    @Override
    public void onPause(){
        timeChecker.cancel();
        super.onPause();
    }

    public void mOnClick(View v) {
        switch (v.getId()) {
            case R.id.cardsolving_btn_showhint:
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
                if (receivedCard == null) {
                    Log.d("CardSolvingActivity", "receivedCard is null");
                } else {

                    Intent intent_goanswer = new Intent(getApplicationContext(), CardAnswerSheetActivity.class);
                    intent_goanswer.putExtra("card_id", receivedCard.getMyCardId());
                    intent_goanswer.putExtra("card_type", receivedCard.getMyCardType());
                    intent_goanswer.putExtra("card_question", receivedCard.getMyCardQuestion());
                    intent_goanswer.putExtra("card_answer", receivedCard.getMyCardAnswer());
                    intent_goanswer.putExtra("my_answer", answer.getText().toString());
                    startActivity(intent_goanswer);
                }
                break;
        }
    }
}
