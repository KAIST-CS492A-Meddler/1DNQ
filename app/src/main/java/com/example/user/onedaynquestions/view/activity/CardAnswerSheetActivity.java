package com.example.user.onedaynquestions.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.onedaynquestions.R;

import org.w3c.dom.Text;

/**
 * Created by user on 2016-06-07.
 */
public class CardAnswerSheetActivity extends AppCompatActivity {


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

        Toast.makeText(getApplicationContext(), "- card_type: " + card_type +
                "\n- card_question: " + card_question +
                "\n- card_answer: " + card_answer +
                "\n- my_answer: " + my_answer +
                "\n- card_id: " + card_id, Toast.LENGTH_SHORT).show();

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
                intent_goeval.putExtra("self_eval", 0);
                startActivity(intent_goeval);
                break;
            case R.id.cardanswer_btn_right:
                intent_goeval.putExtra("self_eval", 1);
                startActivity(intent_goeval);
                break;
        }
    }


}
