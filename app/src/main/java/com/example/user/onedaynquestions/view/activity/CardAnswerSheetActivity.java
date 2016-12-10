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

import com.example.user.onedaynquestions.R;

/**
 * Created by user on 2016-06-07.
 */
public class CardAnswerSheetActivity extends AppCompatActivity {


    public static Activity thisActivity;

    Context mContext;


    int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        count = 0;
        thisActivity = this;
        setContentView(R.layout.activity_cardanswersheet);


    }


    @Override
    public void onPause(){
        super.onPause();
    }

    public void mOnClick(View v) {
        Intent intent_goeval = new Intent(getApplicationContext(), CardEvaluationActivity.class);

        switch (v.getId()) {
            case R.id.cardanswer_btn_wrong:
                startActivity(intent_goeval);
                break;
            case R.id.cardanswer_btn_right:
                startActivity(intent_goeval);
                break;
        }
    }


}
