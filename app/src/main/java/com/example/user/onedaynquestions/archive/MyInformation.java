package com.example.user.onedaynquestions.archive;

/**
 * Created by user on 2016-04-18.
 */
public class MyInformation {

    /*
    private static final String ATTR_MYINFO_ID = "myinfo_id";
    private static final String ATTR_MYINFO_NICK = "myinfo_nick";
    private static final String ATTR_MYINFO_NAME = "myinfo_name";
    private static final String ATTR_MYINFO_AGE = "myinfo_age";
    private static final String ATTR_MYINFO_SEX = "myinfo_sex";
    private static final String ATTR_MYINFO_HEIGHT = "myinfo_height";
    private static final String ATTR_MYINFO_WEIGHT = "myinfo_weight";
    private static final String ATTR_MYINFO_REGISTERED = "myinfo_registered";
    private static final String ATTR_MYINFO_DEVICEID = "myinfo_device_id";

    private static final String CREATE_TABLE_MYINFO =
            "CREATE TABLE " + TABLE_MYINFO + "(" +
                    ATTR_MYINFO_ID + " VARCHAR(30) PRIMARY KEY, " +
                    ATTR_MYINFO_NICK + " VARCHAR(30), " +
                    ATTR_MYINFO_NAME + " VARCHAR(30), " +
                    ATTR_MYINFO_AGE + " INTEGER, " +
                    ATTR_MYINFO_SEX + " INTEGER, " +
                    ATTR_MYINFO_HEIGHT + " REAL DEFAULT 160.0, " +
                    ATTR_MYINFO_WEIGHT + " REAL DEFAULT 50.0, " +
                    ATTR_MYINFO_REGISTERED + " INTEGER DEFAULT -1, " +
                    ATTR_MYINFO_DEVICEID + " VARCHAR(100)" +
                    ");";
                    */

    String userId;
    String userNick;
    String userName;
    int userAge;
    int userSex;
    int userHeight;
    int userWeight;
    int userRegistered;
    String userDeviceId;

    private void initUserInfo() {
        userId = "-1";
        userNick = "-1";
        userName = "-1";
        userAge = -1;
        userSex = -1;
        userHeight = -1;
        userWeight = -1;
        userRegistered = -1;
        userDeviceId = "-1";
    }

    public MyInformation() {
        initUserInfo();
    }

    public MyInformation(String userId, String userNick, String userName) {
        initUserInfo();

        this.userId = userId;
        this.userNick = userNick;
        this.userName = userName;
    }

    public MyInformation(String userId, String userNick, String userName, String userDeviceId) {
        initUserInfo();

        this.userId = userId;
        this.userNick = userNick;
        this.userName = userName;
        this.userDeviceId = userDeviceId;
    }

    public MyInformation(String userId, String userNick, String userName, int userAge, int userSex, int userHeight, int userWeight, int userRegistered, String userDeviceId) {
        initUserInfo();

        this.userId = userId;
        this.userNick = userNick;
        this.userName = userName;
        this.userAge = userAge;
        this.userSex = userSex;
        this.userHeight = userHeight;
        this.userWeight = userWeight;
        this.userRegistered = userRegistered;
        this.userDeviceId = userDeviceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    public int getUserSex() {
        return userSex;
    }

    public void setUserSex(int userSex) {
        this.userSex = userSex;
    }

    public int getUserHeight() {
        return userHeight;
    }

    public void setUserHeight(int userHeight) {
        this.userHeight = userHeight;
    }

    public int getUserWeight() {
        return userWeight;
    }

    public void setUserWeight(int userWeight) {
        this.userWeight = userWeight;
    }

    public int getUserRegistered() {
        return userRegistered;
    }

    public void setUserRegistered(int userRegistered) {
        this.userRegistered = userRegistered;
    }

    public String getUserDeviceId() {
        return userDeviceId;
    }

    public void setUserDeviceId(String userDeviceId) {
        this.userDeviceId = userDeviceId;
    }
}
