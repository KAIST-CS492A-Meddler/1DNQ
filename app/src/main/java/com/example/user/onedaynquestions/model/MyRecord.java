package com.example.user.onedaynquestions.model;

/**
 * Created by user on 2016-04-18.
 */
public class MyRecord {

    /* myrecords table */
    /*
    private static final String ATTR_MYRECORD_ID = "myrecord_id";
    private static final String ATTR_MYRECORD_DATE = "myrecord_date";
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
                    ATTR_MYRECORD_DATE + " VARCHAR(30), " +
                    ATTR_MYRECORD_EQ1_ID + " VARCHAR(30), " +
                    ATTR_MYRECORD_EQ1_DONE + " VARCHAR(30), " +
                    ATTR_MYRECORD_EQ2_ID + " VARCHAR(30), " +
                    ATTR_MYRECORD_EQ2_DONE + " VARCHAR(30), " +
                    ATTR_MYRECORD_EQ3_ID + " VARCHAR(30), " +
                    ATTR_MYRECORD_EQ3_DONE + " VARCHAR(30), " +
                    ATTR_MYRECORD_EQ4_ID + " VARCHAR(30), " +
                    ATTR_MYRECORD_EQ4_DONE + " VARCHAR(30), " +
                    ATTR_MYRECORD_EQ5_ID + " VARCHAR(30), " +
                    ATTR_MYRECORD_EQ5_DONE + " VARCHAR(30), " +
                    "FOREIGN_KEY(" + ATTR_MYRECORD_EQ1_ID +
                    ") REFERENCES " + TABLE_MYHEREAGENTS + "(" + ATTR_MYEQ_MACID + ")," +
                    "FOREIGN_KEY(" + ATTR_MYRECORD_EQ2_ID +
                    ") REFERENCES " + TABLE_MYHEREAGENTS + "(" + ATTR_MYEQ_MACID + ")," +
                    "FOREIGN_KEY(" + ATTR_MYRECORD_EQ3_ID +
                    ") REFERENCES " + TABLE_MYHEREAGENTS + "(" + ATTR_MYEQ_MACID + ")," +
                    "FOREIGN_KEY(" + ATTR_MYRECORD_EQ4_ID +
                    ") REFERENCES " + TABLE_MYHEREAGENTS + "(" + ATTR_MYEQ_MACID + ")," +
                    "FOREIGN_KEY(" + ATTR_MYRECORD_EQ5_ID +
                    ") REFERENCES " + TABLE_MYHEREAGENTS + "(" + ATTR_MYEQ_MACID + ")" +
                    ")"; */

    String recordId;
    String recordName;
    String recordDateTime;
    String recordEq1Id;
    int recordEq1Done;
    String recordEq2Id;
    int recordEq2Done;
    String recordEq3Id;
    int recordEq3Done;
    String recordEq4Id;
    int recordEq4Done;
    String recordEq5Id;
    int recordEq5Done;

    private void initRecord() {
        recordId = "-1";
        recordName = "-1";
        recordDateTime = "-1";
        recordEq1Id = "-1";
        recordEq1Done = -1;
        recordEq2Id = "-1";
        recordEq2Done = -1;
        recordEq3Id = "-1";
        recordEq3Done = -1;
        recordEq4Id = "-1";
        recordEq4Done = -1;
        recordEq5Id = "-1";
        recordEq5Done = -1;
    }

    public MyRecord() {
        initRecord();
    }

    public MyRecord(String recordId, String recordName) {
        initRecord();

        this.recordId = recordId;
        this.recordName = recordName;
    }

    public MyRecord(String recordId, String recordName, String recordDateTime) {
        initRecord();

        this.recordId = recordId;
        this.recordName = recordName;
        this.recordDateTime = recordDateTime;
    }

    public MyRecord(String recordId, String recordName, String recordDateTime, String recordEq1Id, int recordEq1Done) {
        initRecord();

        this.recordId = recordId;
        this.recordName = recordName;
        this.recordDateTime = recordDateTime;
        this.recordEq1Id = recordEq1Id;
        this.recordEq1Done = recordEq1Done;
    }

    public MyRecord(String recordId, String recordName, String recordDateTime, String recordEq1Id, int recordEq1Done, String recordEq2Id, int recordEq2Done) {
        initRecord();

        this.recordId = recordId;
        this.recordName = recordName;
        this.recordDateTime = recordDateTime;
        this.recordEq1Id = recordEq1Id;
        this.recordEq1Done = recordEq1Done;
        this.recordEq2Id = recordEq2Id;
        this.recordEq2Done = recordEq2Done;
    }

    public MyRecord(String recordId, String recordName, String recordDateTime, String recordEq1Id, int recordEq1Done, String recordEq2Id, int recordEq2Done, String recordEq3Id, int recordEq3Done) {
        initRecord();

        this.recordId = recordId;
        this.recordName = recordName;
        this.recordDateTime = recordDateTime;
        this.recordEq1Id = recordEq1Id;
        this.recordEq1Done = recordEq1Done;
        this.recordEq2Id = recordEq2Id;
        this.recordEq2Done = recordEq2Done;
        this.recordEq3Id = recordEq3Id;
        this.recordEq3Done = recordEq3Done;
    }

    public MyRecord(String recordId, String recordName, String recordDateTime, String recordEq1Id, int recordEq1Done, String recordEq2Id, int recordEq2Done, String recordEq3Id, int recordEq3Done, String recordEq4Id, int recordEq4Done) {
        initRecord();

        this.recordId = recordId;
        this.recordName = recordName;
        this.recordDateTime = recordDateTime;
        this.recordEq1Id = recordEq1Id;
        this.recordEq1Done = recordEq1Done;
        this.recordEq2Id = recordEq2Id;
        this.recordEq2Done = recordEq2Done;
        this.recordEq3Id = recordEq3Id;
        this.recordEq3Done = recordEq3Done;
        this.recordEq4Id = recordEq4Id;
        this.recordEq4Done = recordEq4Done;
    }

    public MyRecord(String recordId, String recordName, String recordDateTime, String recordEq1Id, int recordEq1Done, String recordEq2Id, int recordEq2Done, String recordEq3Id, int recordEq3Done, String recordEq4Id, int recordEq4Done, String recordEq5Id, int recordEq5Done) {
        initRecord();

        this.recordId = recordId;
        this.recordName = recordName;
        this.recordDateTime = recordDateTime;
        this.recordEq1Id = recordEq1Id;
        this.recordEq1Done = recordEq1Done;
        this.recordEq2Id = recordEq2Id;
        this.recordEq2Done = recordEq2Done;
        this.recordEq3Id = recordEq3Id;
        this.recordEq3Done = recordEq3Done;
        this.recordEq4Id = recordEq4Id;
        this.recordEq4Done = recordEq4Done;
        this.recordEq5Id = recordEq5Id;
        this.recordEq5Done = recordEq5Done;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getRecordName() {
        return recordName;
    }

    public void setRecordName(String recordName) {
        this.recordName = recordName;
    }

    public String getRecordDateTime() {
        return recordDateTime;
    }

    public void setRecordDateTime(String recordDateTime) {
        this.recordDateTime = recordDateTime;
    }

    public String getRecordEq1Id() {
        return recordEq1Id;
    }

    public void setRecordEq1Id(String recordEq1Id) {
        this.recordEq1Id = recordEq1Id;
    }

    public int getRecordEq1Done() {
        return recordEq1Done;
    }

    public void setRecordEq1Done(int recordEq1Done) {
        this.recordEq1Done = recordEq1Done;
    }

    public String getRecordEq2Id() {
        return recordEq2Id;
    }

    public void setRecordEq2Id(String recordEq2Id) {
        this.recordEq2Id = recordEq2Id;
    }

    public int getRecordEq2Done() {
        return recordEq2Done;
    }

    public void setRecordEq2Done(int recordEq2Done) {
        this.recordEq2Done = recordEq2Done;
    }

    public String getRecordEq3Id() {
        return recordEq3Id;
    }

    public void setRecordEq3Id(String recordEq3Id) {
        this.recordEq3Id = recordEq3Id;
    }

    public int getRecordEq3Done() {
        return recordEq3Done;
    }

    public void setRecordEq3Done(int recordEq3Done) {
        this.recordEq3Done = recordEq3Done;
    }

    public String getRecordEq4Id() {
        return recordEq4Id;
    }

    public void setRecordEq4Id(String recordEq4Id) {
        this.recordEq4Id = recordEq4Id;
    }

    public int getRecordEq4Done() {
        return recordEq4Done;
    }

    public void setRecordEq4Done(int recordEq4Done) {
        this.recordEq4Done = recordEq4Done;
    }

    public String getRecordEq5Id() {
        return recordEq5Id;
    }

    public void setRecordEq5Id(String recordEq5Id) {
        this.recordEq5Id = recordEq5Id;
    }

    public int getRecordEq5Done() {
        return recordEq5Done;
    }

    public void setRecordEq5Done(int recordEq5Done) {
        this.recordEq5Done = recordEq5Done;
    }
}
