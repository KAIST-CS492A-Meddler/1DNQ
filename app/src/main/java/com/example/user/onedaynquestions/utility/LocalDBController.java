package com.example.user.onedaynquestions.utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.user.onedaynquestions.model.MyCard;
import com.example.user.onedaynquestions.model.MyGroup;
import com.example.user.onedaynquestions.model.MyInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by user on 2016-12-04.
 */

public class LocalDBController extends SQLiteOpenHelper{

    SQLiteDatabase mDB;

    private static final String TAG_DB = "LocalDBController";

    private static final String DATABASE_NAME = "odnqDB.db";
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
    private static final String ATTR_MYINFO_TOKEN = "myinfo_token";
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
                    ATTR_MYINFO_TOKEN + " VARCHAR(500), " +
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
        values.put(ATTR_MYINFO_TOKEN,myInfo.getMyInfoToken());
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
    /** BASIC SELECT QUERIES **/

    public MyInfo getMyInfo() {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_MYINFO;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null && c.getCount() != 0) {

            c.moveToFirst();

            MyInfo tmpMyInfo = new MyInfo();

            tmpMyInfo.setMyInfoId(c.getString(c.getColumnIndex(ATTR_MYINFO_ID)));
            tmpMyInfo.setMyInfoNick(c.getString(c.getColumnIndex(ATTR_MYINFO_NICK)));
            tmpMyInfo.setMyInfoName(c.getString(c.getColumnIndex(ATTR_MYINFO_NAME)));
            tmpMyInfo.setMyInfoAge(c.getInt(c.getColumnIndex(ATTR_MYINFO_AGE)));
            tmpMyInfo.setMyInfoGender(c.getInt(c.getColumnIndex(ATTR_MYINFO_GENDER)));
            tmpMyInfo.setMyInfoDeviceId(c.getString(c.getColumnIndex(ATTR_MYINFO_DEVICEID)));
            tmpMyInfo.setMyInfoToken(c.getString(c.getColumnIndex(ATTR_MYINFO_TOKEN)));
            tmpMyInfo.setMyInfoExp(c.getInt(c.getColumnIndex(ATTR_MYINFO_EXP)));
            tmpMyInfo.setMyInfoQuality(c.getFloat(c.getColumnIndex(ATTR_MYINFO_QUALITY)));
            tmpMyInfo.setMyInfoCardNum(c.getInt(c.getColumnIndex(ATTR_MYINFO_CARDNUM)));
            tmpMyInfo.setMyInfoLoginNum(c.getInt(c.getColumnIndex(ATTR_MYINFO_LOGINNUM)));
            tmpMyInfo.setMyInfoAnswerRight(c.getInt(c.getColumnIndex(ATTR_MYINFO_ANSWERRIGHT)));
            tmpMyInfo.setMyInfoAnswerWrong(c.getInt(c.getColumnIndex(ATTR_MYINFO_ANSWERWRONG)));

            return tmpMyInfo;

        } else {
            return null;
        }
    }

    public MyInfo getMyInfoWithId(String myInfoId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_MYINFO +
                " WHERE " + ATTR_MYINFO_ID + " = '" + myInfoId + "'";

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {
            if (c.getCount() != 0) {
                c.moveToFirst();

                MyInfo tmpMyInfo = new MyInfo();

                tmpMyInfo.setMyInfoId(myInfoId);
                tmpMyInfo.setMyInfoNick(c.getString(c.getColumnIndex(ATTR_MYINFO_NICK)));
                tmpMyInfo.setMyInfoName(c.getString(c.getColumnIndex(ATTR_MYINFO_NAME)));
                tmpMyInfo.setMyInfoAge(c.getInt(c.getColumnIndex(ATTR_MYINFO_AGE)));
                tmpMyInfo.setMyInfoGender(c.getInt(c.getColumnIndex(ATTR_MYINFO_GENDER)));
                tmpMyInfo.setMyInfoDeviceId(c.getString(c.getColumnIndex(ATTR_MYINFO_DEVICEID)));
                tmpMyInfo.setMyInfoExp(c.getInt(c.getColumnIndex(ATTR_MYINFO_EXP)));
                tmpMyInfo.setMyInfoQuality(c.getFloat(c.getColumnIndex(ATTR_MYINFO_QUALITY)));
                tmpMyInfo.setMyInfoCardNum(c.getInt(c.getColumnIndex(ATTR_MYINFO_CARDNUM)));
                tmpMyInfo.setMyInfoLoginNum(c.getInt(c.getColumnIndex(ATTR_MYINFO_LOGINNUM)));
                tmpMyInfo.setMyInfoAnswerRight(c.getInt(c.getColumnIndex(ATTR_MYINFO_ANSWERRIGHT)));
                tmpMyInfo.setMyInfoAnswerWrong(c.getInt(c.getColumnIndex(ATTR_MYINFO_ANSWERWRONG)));

                return tmpMyInfo;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public MyCard getMyCardWithId(String myCardId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_MYCARD +
                " WHERE " + ATTR_MYCARD_ID + " = '" + myCardId + "'";

        Cursor c = db.rawQuery(selectQuery, null);


        if (c != null) {
            if (c.getCount() != 0) {
                c.moveToFirst();

                MyCard tmpMyCard = new MyCard();

                tmpMyCard.setMyCardId(myCardId);
                tmpMyCard.setMyCardDateTime(c.getString(c.getColumnIndex(ATTR_MYCARD_DATETIME)));
                tmpMyCard.setMyCardType(c.getInt(c.getColumnIndex(ATTR_MYCARD_TYPE)));
                tmpMyCard.setMyCardMaker(c.getString(c.getColumnIndex(ATTR_MYCARD_MAKER)));
                tmpMyCard.setMyCardGroup(c.getString(c.getColumnIndex(ATTR_MYCARD_GROUP)));
                tmpMyCard.setMyCardQuestion(c.getString(c.getColumnIndex(ATTR_MYCARD_QUESTION)));
                tmpMyCard.setMyCardAnswer(c.getString(c.getColumnIndex(ATTR_MYCARD_ANSWER)));
                tmpMyCard.setMyCardWrong(c.getInt(c.getColumnIndex(ATTR_MYCARD_WRONGNUM)));
                tmpMyCard.setMyCardDifficulty(c.getInt(c.getColumnIndex(ATTR_MYCARD_DIFFICULTY)));
                tmpMyCard.setMyCardQuality(c.getInt(c.getColumnIndex(ATTR_MYCARD_QUALITY)));
                tmpMyCard.setMyCardStarred(c.getInt(c.getColumnIndex(ATTR_MYCARD_STARRED)));

                return tmpMyCard;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /** COUNT TABLE QUERIES **/

    public int countTableMyInfo() {
        SQLiteDatabase db = this.getReadableDatabase();

        String countQuery = "SELECT * FROM " + TABLE_MYINFO;
        Cursor c = db.rawQuery(countQuery, null);

        if (c != null) {
            int count = c.getCount();
            c.close();

            return count;
        } else {
            return 0;
        }
    }

    public int countTableMyCard() {
        SQLiteDatabase db = this.getReadableDatabase();

        String countQuery = "SELECT * FROM " + TABLE_MYCARD;
        Cursor c = db.rawQuery(countQuery, null);

        if (c != null) {
            int count = c.getCount();
            c.close();

            return count;
        } else {
            return 0;
        }
    }

    public int countTableMyGroup() {
        SQLiteDatabase db = this.getReadableDatabase();

        String countQuery = "SELECT * FROM " + TABLE_MYGROUP;
        Cursor c = db.rawQuery(countQuery, null);

        if (c != null) {
            int count = c.getCount();
            c.close();

            return count;
        } else {
            return 0;
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /** UPDATE TABLE QUERIES **/

    public int updateMyInfo(MyInfo myInfo) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ATTR_MYINFO_ID, myInfo.getMyInfoId());
        values.put(ATTR_MYINFO_NICK, myInfo.getMyInfoNick());
        values.put(ATTR_MYINFO_NAME, myInfo.getMyInfoName());
        values.put(ATTR_MYINFO_AGE, myInfo.getMyInfoAge());
        values.put(ATTR_MYINFO_GENDER, myInfo.getMyInfoGender());

        int returnVal;
        returnVal = db.update(TABLE_MYINFO, values, ATTR_MYINFO_ID + " = ?",
                new String[] { String.valueOf(myInfo.getMyInfoId()) });

        return returnVal;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /** DROP TABLE QUERIES **/

    public void dropTableMyInfo() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MYINFO);
        Log.d(TAG_DB, "[Database] LocalDBController - dropTableMyInfo(): Table(" + TABLE_MYINFO + ") is dropped.");

        db.execSQL(CREATE_TABLE_MYINFO);
        Log.d(TAG_DB, "[Database] LocalDBController - dropTableMyInfo(): Table(" + TABLE_MYINFO + ") is recreated.");
    }

    public void dropTableMyCard() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MYCARD);
        Log.d(TAG_DB, "[Database] LocalDBController - dropTableMyInfo(): Table(" + TABLE_MYCARD + ") is dropped.");

        db.execSQL(CREATE_TABLE_MYCARD);
        Log.d(TAG_DB, "[Database] LocalDBController - dropTableMyInfo(): Table(" + TABLE_MYCARD + ") is recreated.");
    }

    public void dropTableMyGroup() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MYGROUP);
        Log.d(TAG_DB, "[Database] LocalDBController - dropTableMyInfo(): Table(" + TABLE_MYGROUP + ") is dropped.");

        db.execSQL(CREATE_TABLE_MYGROUP);
        Log.d(TAG_DB, "[Database] LocalDBController - dropTableMyInfo(): Table(" + TABLE_MYGROUP + ") is recreated.");
    }

    public void dropAllTables() {
        dropTableMyInfo();
        dropTableMyCard();
        dropTableMyGroup();

        Log.d(TAG_DB, "[Database] LocalDBController - dropAllTables(): All tables are dropped & recreated.");

    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
    /** CLOSE DB QUERIES **/

    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen()) {
            db.close();
        }
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////

    public String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    public LocalDBController(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG_DB, "[Database-local] LocalDBController(): Constructor is called.");

        mDB = getWritableDatabase();

        Log.d(TAG_DB, "[Database-local] LocalDBController(): getWritableDatabase() is succeeded.");

        //this.mDB = mDB;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d(TAG_DB, "[Database-local] LocalDBController-onCreate(): Creating tables...");

        Log.d(TAG_DB, "[Database-local] CREATE_TABLE_MYINFO: " + CREATE_TABLE_MYINFO);
        Log.d(TAG_DB, "[Database-local] CREATE_TABLE_MYCARD: " + CREATE_TABLE_MYCARD);
        Log.d(TAG_DB, "[Database-local] CREATE_TABLE_MYGROUP: " + CREATE_TABLE_MYGROUP);

        db.execSQL(CREATE_TABLE_MYINFO);
        db.execSQL(CREATE_TABLE_MYCARD);
        db.execSQL(CREATE_TABLE_MYGROUP);

        Log.d(TAG_DB, "[Database-local] LocalDBController-onCreate(): Tables are created successfully.");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG_DB, "[Database-local] LocalDBController-onUpgrade()");

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MYINFO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MYCARD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MYGROUP);

        onCreate(db);

    }
}
