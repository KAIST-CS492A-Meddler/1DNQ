package com.example.user.onedaynquestions.model;

/**
 * Created by user on 2016-05-25.
 */
public class MyHereAgent {

    /* myhereagents table */
    /*
    private static final String ATTR_MYEQ_MACID = "myeq_mac_id";
    private static final String ATTR_MYEQ_NAME = "myeq_name";
    private static final String ATTR_MYEQ_TYPE = "myeq_type";
    private static final String ATTR_MYEQ_BEACONMAJORID = "myeq_beacon_major_id";
    private static final String ATTR_MYEQ_BEACONMINORID = "myeq_beacon_minor_id";

    private static final String CREATE_TABLE_MYHEREAGENTS =
            "CREATE TABLE " + TABLE_MYHEREAGENTS + "(" +
                    ATTR_MYEQ_MACID + " VARCHAR(30) PRIMARY KEY, " +
                    ATTR_MYEQ_NAME + " VARCHAR(30) NOT NULL, " +
                    ATTR_MYEQ_TYPE + " INTEGER NOT NULL, " +
                    ATTR_MYEQ_BEACONMAJORID + " VARCHAR(50), " +
                    ATTR_MYEQ_BEACONMINORID + " VARCHAR(50)" +
                    ");";
                    */

    //Equipment type
    //0: NO NAME, 1: Dumbbells, 2: Pushup bars, 3: Jumprope, 4: Hoola-hoop, 5: Others
    public final static int TYPE_DUMBEL = 1;
    public final static int TYPE_PUSH_UP = 2;
    public final static int TYPE_JUMP_ROPE = 3;
    public final static int TYPE_HOOLA_HOOP = 4;
    public final static int TYPE_OTHERS = 5;

    String myeqMacId;
    String myeqName;
    int myeqType;
    String myeqBeaconMajorId;
    String myeqBeaconMinorId;

    private void initHereAgent() {
        myeqMacId = "-1";
        myeqName = "-1";
        myeqType = -1;
        myeqBeaconMajorId = "-1";
        myeqBeaconMinorId = "-1";
    }

    public MyHereAgent() {
        initHereAgent();
    }

    public MyHereAgent(String myeqMacId, String myeqName) {
        initHereAgent();

        this.myeqMacId = myeqMacId;
        this.myeqName = myeqName;
    }

    public MyHereAgent(String myeqMacId, String myeqName, String myeqBeaconMajorId, String myeqBeaconMinorId) {
        initHereAgent();

        this.myeqMacId = myeqMacId;
        this.myeqName = myeqName;
        this.myeqBeaconMajorId = myeqBeaconMajorId;
        this.myeqBeaconMinorId = myeqBeaconMinorId;
    }

    public MyHereAgent(String myeqMacId, String myeqName, int myeqType, String myeqBeaconMajorId, String myeqBeaconMinorId) {
        initHereAgent();

        this.myeqMacId = myeqMacId;
        this.myeqName = myeqName;
        this.myeqType = myeqType;
        this.myeqBeaconMajorId = myeqBeaconMajorId;
        this.myeqBeaconMinorId = myeqBeaconMinorId;
    }

    public String getMyeqMacId() {
        return myeqMacId;
    }

    public void setMyeqMacId(String myeqMacId) {
        this.myeqMacId = myeqMacId;
    }

    public String getMyeqName() {
        return myeqName;
    }

    public void setMyeqName(String myeqName) {
        this.myeqName = myeqName;
    }

    public int getMyeqType() {
        return myeqType;
    }

    public void setMyeqType(int myeqType) {
        this.myeqType = myeqType;
    }

    public String getMyeqBeaconMajorId() {
        return myeqBeaconMajorId;
    }

    public void setMyeqBeaconMajorId(String myeqBeaconMajorId) {
        this.myeqBeaconMajorId = myeqBeaconMajorId;
    }

    public String getMyeqBeaconMinorId() {
        return myeqBeaconMinorId;
    }

    public void setMyeqBeaconMinorId(String myeqBeaconMinorId) {
        this.myeqBeaconMinorId = myeqBeaconMinorId;
    }
}
