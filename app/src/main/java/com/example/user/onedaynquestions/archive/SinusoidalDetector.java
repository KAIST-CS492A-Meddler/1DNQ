package com.example.user.onedaynquestions.archive;

import android.os.CountDownTimer;

import com.jjoe64.graphview.series.DataPoint;

/**
 * Created by PCPC on 2016-06-08.
 */
public class SinusoidalDetector {
    private double period;
    private double amplitude;
    private double unit; // unit number of one cycle
    private double unitLength;
    private boolean isPeriodic;
    private final double maxAngle = 2*Math.PI;
    private final double radToDeg = 180/Math.PI;

    private double maxPosition, minPosition;
    private double previousCountPosition ;
    private boolean isPrevCheckMax, isPrevCheckMin;
    //is most currently check max peak?

    private final int periodCheckThreshold = 10; // 1s
    private final double phaseThreshold = 20;
    private final double amplitudeThreshold = 20; //1s
    private final double periodThresholdMin = 500; //1s
    private final double periodThresholdMax = 6000; //6s
    private final double periodThresholdMinHalf = periodThresholdMin/2; //1s
    private final double periodThresholdMaxHalf = periodThresholdMax/2; //6s
    private PeakDetector peak;

    private double count; //
    private boolean modeChangeable;
    private CountDownTimer periodTimer;
    private CountDownTimer counter;
    private int periodChecker;

    private double prevValue;

    public SinusoidalDetector(double waistMeasurement, double hoopLength){
        period = 0;
        isPeriodic = false;
        amplitude = 0;
        unit = hoopLength / waistMeasurement;

        count = 0;
        peak = new PeakDetector(2);
        peak.setDelta(15);
        peak.setDeltaDivider(5);
        maxPosition = 0;
        minPosition = 0;
        previousCountPosition = 0;
        isPrevCheckMax = false;
        unitLength = Double.MAX_VALUE;
        modeChangeable = false;
        periodChecker = 0;
        prevValue = 0;
        counter = new CountDownTimer(34900, 698)     {
            @Override
            public void onTick(long millisUntilFinished) {
                if(isPeriodic)
                    count++;
            }

            @Override
            public void onFinish() {
                counter.start();
                if(isPeriodic)
                    count++;

            }
        }.start();

    }

    public void reset(){
        period = 0;
        isPeriodic = false;
        amplitude = 0;
        peak.reset();
        maxPosition = 0;
        minPosition = 0;
        previousCountPosition = 0;
        isPrevCheckMax = false;
        isPrevCheckMin = false;
        unitLength = Double.MAX_VALUE;
        modeChangeable = false;
        prevValue = 0;
    }
    public void reset(double waistMeasurement, double hoopLength){
        period = 0;
        isPeriodic = false;
        amplitude = 0;
        unit = hoopLength / waistMeasurement;

        peak.reset();
        maxPosition = 0;
        minPosition = 0;
        previousCountPosition = 0;
        isPrevCheckMax = false;
        isPrevCheckMin = false;
        unitLength = Double.MAX_VALUE;
        modeChangeable = false;
        prevValue = 0;

    }

    public boolean periodMonitor(DataPoint point){




        if(point.getY() > phaseThreshold ){
            peak.setDetectingMode(1);
        }else if(point.getY() < -phaseThreshold){
            peak.setDetectingMode(-1);
        }else{
            peak.setDetectingMode(0);
        }

        if(Math.abs(point.getY() - prevValue) < 3 && peak.getDetectingMode() == 0){
            periodChecker++;
            if(periodChecker > periodCheckThreshold){
                periodChecker = 0;
                halfPeriodUpdate(0);
            }
        }else{
            periodChecker = 0;
        }
        prevValue = point.getY();

        if(isPeriodic){
            double temp = point.getX() - previousCountPosition;
            if(previousCountPosition == 0){
                //count = count + unit;
                previousCountPosition = point.getX();
            }
            else{
                //count += (temp) / unitLength;
                previousCountPosition = point.getX();
            }
        }

        switch (peak.peakDetection(point)) {
            case 1:
                if (peak.getMaxValue() > amplitudeThreshold) {
                    maxPosition = peak.getMaxPosition();
                    if (isPrevCheckMin) {
                        halfPeriodUpdate(maxPosition - minPosition);
                    }
                    previousCountPosition = point.getX();
                    isPrevCheckMax = true;
                    isPrevCheckMin = false;
                }
                break;
            case -1:
                if (peak.getMinValue() < -amplitudeThreshold) {
                    minPosition = peak.getMinPosition();
                    if (isPrevCheckMax) {
                        halfPeriodUpdate(minPosition - maxPosition);
                    }
                    previousCountPosition = point.getX();
                    isPrevCheckMin = true;
                    isPrevCheckMax = false;
                }
                break;
        }

        return isPeriodic;
    }


    public boolean halfPeriodUpdate(double peri){
        if(peri > periodThresholdMinHalf && peri < periodThresholdMaxHalf) {
            if (period == 0) {
                previousCountPosition = 0;
            }
            period = peri*2;
            unitLength = period / unit;
            isPeriodic = true;
            return true;
        }else{
            isPeriodic = false;
            period = 0;
            unitLength = Double.MAX_VALUE;
            //previousCountPosition = 0;
            //count = 0;
            return false;
        }
    }



    public double getPhase(double value){
        if(isPeriodic){
            if(amplitude < value) amplitude = value;
            else if(-amplitude > value) amplitude = -value;
            return Math.asin(value/amplitude);
        }
        return 0;
    }

    public int getCount(){
        return (int)count;
    }



    public double getRadToDeg(double rad){
        return rad * radToDeg;
    }
    public double getDegToRad(double deg){
        return deg / radToDeg;
    }

}
