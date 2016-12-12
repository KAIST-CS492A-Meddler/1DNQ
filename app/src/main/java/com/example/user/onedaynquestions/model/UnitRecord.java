package com.example.user.onedaynquestions.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by selab-ymbaek on 12/11/2016.
 */

public class UnitRecord {

    //"2016-01-01 00:00:00";
    Date dailyRecordDateTime;

    int previousDayExp;

    int dailyRecordContribution;
    int dailyRecordStudyRight;
    int dailyRecordStudyWrong;
    DateFormat sdFormat;

    private void initDailyRecord() {

        dailyRecordDateTime = Calendar.getInstance().getTime();

        previousDayExp = 0;

        dailyRecordContribution = 0;
        dailyRecordStudyRight = 0;
        dailyRecordStudyWrong = 0;

    }

    public UnitRecord() {
        initDailyRecord();
    }

    public UnitRecord(int dailyRecordContribution) {
        initDailyRecord();
        sdFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        this.dailyRecordContribution = dailyRecordContribution;
    }

    public UnitRecord(int dailyRecordContribution, int dailyRecordStudyRight, int dailyRecordStudyWrong) {
        initDailyRecord();
        this.dailyRecordContribution = dailyRecordContribution;
        this.dailyRecordStudyRight = dailyRecordStudyRight;
        this.dailyRecordStudyWrong = dailyRecordStudyWrong;
    }

    public UnitRecord(Date dailyRecordDateTime, int dailyRecordContribution, int dailyRecordStudyRight, int dailyRecordStudyWrong) {
        this.dailyRecordDateTime = dailyRecordDateTime;
        this.dailyRecordContribution = dailyRecordContribution;
        this.dailyRecordStudyRight = dailyRecordStudyRight;
        this.dailyRecordStudyWrong = dailyRecordStudyWrong;
    }


    public UnitRecord(String dailyRecordDateTime, int dailyRecordContribution, int dailyRecordStudyRight, int dailyRecordStudyWrong) {

        sdFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date tempDate = Calendar.getInstance().getTime();
        try {
            tempDate = sdFormat.parse(dailyRecordDateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.dailyRecordDateTime = tempDate;
        this.dailyRecordContribution = dailyRecordContribution;
        this.dailyRecordStudyRight = dailyRecordStudyRight;
        this.dailyRecordStudyWrong = dailyRecordStudyWrong;
    }

    public int getDailyRecordContribution() {
        return dailyRecordContribution;
    }

    public void setDailyRecordContribution(int dailyRecordContribution) {
        this.dailyRecordContribution = dailyRecordContribution;
    }

    public int getDailyRecordStudyRight() {
        return dailyRecordStudyRight;
    }

    public void setDailyRecordStudyRight(int dailyRecordStudyRight) {
        this.dailyRecordStudyRight = dailyRecordStudyRight;
    }

    public int getDailyRecordStudyWrong() {
        return dailyRecordStudyWrong;
    }

    public void setDailyRecordStudyWrong(int dailyRecordStudyWrong) {
        this.dailyRecordStudyWrong = dailyRecordStudyWrong;
    }

    public Date getDailyRecordDateTime_Date() {
        return dailyRecordDateTime;
    }
    public String getDailyRecordDateTime() {

        return sdFormat.format(dailyRecordDateTime);
    }



    public void setDailyRecordDateTime(String dailyRecordDateTime) {

        sdFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date tempDate = Calendar.getInstance().getTime();
        try {
            tempDate = sdFormat.parse(dailyRecordDateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.dailyRecordDateTime = tempDate;
    }

    public void setDailyRecordDateTime(Date dailyRecordDateTime) {
        this.dailyRecordDateTime = dailyRecordDateTime;
    }
}
