package com.example.user.onedaynquestions.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.user.onedaynquestions.R;
import com.example.user.onedaynquestions.archive.MyRoutine;

import java.util.StringTokenizer;

/**
 * Created by user on 2016-06-07.
 */
public class CardSolvingActivity extends AppCompatActivity {


    public static Activity thisActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardsolving);


        thisActivity = this;


    }



    public void mOnClick(View v) {

    }

}
