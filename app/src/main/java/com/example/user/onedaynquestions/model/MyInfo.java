package com.example.user.onedaynquestions.model;

/**
 * Created by user on 2016-12-04.
 */

public class MyInfo {


    String myInfoId;
    String myInfoNick;
    String myInfoName;
    int myInfoAge;
    int myInfoGender;
    String myInfoDeviceId;
    int myInfoExp;
    float myInfoQuality;
    int myInfoCardNum;
    int myInfoLoginNum;
    int myInfoAnswerRight;
    int myInfoAnswerWrong;

    private void initMyInfo() {
        myInfoId = "-1";
        myInfoNick = "-1";
        myInfoName = "-1";
        myInfoAge = 20;
        myInfoGender = 1;
        myInfoDeviceId = "-1";
        myInfoExp = 0;
        myInfoQuality = 0;
        myInfoCardNum = 0;
        myInfoLoginNum = 0;
        myInfoAnswerRight = 0;
        myInfoAnswerWrong = 0;
    }


    /* Constructors */

    public MyInfo() {
        initMyInfo();
    }

    public MyInfo(String myInfoId, String myInfoNick, String myInfoName) {
        initMyInfo();

        this.myInfoId = myInfoId;
        this.myInfoNick = myInfoNick;
        this.myInfoName = myInfoName;
    }

    public MyInfo(String myInfoDeviceId, String myInfoId, String myInfoNick, String myInfoName, int myInfoAge, int myInfoGender) {
        initMyInfo();

        this.myInfoDeviceId = myInfoDeviceId;
        this.myInfoId = myInfoId;
        this.myInfoNick = myInfoNick;
        this.myInfoName = myInfoName;
        this.myInfoAge = myInfoAge;
        this.myInfoGender = myInfoGender;
    }

    public MyInfo(String myInfoId, String myInfoNick, String myInfoName, int myInfoAge, int myInfoGender, String myInfoDeviceId, int myInfoExp, float myInfoQuality, int myInfoCardNum, int myInfoLoginNum, int myInfoAnswerRight, int myInfoAnswerWrong) {
        initMyInfo();

        this.myInfoId = myInfoId;
        this.myInfoNick = myInfoNick;
        this.myInfoName = myInfoName;
        this.myInfoAge = myInfoAge;
        this.myInfoGender = myInfoGender;
        this.myInfoDeviceId = myInfoDeviceId;
        this.myInfoExp = myInfoExp;
        this.myInfoQuality = myInfoQuality;
        this.myInfoCardNum = myInfoCardNum;
        this.myInfoLoginNum = myInfoLoginNum;
        this.myInfoAnswerRight = myInfoAnswerRight;
        this.myInfoAnswerWrong = myInfoAnswerWrong;
    }
}
