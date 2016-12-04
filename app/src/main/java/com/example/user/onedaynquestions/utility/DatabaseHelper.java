package com.example.user.onedaynquestions.utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.user.onedaynquestions.archive.MyHereAgent;
import com.example.user.onedaynquestions.archive.MyInformation;
import com.example.user.onedaynquestions.archive.MyRecord;
import com.example.user.onedaynquestions.archive.MyRoutine;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by user on 2016-05-24.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    SQLiteDatabase mDB;

    private static final String TAG_DB = "DatabaseHelperDBTag";

    private static final String DATABASE_NAME = "hereAppDatabase.db";
    private static final int DATABASE_VERSION = 1;

    /* Database tables in hereAppDatabase.db */
    private static final String TABLE_MYINFO = "myinfo";
    private static final String TABLE_MYHEREAGENTS = "myhereagents";
    private static final String TABLE_MYROUTINES = "myroutines";
    private static final String TABLE_MYRECORDS = "myrecords";

    /* myinfo table */
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
                    ATTR_MYINFO_HEIGHT + " INTEGER DEFAULT 160, " +
                    ATTR_MYINFO_WEIGHT + " INTEGER DEFAULT 50, " +
                    ATTR_MYINFO_REGISTERED + " INTEGER DEFAULT -1, " +
                    ATTR_MYINFO_DEVICEID + " VARCHAR(100)" +
                    ");";

    /* myhereagents table */
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

    /* myroutines table */
    private static final String ATTR_MYROUTINE_ID = "myroutine_id";
    private static final String ATTR_MYROUTINE_NAME = "myroutine_name";
    private static final String ATTR_MYROUTINE_EQ1_ID = "myroutine_eq1_id";
    private static final String ATTR_MYROUTINE_EQ1_GOAL = "myroutine_eq1_goal";
    private static final String ATTR_MYROUTINE_EQ2_ID = "myroutine_eq2_id";
    private static final String ATTR_MYROUTINE_EQ2_GOAL = "myroutine_eq2_goal";
    private static final String ATTR_MYROUTINE_EQ3_ID = "myroutine_eq3_id";
    private static final String ATTR_MYROUTINE_EQ3_GOAL = "myroutine_eq3_goal";
    private static final String ATTR_MYROUTINE_EQ4_ID = "myroutine_eq4_id";
    private static final String ATTR_MYROUTINE_EQ4_GOAL = "myroutine_eq4_goal";
    private static final String ATTR_MYROUTINE_EQ5_ID = "myroutine_eq5_id";
    private static final String ATTR_MYROUTINE_EQ5_GOAL = "myroutine_eq5_goal";

    private static final String CREATE_TABLE_MYROUTINES =
            "CREATE TABLE " + TABLE_MYROUTINES + "(" +
                    ATTR_MYROUTINE_ID + " VARCHAR(30) PRIMARY KEY, " +
                    ATTR_MYROUTINE_NAME + " VARCHAR(50), " +
                    ATTR_MYROUTINE_EQ1_ID + " VARCHAR(30), " +
                    ATTR_MYROUTINE_EQ1_GOAL + " VARCHAR(30), " +
                    ATTR_MYROUTINE_EQ2_ID + " VARCHAR(30), " +
                    ATTR_MYROUTINE_EQ2_GOAL + " VARCHAR(30), " +
                    ATTR_MYROUTINE_EQ3_ID + " VARCHAR(30), " +
                    ATTR_MYROUTINE_EQ3_GOAL + " VARCHAR(30), " +
                    ATTR_MYROUTINE_EQ4_ID + " VARCHAR(30), " +
                    ATTR_MYROUTINE_EQ4_GOAL + " VARCHAR(30), " +
                    ATTR_MYROUTINE_EQ5_ID + " VARCHAR(30), " +
                    ATTR_MYROUTINE_EQ5_GOAL + " VARCHAR(30), " +
                    "FOREIGN KEY(" + ATTR_MYROUTINE_EQ1_ID +
                        ") REFERENCES " + TABLE_MYHEREAGENTS + "(" + ATTR_MYEQ_MACID + "), " +
                    "FOREIGN KEY(" + ATTR_MYROUTINE_EQ2_ID +
                        ") REFERENCES " + TABLE_MYHEREAGENTS + "(" + ATTR_MYEQ_MACID + "), " +
                    "FOREIGN KEY(" + ATTR_MYROUTINE_EQ3_ID +
                        ") REFERENCES " + TABLE_MYHEREAGENTS + "(" + ATTR_MYEQ_MACID + "), " +
                    "FOREIGN KEY(" + ATTR_MYROUTINE_EQ4_ID +
                        ") REFERENCES " + TABLE_MYHEREAGENTS + "(" + ATTR_MYEQ_MACID + "), " +
                    "FOREIGN KEY(" + ATTR_MYROUTINE_EQ5_ID +
                        ") REFERENCES " + TABLE_MYHEREAGENTS + "(" + ATTR_MYEQ_MACID + ")" +
                    ");";

    /* myrecords table */
    private static final String ATTR_MYRECORD_ID = "myrecord_id";
    private static final String ATTR_MYRECORD_NAME = "myrecord_name";
    private static final String ATTR_MYRECORD_DATETIME = "myrecord_date";
    private static final String ATTR_MYRECORD_EQ1_ID = "myrecord_eq1_id";
    private static final String ATTR_MYRECORD_EQ1_DONE = "myrecord_eq1_done";
    private static final String ATTR_MYRECORD_EQ2_ID = "myrecord_eq2_id";
    private static final String ATTR_MYRECORD_EQ2_DONE = "myrecord_eq2_done";
    private static final String ATTR_MYRECORD_EQ3_ID = "myrecord_eq3_id";
    private static final String ATTR_MYRECORD_EQ3_DONE = "myrecord_eq3_done";
    private static final String ATTR_MYRECORD_EQ4_ID = "myrecord_eq4_id";
    private static final String ATTR_MYRECORD_EQ4_DONE = "myrecord_eq4_done";
    private static final String ATTR_MYRECORD_EQ5_ID = "myrecord_eq5_id";
    private static final String ATTR_MYRECORD_EQ5_DONE = "myrecord_eq5_done";

    private static final String CREATE_TABLE_MYRECORDS =
            "CREATE TABLE " + TABLE_MYRECORDS + "(" +
                    ATTR_MYRECORD_ID + " VARCHAR(30) PRIMARY KEY, " +
                    ATTR_MYRECORD_NAME + " VARCHAR(30), " +
                    ATTR_MYRECORD_DATETIME + " DATETIME, " +
                    ATTR_MYRECORD_EQ1_ID + " VARCHAR(30), " +
                    ATTR_MYRECORD_EQ1_DONE + " INTEGER, " +
                    ATTR_MYRECORD_EQ2_ID + " VARCHAR(30), " +
                    ATTR_MYRECORD_EQ2_DONE + " INTEGER, " +
                    ATTR_MYRECORD_EQ3_ID + " VARCHAR(30), " +
                    ATTR_MYRECORD_EQ3_DONE + " INTEGER, " +
                    ATTR_MYRECORD_EQ4_ID + " VARCHAR(30), " +
                    ATTR_MYRECORD_EQ4_DONE + " INTEGER, " +
                    ATTR_MYRECORD_EQ5_ID + " VARCHAR(30), " +
                    ATTR_MYRECORD_EQ5_DONE + " INTEGER, " +
                    "FOREIGN KEY(" + ATTR_MYRECORD_EQ1_ID +
                        ") REFERENCES " + TABLE_MYHEREAGENTS + "(" + ATTR_MYEQ_MACID + "), " +
                    "FOREIGN KEY(" + ATTR_MYRECORD_EQ2_ID +
                        ") REFERENCES " + TABLE_MYHEREAGENTS + "(" + ATTR_MYEQ_MACID + "), " +
                    "FOREIGN KEY(" + ATTR_MYRECORD_EQ3_ID +
                        ") REFERENCES " + TABLE_MYHEREAGENTS + "(" + ATTR_MYEQ_MACID + "), " +
                    "FOREIGN KEY(" + ATTR_MYRECORD_EQ4_ID +
                        ") REFERENCES " + TABLE_MYHEREAGENTS + "(" + ATTR_MYEQ_MACID + "), " +
                    "FOREIGN KEY(" + ATTR_MYRECORD_EQ5_ID +
                        ") REFERENCES " + TABLE_MYHEREAGENTS + "(" + ATTR_MYEQ_MACID + ")" +
                    ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG_DB, "[Database] DatabaseHelper(): Constructer is called.");

        mDB = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d(TAG_DB, "[Database] CREATE_TABLE_MYINFO: " + CREATE_TABLE_MYINFO);
        Log.d(TAG_DB, "[Database] CREATE_TABLE_MYHEREAGENTS: " + CREATE_TABLE_MYHEREAGENTS);
        Log.d(TAG_DB, "[Database] CREATE_TABLE_MYROUTINES: " + CREATE_TABLE_MYROUTINES);
        Log.d(TAG_DB, "[Database] CREATE_TABLE_MYRECORDS: " + CREATE_TABLE_MYRECORDS);


        db.execSQL(CREATE_TABLE_MYINFO);
        db.execSQL(CREATE_TABLE_MYHEREAGENTS);
        db.execSQL(CREATE_TABLE_MYROUTINES);
        db.execSQL(CREATE_TABLE_MYRECORDS);

        Log.d(TAG_DB, "[Database] DatabaseHelper-OnCreate(): Create table success.");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG_DB, "[Database] DatabaseHelper-OnUpgrade()");

        //On upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MYINFO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MYHEREAGENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MYROUTINES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MYRECORDS);

        //Create a new tables
        onCreate(db);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////

    /* "MYINFO" TABLE METHODS */

    /**
     * Insert MyInformation to [myinfo] table
     * @param myInformation an object of myinformation
     * @return
     */
    public long insertMyInformation(MyInformation myInformation) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ATTR_MYINFO_ID, myInformation.getUserId());
        values.put(ATTR_MYINFO_NICK, myInformation.getUserNick());
        values.put(ATTR_MYINFO_NAME, myInformation.getUserName());
        values.put(ATTR_MYINFO_AGE, myInformation.getUserAge());
        values.put(ATTR_MYINFO_SEX, myInformation.getUserSex());
        values.put(ATTR_MYINFO_HEIGHT, myInformation.getUserHeight());
        values.put(ATTR_MYINFO_WEIGHT, myInformation.getUserWeight());
        values.put(ATTR_MYINFO_REGISTERED, myInformation.getUserRegistered());
        values.put(ATTR_MYINFO_DEVICEID, myInformation.getUserDeviceId());

        String selectQueryToCount = "SELECT * FROM " + TABLE_MYINFO;
        Cursor c = db.rawQuery(selectQueryToCount, null);

        if (c.getCount() == 0) {
            // MyInformation can have at most one tuple
            long myinfo_id = db.insert(TABLE_MYINFO, null, values);
            return myinfo_id;
        } else {
            return -1;
        }

    }

    /**
     * Get MyInformation object from DB
     * @return Obtained MyInformation object that was stored in DB
     */
    public MyInformation getMyInformation() {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_MYINFO;

        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null && c.getCount() != 0) {
            c.moveToFirst();

            MyInformation tmpMyInformation = new MyInformation();

            tmpMyInformation.setUserId(c.getString(c.getColumnIndex(ATTR_MYINFO_ID)));
            tmpMyInformation.setUserNick(c.getString(c.getColumnIndex(ATTR_MYINFO_NICK)));
            tmpMyInformation.setUserName(c.getString(c.getColumnIndex(ATTR_MYINFO_NAME)));
            tmpMyInformation.setUserAge(c.getInt(c.getColumnIndex(ATTR_MYINFO_AGE)));
            tmpMyInformation.setUserSex(c.getInt(c.getColumnIndex(ATTR_MYINFO_SEX)));
            tmpMyInformation.setUserHeight(c.getInt(c.getColumnIndex(ATTR_MYINFO_HEIGHT)));
            tmpMyInformation.setUserWeight(c.getInt(c.getColumnIndex(ATTR_MYINFO_WEIGHT)));
            tmpMyInformation.setUserRegistered(c.getInt(c.getColumnIndex(ATTR_MYINFO_REGISTERED)));
            tmpMyInformation.setUserDeviceId(c.getString(c.getColumnIndex(ATTR_MYINFO_DEVICEID)));

            return tmpMyInformation;

        } else {
            return null;
        }


    }

    /**
     * Update user information
     * @param myInformation MyInformation object that has updated user information
     * @return update result
     */
    public int updateMyInformation(MyInformation myInformation) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ATTR_MYINFO_ID, myInformation.getUserId());
        values.put(ATTR_MYINFO_NICK, myInformation.getUserNick());
        values.put(ATTR_MYINFO_NAME, myInformation.getUserName());
        values.put(ATTR_MYINFO_AGE, myInformation.getUserAge());
        values.put(ATTR_MYINFO_SEX, myInformation.getUserSex());
        values.put(ATTR_MYINFO_HEIGHT, myInformation.getUserHeight());
        values.put(ATTR_MYINFO_WEIGHT, myInformation.getUserWeight());
        values.put(ATTR_MYINFO_REGISTERED, myInformation.getUserRegistered());
        values.put(ATTR_MYINFO_DEVICEID, myInformation.getUserDeviceId());

        int returnVal;
        returnVal = db.update(TABLE_MYINFO, values, ATTR_MYINFO_ID + " = ?",
                new String[] { String.valueOf(myInformation.getUserId()) });

        return returnVal;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////

    /* "MYHEREAGENTS" TABLE METHODS */


    /**
     * Insert MyHereAgent to [myhereagents] table
     * @param myHereAgent An object of MyHereAgent
     * @return insert result
     */
    public long insertHereAgent(MyHereAgent myHereAgent) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ATTR_MYEQ_MACID, myHereAgent.getMyeqMacId());
        values.put(ATTR_MYEQ_NAME, myHereAgent.getMyeqName());
        values.put(ATTR_MYEQ_TYPE, myHereAgent.getMyeqType());
        values.put(ATTR_MYEQ_BEACONMAJORID, myHereAgent.getMyeqBeaconMajorId());
        values.put(ATTR_MYEQ_BEACONMINORID, myHereAgent.getMyeqBeaconMinorId());

        long myeq_id = db.insert(TABLE_MYHEREAGENTS, null, values);
        return myeq_id;
    }


    /**
     * Get a list of all MyHereAgents from DB
     * @return MyHereAgent list
     */
    public List<MyHereAgent> getAllMyHereAgents() {

        //Obtained MyHereAgents from DB
        List<MyHereAgent> myHereAgents = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_MYHEREAGENTS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            if (c.getCount() != 0) {

                //Looping through all rows and adding to list
                if (c.moveToFirst()) {
                    do {
                        MyHereAgent tmpHereAgent = new MyHereAgent();

                        tmpHereAgent.setMyeqMacId(c.getString(c.getColumnIndex(ATTR_MYEQ_MACID)));
                        tmpHereAgent.setMyeqName(c.getString(c.getColumnIndex(ATTR_MYEQ_NAME)));
                        tmpHereAgent.setMyeqType(c.getInt(c.getColumnIndex(ATTR_MYEQ_TYPE)));
                        tmpHereAgent.setMyeqBeaconMajorId(c.getString(c.getColumnIndex(ATTR_MYEQ_BEACONMAJORID)));
                        tmpHereAgent.setMyeqBeaconMinorId(c.getString(c.getColumnIndex(ATTR_MYEQ_BEACONMINORID)));

                        myHereAgents.add(tmpHereAgent);
                    } while (c.moveToNext());
                }

                return myHereAgents;

            } else {
                return null;
            }

        } else {
            return null;
        }


    }


    /**
     * Get a single MyHereAgent from DB
     * @param myeqMacId A mac-id key for selection
     * @return An object of selected MyHereAgent
     */
    public MyHereAgent getMyHereAgent(String myeqMacId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_MYHEREAGENTS +
                " WHERE " + ATTR_MYEQ_MACID + " = '" + myeqMacId + "'";

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            if (c.getCount() != 0) {
                c.moveToFirst();

                MyHereAgent tmpHereAgent = new MyHereAgent();

                tmpHereAgent.setMyeqMacId(c.getString(c.getColumnIndex(ATTR_MYEQ_MACID)));
                tmpHereAgent.setMyeqName(c.getString(c.getColumnIndex(ATTR_MYEQ_NAME)));
                tmpHereAgent.setMyeqType(c.getInt(c.getColumnIndex(ATTR_MYEQ_TYPE)));
                tmpHereAgent.setMyeqBeaconMajorId(c.getString(c.getColumnIndex(ATTR_MYEQ_BEACONMAJORID)));
                tmpHereAgent.setMyeqBeaconMinorId(c.getString(c.getColumnIndex(ATTR_MYEQ_BEACONMINORID)));

                return tmpHereAgent;
            } else {
                return null;
            }

        } else {
            return null;
        }

    }

    /**
     * Get the device name using its mac_id
     * @param MacId Mac ID of stored arduino agents
     * @return      Device name (that was set by a user) of the corresponding agent
     */
    public String getAgentNameByMacId(String MacId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT " + ATTR_MYEQ_NAME + " FROM " + TABLE_MYHEREAGENTS +
                " WHERE " + ATTR_MYEQ_MACID + " = '" + MacId +"'";

        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null) {
            if (c.getCount() != 0) {
                c.moveToFirst();

                return c.getString(c.getColumnIndex(ATTR_MYEQ_NAME));
            } else {
                return null;
            }

        } else {
            return null;
        }
    }

    /**
     * Count the number of stored MyHereAgents in DB
     * @return the number of stored MyHereAgents in DB
     */
    public int getMyHereAgentCount() {
        SQLiteDatabase db = this.getReadableDatabase();

        String countQuery = "SELECT * FROM " + TABLE_MYHEREAGENTS;
        Cursor c = db.rawQuery(countQuery, null);

        if (c != null) {
            int count = c.getCount();
            c.close();

            return count;

        } else {
            return 0;
        }
    }

    /**
     * Remove a MyHereAgent from DB
     * @param myeqMacId A mac-id key for deletion
     */
    public void deleteMyHereAgent(String myeqMacId) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_MYHEREAGENTS, ATTR_MYEQ_MACID + " = ?",
                new String[] { String.valueOf(myeqMacId) });
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////

    /* "MYROUTINES" TABLE METHODS */

    /**
     * Insert MyRoutine to [myroutines] table
     * @param myRoutine An object of MyRoutine
     * @return insertion result
     */
    public long insertRoutine(MyRoutine myRoutine) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ATTR_MYROUTINE_ID, myRoutine.getRoutineId());
        values.put(ATTR_MYROUTINE_NAME, myRoutine.getRoutineName());
        values.put(ATTR_MYROUTINE_EQ1_ID, myRoutine.getRoutineEq1Id());
        values.put(ATTR_MYROUTINE_EQ1_GOAL, myRoutine.getRoutineEq1Goal());
        values.put(ATTR_MYROUTINE_EQ2_ID, myRoutine.getRoutineEq2Id());
        values.put(ATTR_MYROUTINE_EQ2_GOAL, myRoutine.getRoutineEq2Goal());
        values.put(ATTR_MYROUTINE_EQ3_ID, myRoutine.getRoutineEq3Id());
        values.put(ATTR_MYROUTINE_EQ3_GOAL, myRoutine.getRoutineEq3Goal());
        values.put(ATTR_MYROUTINE_EQ4_ID, myRoutine.getRoutineEq4Id());
        values.put(ATTR_MYROUTINE_EQ4_GOAL, myRoutine.getRoutineEq4Goal());
        values.put(ATTR_MYROUTINE_EQ5_ID, myRoutine.getRoutineEq5Id());
        values.put(ATTR_MYROUTINE_EQ5_GOAL, myRoutine.getRoutineEq5Goal());

        long myroutine_id = db.insert(TABLE_MYROUTINES, null, values);
        return myroutine_id;

    }

    /**
     * Get a list of all MyRoutines from DB
     * @return MyRoutine list
     */
    public List<MyRoutine> getAllMyRoutines() {
        List<MyRoutine> myRoutines = new ArrayList<>();

        //Obtained MyRoutines from DB
        String selectQuery = "SELECT * FROM " + TABLE_MYROUTINES;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            if (c.getCount() != 0) {
                //Looping through all rows and adding to list
                if (c.moveToFirst()) {
                    do {
                        MyRoutine tmpRoutine = new MyRoutine();

                        tmpRoutine.setRoutineId(c.getString(c.getColumnIndex(ATTR_MYROUTINE_ID)));
                        tmpRoutine.setRoutineName(c.getString(c.getColumnIndex(ATTR_MYROUTINE_NAME)));
                        tmpRoutine.setRoutineEq1Id(c.getString(c.getColumnIndex(ATTR_MYROUTINE_EQ1_ID)));
                        tmpRoutine.setRoutineEq1Goal(c.getString(c.getColumnIndex(ATTR_MYROUTINE_EQ1_GOAL)));
                        tmpRoutine.setRoutineEq2Id(c.getString(c.getColumnIndex(ATTR_MYROUTINE_EQ2_ID)));
                        tmpRoutine.setRoutineEq2Goal(c.getString(c.getColumnIndex(ATTR_MYROUTINE_EQ2_GOAL)));
                        tmpRoutine.setRoutineEq3Id(c.getString(c.getColumnIndex(ATTR_MYROUTINE_EQ3_ID)));
                        tmpRoutine.setRoutineEq3Goal(c.getString(c.getColumnIndex(ATTR_MYROUTINE_EQ3_GOAL)));
                        tmpRoutine.setRoutineEq4Id(c.getString(c.getColumnIndex(ATTR_MYROUTINE_EQ4_ID)));
                        tmpRoutine.setRoutineEq4Goal(c.getString(c.getColumnIndex(ATTR_MYROUTINE_EQ4_GOAL)));
                        tmpRoutine.setRoutineEq5Id(c.getString(c.getColumnIndex(ATTR_MYROUTINE_EQ5_ID)));
                        tmpRoutine.setRoutineEq5Goal(c.getString(c.getColumnIndex(ATTR_MYROUTINE_EQ5_GOAL)));

                        myRoutines.add(tmpRoutine);
                    } while (c.moveToNext());
                }

                return myRoutines;

            } else {
                return null;
            }

        } else {
            return null;
        }

    }


    /**
     * Get a single MyRoutine from DB
     * @param myroutineId An id key for selection
     * @return An object of selected MyRoutine
     */
    public MyRoutine getMyRoutine(String myroutineId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_MYROUTINES +
                " WHERE " + ATTR_MYROUTINE_ID + " = '" + myroutineId + "'";

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            if (c.getCount() != 0) {
                c.moveToFirst();

                MyRoutine tmpRoutine = new MyRoutine();

                tmpRoutine.setRoutineId(c.getString(c.getColumnIndex(ATTR_MYROUTINE_ID)));
                tmpRoutine.setRoutineName(c.getString(c.getColumnIndex(ATTR_MYROUTINE_NAME)));
                tmpRoutine.setRoutineEq1Id(c.getString(c.getColumnIndex(ATTR_MYROUTINE_EQ1_ID)));
                tmpRoutine.setRoutineEq1Goal(c.getString(c.getColumnIndex(ATTR_MYROUTINE_EQ1_GOAL)));
                tmpRoutine.setRoutineEq2Id(c.getString(c.getColumnIndex(ATTR_MYROUTINE_EQ2_ID)));
                tmpRoutine.setRoutineEq2Goal(c.getString(c.getColumnIndex(ATTR_MYROUTINE_EQ2_GOAL)));
                tmpRoutine.setRoutineEq3Id(c.getString(c.getColumnIndex(ATTR_MYROUTINE_EQ3_ID)));
                tmpRoutine.setRoutineEq3Goal(c.getString(c.getColumnIndex(ATTR_MYROUTINE_EQ3_GOAL)));
                tmpRoutine.setRoutineEq4Id(c.getString(c.getColumnIndex(ATTR_MYROUTINE_EQ4_ID)));
                tmpRoutine.setRoutineEq4Goal(c.getString(c.getColumnIndex(ATTR_MYROUTINE_EQ4_GOAL)));
                tmpRoutine.setRoutineEq5Id(c.getString(c.getColumnIndex(ATTR_MYROUTINE_EQ5_ID)));
                tmpRoutine.setRoutineEq5Goal(c.getString(c.getColumnIndex(ATTR_MYROUTINE_EQ5_GOAL)));

                return tmpRoutine;
            } else {
                return null;
            }
        } else {
            return null;
        }

    }


    /**
     * Count the number of stored MyRoutines in DB
     * @return the number of stored MyRoutines in DB
     */
    public int getMyRoutineCount() {
        //Obtained MyRoutines from DB
        String selectQuery = "SELECT * FROM " + TABLE_MYROUTINES;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            int count = c.getCount();
            c.close();
            return count;
        } else {
            return 0;
        }

    }


    /**
     * Update one of MyRoutine in DB
     * @param myRoutine Updated object of MyRoutine
     * @return Update result
     */
    public int updateMyRoutine(MyRoutine myRoutine) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ATTR_MYROUTINE_ID, myRoutine.getRoutineId());
        values.put(ATTR_MYROUTINE_NAME, myRoutine.getRoutineName());
        values.put(ATTR_MYROUTINE_EQ1_ID, myRoutine.getRoutineEq1Id());
        values.put(ATTR_MYROUTINE_EQ1_GOAL, myRoutine.getRoutineEq1Goal());
        values.put(ATTR_MYROUTINE_EQ2_ID, myRoutine.getRoutineEq2Id());
        values.put(ATTR_MYROUTINE_EQ2_GOAL, myRoutine.getRoutineEq2Goal());
        values.put(ATTR_MYROUTINE_EQ3_ID, myRoutine.getRoutineEq3Id());
        values.put(ATTR_MYROUTINE_EQ3_GOAL, myRoutine.getRoutineEq3Goal());
        values.put(ATTR_MYROUTINE_EQ4_ID, myRoutine.getRoutineEq4Id());
        values.put(ATTR_MYROUTINE_EQ4_GOAL, myRoutine.getRoutineEq4Goal());
        values.put(ATTR_MYROUTINE_EQ5_ID, myRoutine.getRoutineEq5Id());
        values.put(ATTR_MYROUTINE_EQ5_GOAL, myRoutine.getRoutineEq5Goal());

        int updateResult;
        updateResult = db.update(TABLE_MYROUTINES, values, ATTR_MYROUTINE_ID + " = ?",
                new String[] { String.valueOf(myRoutine.getRoutineId()) });

        return updateResult;
    }


    /**
     * Remove a MyRoutine from DB
     * @param myroutineId An id key for deletion
     */
    public void deleteMyRoutine(String myroutineId) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_MYROUTINES, ATTR_MYROUTINE_ID + " = ?",
                new String[] { String.valueOf(myroutineId) });
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////

    /* "MYRECORDS" TABLE METHODS */

    /**
     * Insert MyRecord to [myrecords] table
     * @param myRecord An object of MyRecord
     * @return insertion result
     */
    public long insertRecord(MyRecord myRecord) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ATTR_MYRECORD_ID, myRecord.getRecordId());
        values.put(ATTR_MYRECORD_NAME, myRecord.getRecordName());
        values.put(ATTR_MYRECORD_DATETIME, myRecord.getRecordDateTime());
        values.put(ATTR_MYRECORD_EQ1_ID, myRecord.getRecordEq1Id());
        values.put(ATTR_MYRECORD_EQ1_DONE, myRecord.getRecordEq1Done());
        values.put(ATTR_MYRECORD_EQ2_ID, myRecord.getRecordEq2Id());
        values.put(ATTR_MYRECORD_EQ2_DONE, myRecord.getRecordEq2Done());
        values.put(ATTR_MYRECORD_EQ3_ID, myRecord.getRecordEq3Id());
        values.put(ATTR_MYRECORD_EQ3_DONE, myRecord.getRecordEq3Done());
        values.put(ATTR_MYRECORD_EQ4_ID, myRecord.getRecordEq4Id());
        values.put(ATTR_MYRECORD_EQ4_DONE, myRecord.getRecordEq4Done());
        values.put(ATTR_MYRECORD_EQ5_ID, myRecord.getRecordEq5Id());
        values.put(ATTR_MYRECORD_EQ5_DONE, myRecord.getRecordEq5Done());

        long myrecord_id = db.insert(TABLE_MYRECORDS, null, values);
        return myrecord_id;
    }


    /**
     * Get a list of all MyRecords from DB
     * @return MyRecord list
     */
    public List<MyRecord> getAllMyRecords() {
        List<MyRecord> myRecords = new ArrayList<>();

        //Obtained myRecords from DB
        String selectQuery = "SELECT * FROM " + TABLE_MYRECORDS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            if (c.getCount() != 0) {

                if (c.moveToFirst()) {
                    do {
                        MyRecord tmpMyRecord = new MyRecord();

                        tmpMyRecord.setRecordId(c.getString(c.getColumnIndex(ATTR_MYRECORD_ID)));
                        tmpMyRecord.setRecordName(c.getString(c.getColumnIndex(ATTR_MYRECORD_NAME)));
                        tmpMyRecord.setRecordDateTime(c.getString(c.getColumnIndex(ATTR_MYRECORD_DATETIME)));
                        tmpMyRecord.setRecordEq1Id(c.getString(c.getColumnIndex(ATTR_MYRECORD_EQ1_ID)));
                        tmpMyRecord.setRecordEq1Done(c.getInt(c.getColumnIndex(ATTR_MYRECORD_EQ1_DONE)));
                        tmpMyRecord.setRecordEq2Id(c.getString(c.getColumnIndex(ATTR_MYRECORD_EQ2_ID)));
                        tmpMyRecord.setRecordEq2Done(c.getInt(c.getColumnIndex(ATTR_MYRECORD_EQ2_DONE)));
                        tmpMyRecord.setRecordEq3Id(c.getString(c.getColumnIndex(ATTR_MYRECORD_EQ3_ID)));
                        tmpMyRecord.setRecordEq3Done(c.getInt(c.getColumnIndex(ATTR_MYRECORD_EQ3_DONE)));
                        tmpMyRecord.setRecordEq4Id(c.getString(c.getColumnIndex(ATTR_MYRECORD_EQ4_ID)));
                        tmpMyRecord.setRecordEq4Done(c.getInt(c.getColumnIndex(ATTR_MYRECORD_EQ4_DONE)));
                        tmpMyRecord.setRecordEq5Id(c.getString(c.getColumnIndex(ATTR_MYRECORD_EQ5_ID)));
                        tmpMyRecord.setRecordEq5Done(c.getInt(c.getColumnIndex(ATTR_MYRECORD_EQ5_DONE)));

                        myRecords.add(tmpMyRecord);
                    } while (c.moveToNext());
                }

                return myRecords;
            } else {
                return null;
            }

        } else {
            return null;
        }


    }


    /**
     * Get a single MyRecord from DB
     * @param myrecordId An id key for selection
     * @return An object of selected MyRecord
     */
    public MyRecord getMyRecord(String myrecordId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_MYRECORDS +
                " WHERE " + ATTR_MYRECORD_ID + " = '" + myrecordId + "'";

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            if (c.getCount() != 0) {
                c.moveToFirst();

                MyRecord tmpMyRecord = new MyRecord();

                tmpMyRecord.setRecordId(c.getString(c.getColumnIndex(ATTR_MYRECORD_ID)));
                tmpMyRecord.setRecordName(c.getString(c.getColumnIndex(ATTR_MYRECORD_NAME)));
                tmpMyRecord.setRecordDateTime(c.getString(c.getColumnIndex(ATTR_MYRECORD_DATETIME)));
                tmpMyRecord.setRecordEq1Id(c.getString(c.getColumnIndex(ATTR_MYRECORD_EQ1_ID)));
                tmpMyRecord.setRecordEq1Done(c.getInt(c.getColumnIndex(ATTR_MYRECORD_EQ1_DONE)));
                tmpMyRecord.setRecordEq2Id(c.getString(c.getColumnIndex(ATTR_MYRECORD_EQ2_ID)));
                tmpMyRecord.setRecordEq2Done(c.getInt(c.getColumnIndex(ATTR_MYRECORD_EQ2_DONE)));
                tmpMyRecord.setRecordEq3Id(c.getString(c.getColumnIndex(ATTR_MYRECORD_EQ3_ID)));
                tmpMyRecord.setRecordEq3Done(c.getInt(c.getColumnIndex(ATTR_MYRECORD_EQ3_DONE)));
                tmpMyRecord.setRecordEq4Id(c.getString(c.getColumnIndex(ATTR_MYRECORD_EQ4_ID)));
                tmpMyRecord.setRecordEq4Done(c.getInt(c.getColumnIndex(ATTR_MYRECORD_EQ4_DONE)));
                tmpMyRecord.setRecordEq5Id(c.getString(c.getColumnIndex(ATTR_MYRECORD_EQ5_ID)));
                tmpMyRecord.setRecordEq5Done(c.getInt(c.getColumnIndex(ATTR_MYRECORD_EQ5_DONE)));

                return tmpMyRecord;
            } else {
                return null;
            }

        } else {
            return null;
        }

    }


    /**
     * Count the number of stored MyRecords in DB
     * @return the number of stored MyRecords in DB
     */
    public int getMyRecordCount() {
        //Obtained MyRoutines from DB
        String selectQuery = "SELECT * FROM " + TABLE_MYRECORDS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            int count = c.getCount();
            c.close();

            return count;
        } else {
            return 0;
        }

    }


    /**
     * Update one of MyRecord in DB
     * @param myRecord Updated object of MyRecord
     * @return Update result
     */
    public int updateMyRecord(MyRecord myRecord) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ATTR_MYRECORD_ID, myRecord.getRecordId());
        values.put(ATTR_MYRECORD_NAME, myRecord.getRecordName());
        values.put(ATTR_MYRECORD_DATETIME, myRecord.getRecordDateTime());
        values.put(ATTR_MYRECORD_EQ1_ID, myRecord.getRecordEq1Id());
        values.put(ATTR_MYRECORD_EQ1_DONE, myRecord.getRecordEq1Done());
        values.put(ATTR_MYRECORD_EQ2_ID, myRecord.getRecordEq2Id());
        values.put(ATTR_MYRECORD_EQ2_DONE, myRecord.getRecordEq2Done());
        values.put(ATTR_MYRECORD_EQ3_ID, myRecord.getRecordEq3Id());
        values.put(ATTR_MYRECORD_EQ3_DONE, myRecord.getRecordEq3Done());
        values.put(ATTR_MYRECORD_EQ4_ID, myRecord.getRecordEq4Id());
        values.put(ATTR_MYRECORD_EQ4_DONE, myRecord.getRecordEq4Done());
        values.put(ATTR_MYRECORD_EQ5_ID, myRecord.getRecordEq5Id());
        values.put(ATTR_MYRECORD_EQ5_DONE, myRecord.getRecordEq5Done());

        int updateResult;
        updateResult = db.update(TABLE_MYRECORDS, values, ATTR_MYRECORD_ID + " = ?",
                new String[] { String.valueOf(myRecord.getRecordId()) });

        return updateResult;
    }


    /**
     * Remove a MyRecord from DB
     * @param myrecordId An id key for deletion
     */
    public void deleteMyRecord(String myrecordId) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_MYRECORDS, ATTR_MYRECORD_ID + " = ?",
                new String[] { String.valueOf(myrecordId) });
    }


    /**
     * Drop the table [myinfo]
     */
    public void dropTableMyInfo() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MYINFO);
        Log.d(TAG_DB, "[Database] dropTableMyInfo: Table " + TABLE_MYINFO + " is dropped.");

        db.execSQL(CREATE_TABLE_MYINFO);
        Log.d(TAG_DB, "[Database] dropTableMyInfo: Table " + TABLE_MYINFO + " is recreated.");
    }

    /**
     * Drop the table [myhereagents]
     */
    public void dropTableMyHereAgents() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MYHEREAGENTS);
        Log.d(TAG_DB, "[Database] dropTableMyHereAgents: Table " + TABLE_MYHEREAGENTS + " is dropped.");

        db.execSQL(CREATE_TABLE_MYHEREAGENTS);
        Log.d(TAG_DB, "[Database] dropTableMyHereAgents: Table " + TABLE_MYHEREAGENTS + " is recreated.");
    }

    /**
     * Drop the table [myroutines]
     */
    public void dropTableMyRoutines() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MYROUTINES);
        Log.d(TAG_DB, "[Database] dropTableMyRoutines: Table " + TABLE_MYROUTINES + " is dropped.");

        db.execSQL(CREATE_TABLE_MYROUTINES);
        Log.d(TAG_DB, "[Database] dropTableMyRoutines: Table " + TABLE_MYROUTINES + " is recreated.");
    }

    /**
     * Drop the table [myrecords]
     */
    public void dropTableMyRecords() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MYRECORDS);
        Log.d(TAG_DB, "[Database] dropTableMyRecords: Table " + TABLE_MYRECORDS + " is dropped.");

        db.execSQL(CREATE_TABLE_MYRECORDS);
        Log.d(TAG_DB, "[Database] dropTableMyRecords: Table " + TABLE_MYRECORDS + " is recreated.");
    }

    /**
     * Drop all tables in DB
     */
    public void dropAllTables() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MYINFO);
        Log.d(TAG_DB, "[Database] dropAllTables: Table " + TABLE_MYINFO + " is dropped.");

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MYHEREAGENTS);
        Log.d(TAG_DB, "[Database] dropAllTables: Table " + TABLE_MYHEREAGENTS + " is dropped.");

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MYROUTINES);
        Log.d(TAG_DB, "[Database] dropAllTables: Table " + TABLE_MYROUTINES + " is dropped.");

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MYRECORDS);
        Log.d(TAG_DB, "[Database] dropAllTables: Table " + TABLE_MYRECORDS + " is dropped.");

        //Recreate tables
        onCreate(db);
        Log.d(TAG_DB, "[Database] dropAllTables: Tables are recreated.");

    }

    /**
     * Close the opened DB
     */
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen()) {
            db.close();
        }
    }


    /**
     * Get current DateTime
     * @return current DateTime
     */
    public String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

}
