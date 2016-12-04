package com.example.user.onedaynquestions.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.onedaynquestions.R;
import com.example.user.onedaynquestions.archive.RecordAgent;
import com.example.user.onedaynquestions.archive.MyRecord;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by user on 2016-06-07.
 */
public class FinishExerciseActivity extends AppCompatActivity {

    private static final int VIEW_VISIBLE = 71;
    private static final int VIEW_INVISIBLE = 72;
    private static final int VIEW_GONE = 73;

    ArrayList<RecordAgent> agentRecords;
    String routineName;

    TextView tv_myroutine;

    LinearLayout layout_eq1;
    TextView tv_eq1_name;
    TextView tv_eq1_goal;
    TextView tv_eq1_record_times;
    TextView tv_eq1_record_secs;

    LinearLayout layout_eq2;
    TextView tv_eq2_name;
    TextView tv_eq2_goal;
    TextView tv_eq2_record_times;
    TextView tv_eq2_record_secs;

    LinearLayout layout_eq3;
    TextView tv_eq3_name;
    TextView tv_eq3_goal;
    TextView tv_eq3_record_times;
    TextView tv_eq3_record_secs;

    LinearLayout layout_eq4;
    TextView tv_eq4_name;
    TextView tv_eq4_goal;
    TextView tv_eq4_record_times;
    TextView tv_eq4_record_secs;

    LinearLayout layout_eq5;
    TextView tv_eq5_name;
    TextView tv_eq5_goal;
    TextView tv_eq5_record_times;
    TextView tv_eq5_record_secs;

    EditText et_record_name;
    Button btn_check;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finishexercise);

        //Receive the result arraylist
        Intent intent = getIntent();
        agentRecords = (ArrayList<RecordAgent>) intent.getSerializableExtra("agentRecords");
        routineName = (String) intent.getStringExtra("routineName");

        Log.d("agentRecords", "[FinishExerciseActivity] Intent extra is received (size: " + agentRecords.size() + ")");
        Log.d("agentRecords", "[FinishExerciseActivity] Routine name: " + routineName);


        initWidgets();
        initResultTable();

    }

    private void initWidgets() {
        tv_myroutine = (TextView)findViewById(R.id.finishexercise_tv_myroutine);

        layout_eq1 = (LinearLayout)findViewById(R.id.finishexercise_tv_eq1);
        tv_eq1_name = (TextView)findViewById(R.id.finishexercise_tv_eq1_name);
        tv_eq1_goal = (TextView)findViewById(R.id.finishexercise_tv_eq1_goal);
        tv_eq1_record_times = (TextView)findViewById(R.id.finishexercise_tv_eq1_record_times);
        tv_eq1_record_secs = (TextView)findViewById(R.id.finishexercise_tv_eq1_record_secs);

        layout_eq2 = (LinearLayout)findViewById(R.id.finishexercise_tv_eq2);
        tv_eq2_name = (TextView)findViewById(R.id.finishexercise_tv_eq2_name);
        tv_eq2_goal = (TextView)findViewById(R.id.finishexercise_tv_eq2_goal);
        tv_eq2_record_times = (TextView)findViewById(R.id.finishexercise_tv_eq2_record_times);
        tv_eq2_record_secs = (TextView)findViewById(R.id.finishexercise_tv_eq2_record_secs);

        layout_eq3 = (LinearLayout)findViewById(R.id.finishexercise_tv_eq3);
        tv_eq3_name = (TextView)findViewById(R.id.finishexercise_tv_eq3_name);
        tv_eq3_goal = (TextView)findViewById(R.id.finishexercise_tv_eq3_goal);
        tv_eq3_record_times = (TextView)findViewById(R.id.finishexercise_tv_eq3_record_times);
        tv_eq3_record_secs = (TextView)findViewById(R.id.finishexercise_tv_eq3_record_secs);

        layout_eq4 = (LinearLayout)findViewById(R.id.finishexercise_tv_eq4);
        tv_eq4_name = (TextView)findViewById(R.id.finishexercise_tv_eq4_name);
        tv_eq4_goal = (TextView)findViewById(R.id.finishexercise_tv_eq4_goal);
        tv_eq4_record_times = (TextView)findViewById(R.id.finishexercise_tv_eq4_record_times);
        tv_eq4_record_secs = (TextView)findViewById(R.id.finishexercise_tv_eq4_record_secs);

        layout_eq5 = (LinearLayout)findViewById(R.id.finishexercise_tv_eq5);
        tv_eq5_name = (TextView)findViewById(R.id.finishexercise_tv_eq5_name);
        tv_eq5_goal = (TextView)findViewById(R.id.finishexercise_tv_eq5_goal);
        tv_eq5_record_times = (TextView)findViewById(R.id.finishexercise_tv_eq5_record_times);
        tv_eq5_record_secs = (TextView)findViewById(R.id.finishexercise_tv_eq5_record_secs);

        et_record_name = (EditText) findViewById(R.id.finishexercise_et_recordname);

        btn_check = (Button) findViewById(R.id.finishexercise_btn_finish);
        btn_check.setBackgroundResource(R.drawable.effect_button_press);
    }

    private void initResultTable() {
        if (routineName != null && !routineName.equals("")) {
            tv_myroutine.setText(routineName);
        }

        boolean isMoreAgent = true;

        if (agentRecords == null) {
            return;
        }

        /* EQ1 */
        if (agentRecords.size() < 1) {
            setEq1RecordVisibility(VIEW_GONE);
        } else {
            setEq1RecordVisibility(VIEW_VISIBLE);
            tv_eq1_name.setText(agentRecords.get(0).getAgentName());
            tv_eq1_goal.setText(agentRecords.get(0).makeGoalString());
            tv_eq1_record_times.setText(agentRecords.get(0).getRecordCount() + " times");
            tv_eq1_record_secs.setText(agentRecords.get(0).getRecordTime() + " secs");
        }

        /* EQ2 */
        if (agentRecords.size() < 2) {
            setEq2RecordVisibility(VIEW_GONE);
        } else {
            setEq2RecordVisibility(VIEW_VISIBLE);
            tv_eq2_name.setText(agentRecords.get(1).getAgentName());
            tv_eq2_goal.setText(agentRecords.get(1).makeGoalString());
            tv_eq2_record_times.setText(agentRecords.get(1).getRecordCount() + " times");
            tv_eq2_record_secs.setText(agentRecords.get(1).getRecordTime() + " secs");
        }

        /* EQ3 */
        if (agentRecords.size() < 3) {
            setEq3RecordVisibility(VIEW_GONE);
        } else {
            setEq3RecordVisibility(VIEW_VISIBLE);
            tv_eq3_name.setText(agentRecords.get(2).getAgentName());
            tv_eq3_goal.setText(agentRecords.get(2).makeGoalString());
            tv_eq3_record_times.setText(agentRecords.get(2).getRecordCount() + " times");
            tv_eq3_record_secs.setText(agentRecords.get(2).getRecordTime() + " secs");
        }

        /* EQ4 */
        if (agentRecords.size() < 4) {
            setEq4RecordVisibility(VIEW_GONE);
        } else {
            setEq4RecordVisibility(VIEW_VISIBLE);
            tv_eq4_name.setText(agentRecords.get(3).getAgentName());
            tv_eq4_goal.setText(agentRecords.get(3).makeGoalString());
            tv_eq4_record_times.setText(agentRecords.get(3).getRecordCount() + " times");
            tv_eq4_record_secs.setText(agentRecords.get(3).getRecordTime() + " secs");
        }

        /* EQ5 */
        if (agentRecords.size() < 5) {
            setEq5RecordVisibility(VIEW_GONE);
        } else {
            setEq5RecordVisibility(VIEW_VISIBLE);
            tv_eq5_name.setText(agentRecords.get(4).getAgentName());
            tv_eq5_goal.setText(agentRecords.get(4).makeGoalString());
            tv_eq5_record_times.setText(agentRecords.get(4).getRecordCount() + " times");
            tv_eq5_record_secs.setText(agentRecords.get(4).getRecordTime() + " secs");
        }
    }





    public void mOnClick(View v) {
        switch (v.getId()) {
            case R.id.finishexercise_btn_finish:
                if (et_record_name.getText().toString().equals("") || et_record_name.getText() == null) {
                    Snackbar.make(v, "Please write your record name first", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                } else {
                    String recordName = et_record_name.getText().toString();

                    //Generate a record ID
                    String recordId;
                    int recordCount = MainActivity.hereDB.getMyRecordCount();
                    if (recordCount < 10) {
                        recordId = String.format("RECORD0%d", recordCount + 1);
                    } else {
                        recordId = String.format("RECORD%d", recordCount + 1);
                    }

                    //Set current datetime
                    SimpleDateFormat datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String currentDateandTime = datetime.format(new Date());

                    //Make a new tuple instance
                    MyRecord myRecordTuple = new MyRecord();
                    myRecordTuple.setRecordId(recordId);
                    myRecordTuple.setRecordName(recordName);
                    myRecordTuple.setRecordDateTime(currentDateandTime);
                    //EQ1
                    if (agentRecords.size() >= 1) {
                        myRecordTuple.setRecordEq1Id(agentRecords.get(0).getAgentMacId());
                        //Count numbers
                        if (getRecordType(agentRecords.get(0).getAgentType()) == 1) {
                            myRecordTuple.setRecordEq1Done(agentRecords.get(0).getRecordCount());
                        }
                        //Count time
                        else if (getRecordType(agentRecords.get(0).getAgentType()) == 2) {
                            myRecordTuple.setRecordEq1Done(agentRecords.get(0).getRecordTime());
                        }
                    }
                    //EQ2
                    if (agentRecords.size() >= 2) {
                        myRecordTuple.setRecordEq2Id(agentRecords.get(1).getAgentMacId());
                        //Count numbers
                        if (getRecordType(agentRecords.get(1).getAgentType()) == 1) {
                            myRecordTuple.setRecordEq2Done(agentRecords.get(1).getRecordCount());
                        }
                        //Count time
                        else if (getRecordType(agentRecords.get(1).getAgentType()) == 2) {
                            myRecordTuple.setRecordEq2Done(agentRecords.get(1).getRecordTime());
                        }
                    }
                    //EQ3
                    if (agentRecords.size() >= 3) {
                        myRecordTuple.setRecordEq3Id(agentRecords.get(2).getAgentMacId());
                        //Count numbers
                        if (getRecordType(agentRecords.get(2).getAgentType()) == 1) {
                            myRecordTuple.setRecordEq3Done(agentRecords.get(2).getRecordCount());
                        }
                        //Count time
                        else if (getRecordType(agentRecords.get(2).getAgentType()) == 2) {
                            myRecordTuple.setRecordEq3Done(agentRecords.get(2).getRecordTime());
                        }
                    }
                    //EQ4
                    if (agentRecords.size() >= 4) {
                        myRecordTuple.setRecordEq4Id(agentRecords.get(3).getAgentMacId());
                        //Count numbers
                        if (getRecordType(agentRecords.get(3).getAgentType()) == 1) {
                            myRecordTuple.setRecordEq4Done(agentRecords.get(3).getRecordCount());
                        }
                        //Count time
                        else if (getRecordType(agentRecords.get(3).getAgentType()) == 2) {
                            myRecordTuple.setRecordEq4Done(agentRecords.get(3).getRecordTime());
                        }
                    }
                    //EQ5
                    if (agentRecords.size() >= 5) {
                        myRecordTuple.setRecordEq5Id(agentRecords.get(4).getAgentMacId());
                        //Count numbers
                        if (getRecordType(agentRecords.get(4).getAgentType()) == 1) {
                            myRecordTuple.setRecordEq5Done(agentRecords.get(4).getRecordCount());
                        }
                        //Count time
                        else if (getRecordType(agentRecords.get(4).getAgentType()) == 2) {
                            myRecordTuple.setRecordEq5Done(agentRecords.get(4).getRecordTime());
                        }
                    }

                    //Insert the tuple to DB
                    MainActivity.hereDB.insertRecord(myRecordTuple);

                    Toast.makeText(getApplicationContext(), "Your record is saved!\n" + recordName, Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }


    /**
     * Get record type (Measure countNum or time?)
     * @param agentType
     * @return
     */
    private int getRecordType(int agentType) {
        if (agentType == 1 || agentType == 2) {
            return 1;
        } else {
            return 2;
        }
    }


    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "Your record is saved!", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }

    private void setEq1RecordVisibility(int visible) {
        switch (visible) {
            case VIEW_VISIBLE:
                Log.d("agentRecords", "[Eq1] VISIBLE ");
                tv_eq1_name.setVisibility(View.VISIBLE);
                tv_eq1_goal.setVisibility(View.VISIBLE);
                tv_eq1_record_times.setVisibility(View.VISIBLE);
                tv_eq1_record_secs.setVisibility(View.VISIBLE);
                break;
            case VIEW_INVISIBLE:
                tv_eq1_name.setVisibility(View.INVISIBLE);
                tv_eq1_goal.setVisibility(View.INVISIBLE);
                tv_eq1_record_times.setVisibility(View.INVISIBLE);
                tv_eq1_record_secs.setVisibility(View.INVISIBLE);
                break;
            case VIEW_GONE:
                Log.d("agentRecords", "[Eq1] GONE ");
                tv_eq1_name.setVisibility(View.GONE);
                tv_eq1_goal.setVisibility(View.GONE);
                tv_eq1_record_times.setVisibility(View.GONE);
                tv_eq1_record_secs.setVisibility(View.GONE);
                break;
        }
    }

    private void setEq2RecordVisibility(int visible) {
        switch (visible) {
            case VIEW_VISIBLE:
                Log.d("agentRecords", "[Eq2] VISIBLE ");
                tv_eq2_name.setVisibility(View.VISIBLE);
                tv_eq2_goal.setVisibility(View.VISIBLE);
                tv_eq2_record_times.setVisibility(View.VISIBLE);
                tv_eq2_record_secs.setVisibility(View.VISIBLE);
                break;
            case VIEW_INVISIBLE:
                tv_eq2_name.setVisibility(View.INVISIBLE);
                tv_eq2_goal.setVisibility(View.INVISIBLE);
                tv_eq2_record_times.setVisibility(View.INVISIBLE);
                tv_eq2_record_secs.setVisibility(View.INVISIBLE);
                break;
            case VIEW_GONE:
                Log.d("agentRecords", "[Eq2] GONE ");
                tv_eq2_name.setVisibility(View.GONE);
                tv_eq2_goal.setVisibility(View.GONE);
                tv_eq2_record_times.setVisibility(View.GONE);
                tv_eq2_record_secs.setVisibility(View.GONE);
                break;
        }
    }

    private void setEq3RecordVisibility(int visible) {
        switch (visible) {
            case VIEW_VISIBLE:
                Log.d("agentRecords", "[Eq3] VISIBLE ");
                tv_eq3_name.setVisibility(View.VISIBLE);
                tv_eq3_goal.setVisibility(View.VISIBLE);
                tv_eq3_record_times.setVisibility(View.VISIBLE);
                tv_eq3_record_secs.setVisibility(View.VISIBLE);
                break;
            case VIEW_INVISIBLE:
                tv_eq3_name.setVisibility(View.INVISIBLE);
                tv_eq3_goal.setVisibility(View.INVISIBLE);
                tv_eq3_record_times.setVisibility(View.INVISIBLE);
                tv_eq3_record_secs.setVisibility(View.INVISIBLE);
                break;
            case VIEW_GONE:
                Log.d("agentRecords", "[Eq3] GONE ");
                tv_eq3_name.setVisibility(View.GONE);
                tv_eq3_goal.setVisibility(View.GONE);
                tv_eq3_record_times.setVisibility(View.GONE);
                tv_eq3_record_secs.setVisibility(View.GONE);
                break;
        }
    }

    private void setEq4RecordVisibility(int visible) {
        switch (visible) {
            case VIEW_VISIBLE:
                Log.d("agentRecords", "[Eq4] VISIBLE ");
                tv_eq4_name.setVisibility(View.VISIBLE);
                tv_eq4_goal.setVisibility(View.VISIBLE);
                tv_eq4_record_times.setVisibility(View.VISIBLE);
                tv_eq4_record_secs.setVisibility(View.VISIBLE);
                break;
            case VIEW_INVISIBLE:
                tv_eq4_name.setVisibility(View.INVISIBLE);
                tv_eq4_goal.setVisibility(View.INVISIBLE);
                tv_eq4_record_times.setVisibility(View.INVISIBLE);
                tv_eq4_record_secs.setVisibility(View.INVISIBLE);
                break;
            case VIEW_GONE:
                Log.d("agentRecords", "[Eq4] GONE ");
                tv_eq4_name.setVisibility(View.GONE);
                tv_eq4_goal.setVisibility(View.GONE);
                tv_eq4_record_times.setVisibility(View.GONE);
                tv_eq4_record_secs.setVisibility(View.GONE);
                break;
        }
    }

    private void setEq5RecordVisibility(int visible) {
        switch (visible) {
            case VIEW_VISIBLE:
                Log.d("agentRecords", "[Eq5] VISIBLE ");
                tv_eq5_name.setVisibility(View.VISIBLE);
                tv_eq5_goal.setVisibility(View.VISIBLE);
                tv_eq5_record_times.setVisibility(View.VISIBLE);
                tv_eq5_record_secs.setVisibility(View.VISIBLE);
                break;
            case VIEW_INVISIBLE:
                tv_eq5_name.setVisibility(View.INVISIBLE);
                tv_eq5_goal.setVisibility(View.INVISIBLE);
                tv_eq5_record_times.setVisibility(View.INVISIBLE);
                tv_eq5_record_secs.setVisibility(View.INVISIBLE);
                break;
            case VIEW_GONE:
                Log.d("agentRecords", "[Eq5] GONE ");
                tv_eq5_name.setVisibility(View.GONE);
                tv_eq5_goal.setVisibility(View.GONE);
                tv_eq5_record_times.setVisibility(View.GONE);
                tv_eq5_record_secs.setVisibility(View.GONE);
                break;
        }
    }
}
