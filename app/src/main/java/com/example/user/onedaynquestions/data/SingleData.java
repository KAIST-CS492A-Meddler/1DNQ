package com.example.user.onedaynquestions.data;

/**
 * Created by PCPC on 2016-05-25.
 */
public class SingleData {

    public static long appStartTime = 0;

    private char dataType;
    private float rawData;
    private long createTime; //millisecond


    public SingleData(char type, float raw){
        createTime = System.currentTimeMillis() - appStartTime;
        dataType = type;
        rawData = raw;
    }

}
