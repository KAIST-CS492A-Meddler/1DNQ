package com.example.user.onedaynquestions.utility;

import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;

/**
 * Created by PCPC on 2016-05-24.
 */
public class PeakDetector extends Thread{
    private int equipmentType;
    private ArrayList<DataPoint> data;

    private boolean isDetectingMax, isDetectingMin;

    private double maxValue, minValue;
    private double prevMax, prevMin;
    private double maxPosition, minPosition;
    private double delta; // minimum difference for the peaks
    private double minDelta = 30;
    private double deltaDivider = 2;

    private boolean isEnable;

    private final int listCapacity = 1000;


    public PeakDetector(int type){
        equipmentType = type;
        data = new ArrayList<DataPoint>();
        minValue = Double.MAX_VALUE;
        maxValue = Double.MIN_VALUE;
        prevMin = Double.MAX_VALUE;
        prevMax = Double.MIN_VALUE;

        maxPosition = -1;
        minPosition = -1;

        isDetectingMax = true;
        isDetectingMin = true;

        delta = minDelta;
    }

    public void setDelta(double d){
        minDelta = d;
    }
    public void setDeltaDivider(double d){
        deltaDivider = d;
    }
    public void reset(){
        data = new ArrayList<DataPoint>();
        minValue = Double.MAX_VALUE;
        maxValue = Double.MIN_VALUE;
        prevMin = Double.MAX_VALUE;
        prevMax = Double.MIN_VALUE;
        maxPosition = -1;
        minPosition = -1;
        isDetectingMax = true;
        isDetectingMin = true;
    }

    //reference: http://www.billauer.co.il/peakdet.html
    /*
    @arguments
    @return: no peak (0) min peak (-1) max peak(1)
     */
    public int peakDetection(DataPoint point){
        double added = point.getY();
        if(isDetectingMax){
            if(maxValue < added) {
                maxValue = added;
                maxPosition = point.getX();
                double temp = Math.abs(maxValue /deltaDivider);
                if(temp > minDelta) delta = temp;
            }
            else if( added < maxValue - delta){
                isDetectingMax = false;
                prevMax = maxValue;
                delta = minDelta;
                maxValue = Double.MIN_VALUE;
                return 1;
            }

        }

        if(isDetectingMin){
            if(minValue > added){
                minValue = added;
                minPosition = point.getX();
                double temp = Math.abs(minValue /deltaDivider);
                if(temp > minDelta) delta = temp;
            }
            else if(added > minValue + delta){
                isDetectingMin = false;
                prevMin = minValue;
                delta = minDelta;
                minValue = Double.MAX_VALUE;
                return -1;
            }
        }
        return 0;
    }


    /*
    @arguments
    @return: min: -1, max: 1, both = 0
     */
    public void setDetectingMode(int mode){
        switch (mode){
            case -1:
                isDetectingMin = true;
                isDetectingMax = false;
                break;
            case 1:
                isDetectingMin = false;
                isDetectingMax = true;
                break;
            case 0:
                isDetectingMin = false;
                isDetectingMax = false;
                break;
            case 2:
                isDetectingMin = true;
                isDetectingMax = true;
                break;
        }
    }
    public double getDetectingMode(){
        if(isDetectingMax){
            if(!isDetectingMin){
                return 1;
            }else return 2;
        }else{
            if(isDetectingMin) return -1;
            else return 0;
        }
    }
    public double getMaxValue(){
        return prevMax;
    }

    public double getMinValue(){
        return prevMin;
    }

    public double getMaxPosition(){
        return maxPosition;
    }
    public double getMinPosition(){
        return minPosition;
    }
    public void cutFromTo(int from, int to){
        data = (ArrayList<DataPoint>)data.subList(from, to);
    }

}
