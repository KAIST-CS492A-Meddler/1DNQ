package com.example.user.onedaynquestions.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.user.onedaynquestions.R;
import com.example.user.onedaynquestions.model.MyRoutine;

import java.util.StringTokenizer;

/**
 * Created by user on 2016-06-07.
 */
public class NewCardActivity extends AppCompatActivity {

    MyRoutine selectedRoutine;

    public static Activity thisActivity;
//    ImageView startexercise_iv_girl;
//    ImageView startexercise_iv_start;
//
//    TextView tv_routine_name;
//    TextView tv_routine_eq1;
//    TextView tv_routine_eq2;
//    TextView tv_routine_eq3;
//    TextView tv_routine_eq4;
//    TextView tv_routine_eq5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newcard);

        Intent intent = getIntent();
        selectedRoutine = (MyRoutine) intent.getSerializableExtra("selectedRoutine");

        initWidgets();

        if (selectedRoutine == null) {
            Toast.makeText(getApplicationContext(), "Routine is not set.", Toast.LENGTH_SHORT).show();
        }

        initWidetValues();



        Log.d("parseRoutineGoal", parseRoutineGoal(3, "3|10|-1"));
        Log.d("parseRoutineGoal", parseRoutineGoal(3, "5|-1|120"));
        Log.d("parseRoutineGoal", parseRoutineGoal(3, "1|30|-1"));
        Log.d("parseRoutineGoal", parseRoutineGoal(3, "1|-1|30"));



        thisActivity = this;

//        startexercise_iv_girl = (ImageView) findViewById(R.id.startexercise_iv_girl);
//        startexercise_iv_start = (ImageView) findViewById(R.id.startexercise_iv_start);
//
//        //For animation + activity transition
//        startexercise_iv_start.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                startexercise_iv_girl.setImageResource(R.drawable.start_exercise_girl_anim);
//
//                Handler mHandler = new Handler();
//                mHandler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        startexercise_iv_girl.setImageResource(R.drawable.start_exercise_girl);
//                        try {
//                            Thread.sleep(100);
//
////                            Intent intent_doingexercise = new Intent(getApplicationContext(), DoingExerciseActivity.class);
////                            startActivity(intent_doingexercise);
////                            NewCardActivity.this.finish();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, 50);
//
//                return false;
//            }
//        });

    }


    private void initWidgets() {

//        tv_routine_name = (TextView) findViewById(R.id.startexercise_tv_myroutine);
//        tv_routine_eq1 = (TextView) findViewById(R.id.startexercise_tv_eq1);
//        tv_routine_eq2 = (TextView) findViewById(R.id.startexercise_tv_eq2);
//        tv_routine_eq3 = (TextView) findViewById(R.id.startexercise_tv_eq3);
//        tv_routine_eq4 = (TextView) findViewById(R.id.startexercise_tv_eq4);
//        tv_routine_eq5 = (TextView) findViewById(R.id.startexercise_tv_eq5);
    }

    private void initWidetValues() {

//        //Initialize equipment information
//        if (selectedRoutine != null) {
//            tv_routine_name.setText(selectedRoutine.getRoutineName());
//
//            //Initialize equipment names
//            String routine_eq1_id = selectedRoutine.getRoutineEq1Id();
//            String routine_eq2_id = selectedRoutine.getRoutineEq2Id();
//            String routine_eq3_id = selectedRoutine.getRoutineEq3Id();
//            String routine_eq4_id = selectedRoutine.getRoutineEq4Id();
//            String routine_eq5_id = selectedRoutine.getRoutineEq5Id();
//
//            String eq1String = "";
//            String eq2String = "";
//            String eq3String = "";
//            String eq4String = "";
//            String eq5String = "";
//
//            if (!routine_eq1_id.equals("-1")) {
//                String routine_eq1_name = MainActivity.hereDB.getAgentNameByMacId(routine_eq1_id);
//                if (routine_eq1_name != null) {
//                    eq1String = routine_eq1_name;
//                } else {
//                    eq1String = "DB failed";
//                }
//            }
//
//            if (!routine_eq2_id.equals("-1")) {
//                String routine_eq2_name = MainActivity.hereDB.getAgentNameByMacId(routine_eq2_id);
//                if (routine_eq2_name != null) {
//                    eq2String = routine_eq2_name;
//                }else {
//                    eq2String = "DB failed";
//                }
//            }
//
//            if (!routine_eq3_id.equals("-1")) {
//                String routine_eq3_name = MainActivity.hereDB.getAgentNameByMacId(routine_eq3_id);
//                if (routine_eq3_name != null) {
//                    eq3String = routine_eq3_name;
//                }else {
//                    eq3String = "DB failed";
//                }
//            }
//
//            if (!routine_eq4_id.equals("-1")) {
//                String routine_eq4_name = MainActivity.hereDB.getAgentNameByMacId(routine_eq4_id);
//                if (routine_eq4_name != null) {
//                    eq4String = routine_eq4_name;
//                }else {
//                    eq4String = "DB failed";
//                }
//            }
//
//            if (!routine_eq5_id.equals("-1")) {
//                String routine_eq5_name = MainActivity.hereDB.getAgentNameByMacId(routine_eq5_id);
//                if (routine_eq5_name != null) {
//                    eq5String = routine_eq5_name;
//                }else {
//                    eq5String = "DB failed";
//                }
//            }
//
//
//            //Initialize goal of equipments & setText
//            if (!eq1String.equals("")) {
//                eq1String += ": " + parseRoutineGoal(3, selectedRoutine.getRoutineEq1Goal());
//                tv_routine_eq1.setText(eq1String);
//            } else {
//                tv_routine_eq1.setVisibility(View.INVISIBLE);
//            }
//            if (!eq2String.equals("")) {
//                eq2String += ": " + parseRoutineGoal(3, selectedRoutine.getRoutineEq2Goal());
//                tv_routine_eq2.setText(eq2String);
//            } else {
//                tv_routine_eq2.setVisibility(View.INVISIBLE);
//            }
//            if (!eq3String.equals("")) {
//                eq3String += ": " + parseRoutineGoal(3, selectedRoutine.getRoutineEq3Goal());
//                tv_routine_eq3.setText(eq3String);
//            } else {
//                tv_routine_eq3.setVisibility(View.INVISIBLE);
//            }
//            if (!eq4String.equals("")) {
//                eq4String += ": " + parseRoutineGoal(3, selectedRoutine.getRoutineEq4Goal());
//                tv_routine_eq4.setText(eq4String);
//            } else {
//                tv_routine_eq4.setVisibility(View.INVISIBLE);
//            }
//            if (!eq5String.equals("")) {
//                eq5String += ": " + parseRoutineGoal(3, selectedRoutine.getRoutineEq5Goal());
//                tv_routine_eq5.setText(eq5String);
//            }else {
//                tv_routine_eq5.setVisibility(View.INVISIBLE);
//            }
//
//
//        } else {
//            tv_routine_name.setText("There is no received routine.");
//        }
    }


    /**
     *
     * @param outputStyle   1:30개씩 2세트, 2:2세트 30개, 3:30 times X 2 sets
     * @param rawGoal       Raw String
     * @return              Parsed/Refined String
     */
    private String parseRoutineGoal(int outputStyle, String rawGoal) {

        String goalSentence = "";

        //Default: style 3
        outputStyle = 3;

        //Type 1. 3|15|-1
        //Type 2. 2|-1|60

        int goalType = 0;   //1: count times, 2: count secs, 3: count times & secs

        String set = "";    //sets
        String count = "";  //times
        String time = "";   //secs

        StringTokenizer tokens = new StringTokenizer(rawGoal, "|");
        set = tokens.nextToken();
        count = tokens.nextToken();
        time = tokens.nextToken();

        Log.d("TokenizerExercise", "set[" + set + "], count[" + count + "], time[" + "]");

        //Single or multiple
        int intSet = Integer.parseInt(set);
        int intCount = Integer.parseInt(count);
        int intTime = Integer.parseInt(time);

        set += " SET";
        count += " TIME";
        time += " SEC";

        //Check goal type
        if (intCount == -1) {
            goalType = 2;
        }
        if (intTime == -1) {
            goalType = 1;
        }
        if (intTime != -1 && intCount != -1) {
            goalType = 3;
        }

        if (intSet > 1) {
            set += "S";
        }
        if (intCount > 1) {
            count += "S";
        }
        if (intTime > 1) {
            time += "S";
        }

        switch (goalType) {
            //Count times (회수)
            case 1:
                goalSentence = count + " X " + set;
                break;
            //Count secs (시간)
            case 2:
                goalSentence = time + " X " + set;
                break;
            // Count both times & secs
            case 3:
                goalSentence = count + " X " + set + " (" + time + ")";
                break;
        }

        return goalSentence;

    }

    public void mOnClick(View v) {
//        switch (v.getId()) {
//            case R.id.startexercise_btn_back:
//                finish();
//                break;
//        }
    }

}
