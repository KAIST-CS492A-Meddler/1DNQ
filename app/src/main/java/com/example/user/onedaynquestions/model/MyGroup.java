package com.example.user.onedaynquestions.model;

/**
 * Created by user on 2016-12-04.
 */

public class MyGroup {
    String myGroupId;
    String myGroupName;
    String myGroupRegisDate;
    int myGroupRanking;

    private void initMyGroup() {
        myGroupId = "-1";
        myGroupName = "-1";
        myGroupRegisDate = "-1";
        myGroupRanking = -1;
    }

    public MyGroup() {
        initMyGroup();
    }

    public MyGroup(String myGroupId, String myGroupName, String myGroupRegisDate) {
        initMyGroup();

        this.myGroupId = myGroupId;
        this.myGroupName = myGroupName;
        this.myGroupRegisDate = myGroupRegisDate;
    }

    public MyGroup(String myGroupId, String myGroupName, String myGroupRegisDate, int myGroupRanking) {
        initMyGroup();

        this.myGroupId = myGroupId;
        this.myGroupName = myGroupName;
        this.myGroupRegisDate = myGroupRegisDate;
        this.myGroupRanking = myGroupRanking;
    }

    public String getMyGroupId() {
        return myGroupId;
    }

    public void setMyGroupId(String myGroupId) {
        this.myGroupId = myGroupId;
    }

    public String getMyGroupName() {
        return myGroupName;
    }

    public void setMyGroupName(String myGroupName) {
        this.myGroupName = myGroupName;
    }

    public String getMyGroupRegisDate() {
        return myGroupRegisDate;
    }

    public void setMyGroupRegisDate(String myGroupRegisDate) {
        this.myGroupRegisDate = myGroupRegisDate;
    }

    public int getMyGroupRanking() {
        return myGroupRanking;
    }

    public void setMyGroupRanking(int myGroupRanking) {
        this.myGroupRanking = myGroupRanking;
    }
}
