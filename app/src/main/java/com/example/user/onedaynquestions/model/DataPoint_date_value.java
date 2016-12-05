package com.example.user.onedaynquestions.model;

import com.jjoe64.graphview.series.DataPointInterface;

import java.io.Serializable;

import static android.R.attr.x;
import static android.R.attr.y;

/**
 * Created by PCPC on 2016-12-06.
 */

public class DataPoint_date_value implements DataPointInterface, Serializable{

    @Override
    public double getX() {
        return x;
    }
    @Override
    public double getY() {
        return y;
    }
    @Override
    public String toString() {
        return "";
    }

}
