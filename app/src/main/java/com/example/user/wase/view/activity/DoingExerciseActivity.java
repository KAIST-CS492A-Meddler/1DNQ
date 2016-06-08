package com.example.user.wase.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.wase.R;
import com.example.user.wase.utility.TaskScheduler;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by user on 2016-06-07.
 */
public class DoingExerciseActivity extends AppCompatActivity {

    LinearLayout layout_whole;

    TextView tv_timer;

    LinearLayout layout_title;
    ImageView iv_current_eq;
    TextView tv_eq_order;
    TextView tv_eq_name;
    TextView tv_eq_goal;
    TextView tv_eq_count;


    int countCurrentEq;

    TaskScheduler timer;
    boolean isTimerRunning;
    int elapsedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doingexercise);

        StartExerciseActivity.thisActivity.finish();

        initWidgets();


        timer = new TaskScheduler();
        timer.scheduleAtFixedRate(increaseTimer, 1000);

    }

    private void initWidgets() {
        isTimerRunning = true;
        elapsedTime = 0;
        countCurrentEq = 0;

        layout_whole = (LinearLayout) findViewById(R.id.doingexercise_layout_whole);
        layout_title = (LinearLayout) findViewById(R.id.doingexercise_layout_title);

        tv_timer = (TextView) findViewById(R.id.doingexercise_tv_timer);

        iv_current_eq = (ImageView) findViewById(R.id.doingexercise_iv_current_eq);
        tv_eq_order = (TextView) findViewById(R.id.doingexercise_tv_eq_order);
        tv_eq_name = (TextView) findViewById(R.id.doingexercise_tv_eq_name);
        tv_eq_goal = (TextView) findViewById(R.id.doingexercise_tv_eq_goal);
        tv_eq_count = (TextView) findViewById(R.id.doingexercise_tv_eq_count);

    }

    private String secondToTimerString(int seconds) {

        int mins = seconds / 60;
        int secs = seconds % 60;

        String timerString = String.format("%02d:%02d", mins, secs);

        return timerString;
    }



    private Runnable increaseTimer = new Runnable() {
        @Override
        public void run() {
            tv_timer.setText(secondToTimerString(elapsedTime));
            if (isTimerRunning) {
                elapsedTime++;
            }
        }
    };


    private void initializeRecord() {
        countCurrentEq = 0;
        elapsedTime = 0;

        //Restart exercising -> Run timer
        isTimerRunning = true;
    }

    public void mOnClick(View v) {
        switch (v.getId()) {
            case R.id.doingexercise_btn_back:
                AlertDialog quitExercisingAlert = askQuitExercising();
                quitExercisingAlert.show();
                break;
            case R.id.doingexercise_btn_skipgonext:
                if (isTimerRunning) {
                    isTimerRunning = false;
                }
                //timer.stop(increaseTimer);

                AlertDialog goToNextExerciseAlert = askGoToNextExercise();
                goToNextExerciseAlert.show();

                Toast.makeText(getApplicationContext(), "Skip this equipment\nor Go to next step", Toast.LENGTH_SHORT).show();
                break;
        }
    }



    private AlertDialog askQuitExercising() {
        AlertDialog myDeleteDialogBox = new AlertDialog.Builder(this)
                .setTitle("Quit Exercising")
                .setMessage("Are you sure you want to stop exercising and go to the main page?")
                .setPositiveButton("Give up", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO: Delete selected routines
                        Toast.makeText(getApplicationContext(), "Exercising is stopped.", Toast.LENGTH_SHORT).show();
                        finish();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();

        return myDeleteDialogBox;
    }

    private AlertDialog askGoToNextExercise() {
        AlertDialog myGoNextDialogBox = new AlertDialog.Builder(this)
                .setTitle("Go to next step")
                .setMessage("Are you sure you want to finish current exercise?")
                .setPositiveButton("Go next!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO: Delete selected routines
                        Toast.makeText(getApplicationContext(), "Exercising is stopped.", Toast.LENGTH_SHORT).show();
                        finish();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();

        return myGoNextDialogBox;
    }

    @Override
    public void onBackPressed() {
        AlertDialog quitExercisingAlert = askQuitExercising();
        quitExercisingAlert.show();
        //super.onBackPressed();
    }
}