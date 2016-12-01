package com.example.user.onedaynquestions.device;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.user.onedaynquestions.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sarahsong on 2016-05-26.
 */
public class WorkoutRecord2 extends AppCompatActivity{
    @Override
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_workout_record2);

        List<Double> workoutData = new ArrayList<Double>();
        //here to input data from DB
        workoutData.add(300.0);workoutData.add(500.0);workoutData.add(100.0);workoutData.add(300.0);workoutData.add(800.0);workoutData.add(400.0);workoutData.add(700.0);

        GraphView gv = (GraphView) findViewById(R.id.workoutlinegraph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {new DataPoint(1,workoutData.get(0)),
                new DataPoint(2,workoutData.get(1)),
                new DataPoint(3,workoutData.get(2)),
                new DataPoint(4,workoutData.get(3)),
                new DataPoint(5,workoutData.get(4)),
                new DataPoint(6,workoutData.get(5)),
                new DataPoint(7,workoutData.get(6))});
        gv.addSeries(series);
    }
}
