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
    String myInfoToken;
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

    public MyInfo(String myInfoId, String myInfoNick, String myInfoName, int myInfoAge, int myInfoGender, String myInfoDeviceId, String myInfoToken, int myInfoExp, float myInfoQuality, int myInfoCardNum, int myInfoLoginNum, int myInfoAnswerRight, int myInfoAnswerWrong) {
        initMyInfo();

        this.myInfoId = myInfoId;
        this.myInfoNick = myInfoNick;
        this.myInfoName = myInfoName;
        this.myInfoAge = myInfoAge;
        this.myInfoGender = myInfoGender;
        this.myInfoDeviceId = myInfoDeviceId;
        this.myInfoToken = myInfoToken;
        this.myInfoExp = myInfoExp;
        this.myInfoQuality = myInfoQuality;
        this.myInfoCardNum = myInfoCardNum;
        this.myInfoLoginNum = myInfoLoginNum;
        this.myInfoAnswerRight = myInfoAnswerRight;
        this.myInfoAnswerWrong = myInfoAnswerWrong;
    }

    public String getMyInfoId() {
        return myInfoId;
    }

    public void setMyInfoId(String myInfoId) {
        this.myInfoId = myInfoId;
    }

    public String getMyInfoNick() {
        return myInfoNick;
    }

    public void setMyInfoNick(String myInfoNick) {
        this.myInfoNick = myInfoNick;
    }

    public String getMyInfoName() {
        return myInfoName;
    }

    public void setMyInfoName(String myInfoName) {
        this.myInfoName = myInfoName;
    }

    public int getMyInfoAge() {
        return myInfoAge;
    }

    public void setMyInfoAge(int myInfoAge) {
        this.myInfoAge = myInfoAge;
    }

    public int getMyInfoGender() {
        return myInfoGender;
    }

    public void setMyInfoGender(int myInfoGender) {
        this.myInfoGender = myInfoGender;
    }

    public String getMyInfoDeviceId() {
        return myInfoDeviceId;
    }

    public void setMyInfoDeviceId(String myInfoDeviceId) {
        this.myInfoDeviceId = myInfoDeviceId;
    }

    public int getMyInfoExp() {
        return myInfoExp;
    }

    public void setMyInfoExp(int myInfoExp) {
        this.myInfoExp = myInfoExp;
    }

    public float getMyInfoQuality() {
        return myInfoQuality;
    }

    public void setMyInfoQuality(float myInfoQuality) {
        this.myInfoQuality = myInfoQuality;
    }

    public int getMyInfoCardNum() {
        return myInfoCardNum;
    }

    public void setMyInfoCardNum(int myInfoCardNum) {
        this.myInfoCardNum = myInfoCardNum;
    }

    public int getMyInfoLoginNum() {
        return myInfoLoginNum;
    }

    public void setMyInfoLoginNum(int myInfoLoginNum) {
        this.myInfoLoginNum = myInfoLoginNum;
    }

    public int getMyInfoAnswerRight() {
        return myInfoAnswerRight;
    }

    public void setMyInfoAnswerRight(int myInfoAnswerRight) {
        this.myInfoAnswerRight = myInfoAnswerRight;
    }

    public int getMyInfoAnswerWrong() {
        return myInfoAnswerWrong;
    }

    public void setMyInfoAnswerWrong(int myInfoAnswerWrong) {
        this.myInfoAnswerWrong = myInfoAnswerWrong;
    }

    public String getMyInfoToken() {
        return myInfoToken;
    }

    public void setMyInfoToken(String myInfoToken) {
        this.myInfoToken = myInfoToken;
    }
}
