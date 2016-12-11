package com.example.user.onedaynquestions.model;

/**
 * Created by selab-ymbaek on 12/11/2016.
 */

public class DailyRecord {

    int dailyRecordContribution;
    int dailyRecordStudyRight;
    int dailyRecordStudyWrong;

    private void initDailyRecord() {
        dailyRecordContribution = 0;
        dailyRecordStudyRight = 0;
        dailyRecordStudyWrong = 0;
    }

    public DailyRecord() {
        initDailyRecord();
    }

    public DailyRecord(int dailyRecordContribution) {
        initDailyRecord();
        this.dailyRecordContribution = dailyRecordContribution;
    }

    public DailyRecord(int dailyRecordContribution, int dailyRecordStudyRight, int dailyRecordStudyWrong) {
        initDailyRecord();
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
}
