package com.example.user.onedaynquestions.model;

/**
 * Created by selab-ymbaek on 12/13/2016.
 */

public class UserLog {

    String logTimestamp;
    int logDuration;
    String logCurActivity;
    String logCurEvent;

    private void initUserLog() {
        logTimestamp = "";
        logDuration = 0;
        logCurActivity = "";
        logCurEvent = "";
    }

    public UserLog() {
        initUserLog();
    }

    public UserLog(String logCurActivity, String logCurEvent, String logTimestamp) {
        initUserLog();
        this.logCurActivity = logCurActivity;
        this.logCurEvent = logCurEvent;
        this.logTimestamp = logTimestamp;
    }

    public UserLog(String logTimestamp, int logDuration, String logCurActivity, String logCurEvent) {
        initUserLog();
        this.logTimestamp = logTimestamp;
        this.logDuration = logDuration;
        this.logCurActivity = logCurActivity;
        this.logCurEvent = logCurEvent;
    }

    public String getLogTimestamp() {
        return logTimestamp;
    }

    public void setLogTimestamp(String logTimestamp) {
        this.logTimestamp = logTimestamp;
    }

    public int getLogDuration() {
        return logDuration;
    }

    public void setLogDuration(int logDuration) {
        this.logDuration = logDuration;
    }

    public String getLogCurActivity() {
        return logCurActivity;
    }

    public void setLogCurActivity(String logCurActivity) {
        this.logCurActivity = logCurActivity;
    }

    public String getLogCurEvent() {
        return logCurEvent;
    }

    public void setLogCurEvent(String logCurEvent) {
        this.logCurEvent = logCurEvent;
    }
}


