package com.example.user.onedaynquestions.device;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.user.onedaynquestions.R;

/**
 * Created by sarahsong on 2016-05-26.
 * Cheer up!
 */
public class WorkoutRecords extends AppCompatActivity implements View.OnClickListener{

    Intent testIntent1;
    Intent testIntent2;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_workout_records);
        Button workout1 = (Button)findViewById(R.id.workout1);
        workout1.setOnClickListener(this);
        Button workout2 = (Button)findViewById(R.id.workout2);
        workout2.setOnClickListener(this);

        testIntent1 = new Intent(this, WorkoutRecord.class);
        testIntent2 = new Intent(this, WorkoutRecord2.class);


        startActivity(testIntent2);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.workout1:
                startActivity(testIntent1);
                break;
            case R.id.workout2:
                startActivity(testIntent2);
                break;
        }
    }
}
