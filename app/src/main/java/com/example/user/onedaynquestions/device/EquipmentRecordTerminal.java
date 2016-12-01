package com.example.user.onedaynquestions.device;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.example.user.onedaynquestions.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by user on 2016-05-31.
 */
public class EquipmentRecordTerminal extends Activity {
    public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    public static final String EXTRAS_DEVICE_RECORD = "DEVICE_RECORD";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.equipment_record_graph);

        Calendar calendar = Calendar.getInstance();

        List<Double> workoutData = new ArrayList<Double>();
        //here to input data from DB
        workoutData.add(300.0);workoutData.add(500.0);workoutData.add(100.0);workoutData.add(300.0);workoutData.add(800.0);workoutData.add(400.0);workoutData.add(700.0);

        GraphView gv = (GraphView) findViewById(R.id.week_linear_graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {new DataPoint(1,workoutData.get(0)),
                new DataPoint(2,workoutData.get(1)),
                new DataPoint(3,workoutData.get(2)),
                new DataPoint(4,workoutData.get(3)),
                new DataPoint(5,workoutData.get(4)),
                new DataPoint(6,workoutData.get(5)),
                new DataPoint(7,workoutData.get(6))});

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(gv);
        staticLabelsFormatter.setHorizontalLabels(new String[] {getDay(calendar.get(Calendar.DAY_OF_WEEK)-6), getDay(calendar.get(Calendar.DAY_OF_WEEK)-5), getDay(calendar.get(Calendar.DAY_OF_WEEK)-4), getDay(calendar.get(Calendar.DAY_OF_WEEK)-3), getDay(calendar.get(Calendar.DAY_OF_WEEK)-2), getDay(calendar.get(Calendar.DAY_OF_WEEK)-1), getDay(calendar.get(Calendar.DAY_OF_WEEK))});
        gv.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

        series.setDrawDataPoints(true);
        series.setColor(Color.BLUE);
        series.setDataPointsRadius(10);
        series.setThickness(8);
        gv.addSeries(series);

    }

    String getDay (int date){
        int modulo = date%7;
        if (modulo < 0) modulo+=7;
        switch(modulo){
            case 1:
                return "Sun";
            case 2:
                return "Mon";
            case 3:
                return "Tue";
            case 4:
                return "Wed";
            case 5:
                return "Thu";
            case 6:
                return "Fri";
            case 0:
                return "Sat";
        }
        return "unknown";
    }
}
