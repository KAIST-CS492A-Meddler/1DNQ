package com.example.user.onedaynquestions.utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.user.onedaynquestions.model.MyCard;
import com.example.user.onedaynquestions.model.MyGroup;
import com.example.user.onedaynquestions.model.MyInfo;

/**
 * Created by user on 2016-12-04.
 */

public class DatabaseController extends SQLiteOpenHelper{

    SQLiteDatabase mDB;

    private static final String TAG_DB = "DatabaseControllerTag";

    private static final String DATABASE_NAME = "ODNQ_DB.db";
    private static final int DATABASE_VERSION = 1;

    /* Database tables in ODNQ_DB */
    private static final String TABLE_MYINFO = "MyInfo";
    private static final String TABLE_MYCARD = "MyCard";
    private static final String TABLE_MYGROUP = "MyGroup";

    /* MyInfo table */
    private static final String ATTR_MYINFO_ID = "myinfo_id";
    private static final String ATTR_MYINFO_NICK = "myinfo_nick";
    private static final String ATTR_MYINFO_NAME = "myinfo_name";
    private static final String ATTR_MYINFO_AGE = "myinfo_age";
    private static final String ATTR_MYINFO_GENDER = "myinfo_gender";
    private static final String ATTR_MYINFO_DEVICEID = "myinfo_deviceid";
    private static final String ATTR_MYINFO_EXP = "myinfo_exp";
    private static final String ATTR_MYINFO_QUALITY = "myinfo_quality";
    private static final String ATTR_MYINFO_CARDNUM = "myinfo_cardnum";
    private static final String ATTR_MYINFO_LOGINNUM = "myinfo_loginnum";
    private static final String ATTR_MYINFO_ANSWERRIGHT = "myinfo_answerright";
    private static final String ATTR_MYINFO_ANSWERWRONG = "myinfo_answerwrong";

    /* MyCard table */
    private static final String ATTR_MYCARD_ID = "mycard_id";
    private static final String ATTR_MYCARD_DATETIME = "mycard_datetime";
    private static final String ATTR_MYCARD_TYPE = "mycard_type";
    private static final String ATTR_MYCARD_MAKER = "mycard_maker";
    private static final String ATTR_MYCARD_GROUP = "mycard_group";
    private static final String ATTR_MYCARD_QUESTION = "mycard_question";
    private static final String ATTR_MYCARD_ANSWER = "mycard_answer";
    private static final String ATTR_MYCARD_WRONGNUM = "mycard_wrongnum";
    private static final String ATTR_MYCARD_DIFFICULTY = "mycard_difficulty";
    private static final String ATTR_MYCARD_QUALITY = "mycard_quality";
    private static final String ATTR_MYCARD_STARRED = "mycard_starred";

    /* MyGroup table */
    private static final String ATTR_MYGROUP_ID = "mygroup_id";
    private static final String ATTR_MYGROUP_NAME = "mygroup_name";
    private static final String ATTR_MYGROUP_REGISDATE = "mygroup_regisdate";
    private static final String ATTR_MYGROUP_RANKING = "mygroup_ranking";


    /**
     * SQL QUERIES
     */

    /* CREATE TABLE */

    //Create MyInfo table
    private static final String CREATE_TABLE_MYINFO =
            "CREATE TABLE " + TABLE_MYINFO + "(" +
                    ATTR_MYINFO_ID + " VARCHAR(30) PRIMARY KEY, " +
                    ATTR_MYINFO_NICK + " VARCHAR(30) NOT NULL, " +
                    ATTR_MYINFO_NAME + " VARCHAR(30) NOT NULL, " +
                    ATTR_MYINFO_AGE + " INTEGER DEFAULT 20, " +
                    ATTR_MYINFO_GENDER + " INTEGER DEFAULT 1, " +
                    ATTR_MYINFO_DEVICEID + " VARCHAR(100), " +
                    ATTR_MYINFO_EXP + " INTEGER DEFAULT 0, " +
                    ATTR_MYINFO_QUALITY + " FLOAT DEFAULT 0, " +
                    ATTR_MYINFO_CARDNUM + " INTEGER DEFAULT 0, " +
                    ATTR_MYINFO_LOGINNUM + " INTEGER DEFAULT 1, " +
                    ATTR_MYINFO_ANSWERRIGHT + " INTEGER DEFAULT 0, " +
                    ATTR_MYINFO_ANSWERWRONG + " INTEGER DEFAULT 0" +
                    ");";

    //Create MyCard table
    private static final String CREATE_TABLE_MYCARD =
            "CREATE TABLE " + TABLE_MYCARD + "(" +
                    ATTR_MYCARD_ID + " VARCHAR(30) PRIMARY KEY, " +
                    ATTR_MYCARD_DATETIME + " DATETIME NOT NULL, " +
                    ATTR_MYCARD_TYPE + " INTEGER DEFAULT 0, " +
                    ATTR_MYCARD_MAKER + " VARCHAR(30) NOT NULL, " +
                    ATTR_MYCARD_GROUP + " VARCHAR(30) NOT NULL, " +
                    ATTR_MYCARD_QUESTION + " VARCHAR(50) NOT NULL, " +
                    ATTR_MYCARD_ANSWER + " VARCHAR(100) NOT NULL, " +
                    ATTR_MYCARD_WRONGNUM + " INTEGER DEFAULT 0, " +
                    ATTR_MYCARD_DIFFICULTY + " INTEGER DEFAULT -1, " +
                    ATTR_MYCARD_QUALITY + " INTEGER DEFAULT -1, " +
                    ATTR_MYCARD_STARRED + " INTEGER DEFAULT 0" +
                    ");";

    //Create MyGroup table
    private static final String CREATE_TABLE_MYGROUP =
            "CREATE TABLE " + TABLE_MYGROUP + "(" +
                    ATTR_MYGROUP_ID + " VARCHAR(30) PRIMARY KEY, " +
                    ATTR_MYGROUP_NAME + " VARCHAR(50) NOT NULL, " +
                    ATTR_MYGROUP_REGISDATE + " DATETIME NOT NULL, " +
                    ATTR_MYGROUP_RANKING + " INTEGER DEFAULT -1" +
                    ");";


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /** INSERT QUERIES **/


    public long insertMyInfo(MyInfo myInfo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ATTR_MYINFO_ID,myInfo.getMyInfoId());
        values.put(ATTR_MYINFO_NICK,myInfo.getMyInfoNick());
        values.put(ATTR_MYINFO_NAME,myInfo.getMyInfoName());
        values.put(ATTR_MYINFO_AGE,myInfo.getMyInfoAge());
        values.put(ATTR_MYINFO_GENDER,myInfo.getMyInfoGender());
        values.put(ATTR_MYINFO_DEVICEID,myInfo.getMyInfoDeviceId());
        values.put(ATTR_MYINFO_EXP,myInfo.getMyInfoExp());
        values.put(ATTR_MYINFO_QUALITY,myInfo.getMyInfoQuality());
        values.put(ATTR_MYINFO_CARDNUM,myInfo.getMyInfoCardNum());
        values.put(ATTR_MYINFO_LOGINNUM,myInfo.getMyInfoLoginNum());
        values.put(ATTR_MYINFO_ANSWERRIGHT,myInfo.getMyInfoAnswerRight());
        values.put(ATTR_MYINFO_ANSWERWRONG,myInfo.getMyInfoAnswerWrong());

        long myInfo_id = db.insert(TABLE_MYINFO, null, values);
        return myInfo_id;

    }


    public long insertMyCard(MyCard myCard) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ATTR_MYCARD_ID,myCard.getMyCardId());
        values.put(ATTR_MYCARD_DATETIME,myCard.getMyCardDateTime());
        values.put(ATTR_MYCARD_TYPE,myCard.getMyCardType());
        values.put(ATTR_MYCARD_MAKER,myCard.getMyCardMaker());
        values.put(ATTR_MYCARD_GROUP,myCard.getMyCardGroup());
        values.put(ATTR_MYCARD_QUESTION,myCard.getMyCardQuestion());
        values.put(ATTR_MYCARD_ANSWER,myCard.getMyCardAnswer());
        values.put(ATTR_MYCARD_WRONGNUM,myCard.getMyCardWrong());
        values.put(ATTR_MYCARD_DIFFICULTY,myCard.getMyCardDifficulty());
        values.put(ATTR_MYCARD_QUALITY,myCard.getMyCardQuality());
        values.put(ATTR_MYCARD_STARRED,myCard.getMyCardStarred());

        long myCard_id = db.insert(TABLE_MYCARD, null, values);
        return myCard_id;
    }


    public long insertMyGroup(MyGroup myGroup) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ATTR_MYGROUP_ID,myGroup.getMyGroupId());
        values.put(ATTR_MYGROUP_NAME,myGroup.getMyGroupName());
        values.put(ATTR_MYGROUP_REGISDATE,myGroup.getMyGroupRegisDate());
        values.put(ATTR_MYGROUP_RANKING,myGroup.getMyGroupRanking());

        long myGroup_id = db.insert(TABLE_MYGROUP, null, values);
        return myGroup_id;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////


    public DatabaseController(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG_DB, "[Database-local] DatabaseController(): Constructor is called.");

        mDB = getWritableDatabase();
        //this.mDB = mDB;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d(TAG_DB, "[Database-local] DatabaseController-onCreate(): Creating tables...");

        Log.d(TAG_DB, "[Database-local] CREATE_TABLE_MYINFO: " + CREATE_TABLE_MYINFO);
        Log.d(TAG_DB, "[Database-local] CREATE_TABLE_MYCARD: " + CREATE_TABLE_MYCARD);
        Log.d(TAG_DB, "[Database-local] CREATE_TABLE_MYGROUP: " + CREATE_TABLE_MYGROUP);

        db.execSQL(CREATE_TABLE_MYINFO);
        db.execSQL(CREATE_TABLE_MYCARD);
        db.execSQL(CREATE_TABLE_MYGROUP);

        Log.d(TAG_DB, "[Database-local] DatabaseController-onCreate(): Tables are created successfully.");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG_DB, "[Database-local] DatabaseController-onUpgrade()");

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MYINFO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MYCARD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MYGROUP);

        onCreate(db);

    }
}
