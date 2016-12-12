package com.example.user.onedaynquestions.model;

/**
 * Created by selab-ymbaek on 12/11/2016.
 */

public class UnitRecord {

    String dailyRecordDateTime;

    int previousDayExp;

    int dailyRecordContribution;
    int dailyRecordStudyRight;
    int dailyRecordStudyWrong;

    private void initDailyRecord() {

        dailyRecordDateTime = "2016-01-01 00:00:00";

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
        this.dailyRecordContribution = dailyRecordContribution;
    }

    public UnitRecord(int dailyRecordContribution, int dailyRecordStudyRight, int dailyRecordStudyWrong) {
        initDailyRecord();
        this.dailyRecordContribution = dailyRecordContribution;
        this.dailyRecordStudyRight = dailyRecordStudyRight;
        this.dailyRecordStudyWrong = dailyRecordStudyWrong;
    }

    public UnitRecord(String dailyRecordDateTime, int dailyRecordContribution, int dailyRecordStudyRight, int dailyRecordStudyWrong) {
        this.dailyRecordDateTime = dailyRecordDateTime;
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

    public String getDailyRecordDateTime() {
        return dailyRecordDateTime;
    }

    public void setDailyRecordDateTime(String dailyRecordDateTime) {
        this.dailyRecordDateTime = dailyRecordDateTime;
    }
}
