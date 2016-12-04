package com.example.user.onedaynquestions.archive;

import java.io.Serializable;

/**
 * Created by user on 2016-04-18.
 */
public class MyRoutine implements Serializable{

    /* myroutines table */
    /*
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
                    "FOREIGN_KEY(" + ATTR_MYROUTINE_EQ1_ID +
                    ") REFERENCES " + TABLE_MYHEREAGENTS + "(" + ATTR_MYEQ_MACID + ")," +
                    "FOREIGN_KEY(" + ATTR_MYROUTINE_EQ2_ID +
                    ") REFERENCES " + TABLE_MYHEREAGENTS + "(" + ATTR_MYEQ_MACID + ")," +
                    "FOREIGN_KEY(" + ATTR_MYROUTINE_EQ3_ID +
                    ") REFERENCES " + TABLE_MYHEREAGENTS + "(" + ATTR_MYEQ_MACID + ")," +
                    "FOREIGN_KEY(" + ATTR_MYROUTINE_EQ4_ID +
                    ") REFERENCES " + TABLE_MYHEREAGENTS + "(" + ATTR_MYEQ_MACID + ")," +
                    "FOREIGN_KEY(" + ATTR_MYROUTINE_EQ5_ID +
                    ") REFERENCES " + TABLE_MYHEREAGENTS + "(" + ATTR_MYEQ_MACID + ")" +
                    ");";*/

    String routineId;
    String routineName;
    String routineEq1Id;
    String routineEq1Goal;
    String routineEq2Id;
    String routineEq2Goal;
    String routineEq3Id;
    String routineEq3Goal;
    String routineEq4Id;
    String routineEq4Goal;
    String routineEq5Id;
    String routineEq5Goal;

    private void initRoutine() {
        routineId = "-1";
        routineName = "-1";
        routineEq1Id = "-1";
        routineEq1Goal = "-1";
        routineEq2Id = "-1";
        routineEq2Goal = "-1";
        routineEq3Id = "-1";
        routineEq3Goal = "-1";
        routineEq4Id = "-1";
        routineEq4Goal = "-1";
        routineEq5Id = "-1";
        routineEq5Goal = "-1";
    }

    public MyRoutine() {
        initRoutine();
    }

    public MyRoutine(String routineId, String routineName) {
        initRoutine();

        this.routineId = routineId;
        this.routineName = routineName;
    }

    public MyRoutine(String routineId, String routineName, String routineEq1Id, String routineEq1Goal) {
        initRoutine();

        this.routineId = routineId;
        this.routineName = routineName;
        this.routineEq1Id = routineEq1Id;
        this.routineEq1Goal = routineEq1Goal;
    }

    public MyRoutine(String routineId, String routineName, String routineEq1Id, String routineEq1Goal, String routineEq2Id, String routineEq2Goal) {
        initRoutine();

        this.routineId = routineId;
        this.routineName = routineName;
        this.routineEq1Id = routineEq1Id;
        this.routineEq1Goal = routineEq1Goal;
        this.routineEq2Id = routineEq2Id;
        this.routineEq2Goal = routineEq2Goal;
    }

    public MyRoutine(String routineId, String routineName, String routineEq1Id, String routineEq1Goal, String routineEq2Id, String routineEq2Goal, String routineEq3Id, String routineEq3Goal) {
        initRoutine();

        this.routineId = routineId;
        this.routineName = routineName;
        this.routineEq1Id = routineEq1Id;
        this.routineEq1Goal = routineEq1Goal;
        this.routineEq2Id = routineEq2Id;
        this.routineEq2Goal = routineEq2Goal;
        this.routineEq3Id = routineEq3Id;
        this.routineEq3Goal = routineEq3Goal;
    }

    public MyRoutine(String routineId, String routineName, String routineEq1Id, String routineEq1Goal, String routineEq2Id, String routineEq2Goal, String routineEq3Id, String routineEq3Goal, String routineEq4Id, String routineEq4Goal) {
        initRoutine();

        this.routineId = routineId;
        this.routineName = routineName;
        this.routineEq1Id = routineEq1Id;
        this.routineEq1Goal = routineEq1Goal;
        this.routineEq2Id = routineEq2Id;
        this.routineEq2Goal = routineEq2Goal;
        this.routineEq3Id = routineEq3Id;
        this.routineEq3Goal = routineEq3Goal;
        this.routineEq4Id = routineEq4Id;
        this.routineEq4Goal = routineEq4Goal;
    }

    public MyRoutine(String routineId, String routineName, String routineEq1Id, String routineEq1Goal, String routineEq2Id, String routineEq2Goal, String routineEq3Id, String routineEq3Goal, String routineEq4Id, String routineEq4Goal, String routineEq5Id, String routineEq5Goal) {
        initRoutine();

        this.routineId = routineId;
        this.routineName = routineName;
        this.routineEq1Id = routineEq1Id;
        this.routineEq1Goal = routineEq1Goal;
        this.routineEq2Id = routineEq2Id;
        this.routineEq2Goal = routineEq2Goal;
        this.routineEq3Id = routineEq3Id;
        this.routineEq3Goal = routineEq3Goal;
        this.routineEq4Id = routineEq4Id;
        this.routineEq4Goal = routineEq4Goal;
        this.routineEq5Id = routineEq5Id;
        this.routineEq5Goal = routineEq5Goal;
    }

    public String getRoutineId() {
        return routineId;
    }

    public void setRoutineId(String routineId) {
        this.routineId = routineId;
    }

    public String getRoutineName() {
        return routineName;
    }

    public void setRoutineName(String routineName) {
        this.routineName = routineName;
    }

    public String getRoutineEq1Id() {
        return routineEq1Id;
    }

    public void setRoutineEq1Id(String routineEq1Id) {
        this.routineEq1Id = routineEq1Id;
    }

    public String getRoutineEq1Goal() {
        return routineEq1Goal;
    }

    public void setRoutineEq1Goal(String routineEq1Goal) {
        this.routineEq1Goal = routineEq1Goal;
    }

    public String getRoutineEq2Id() {
        return routineEq2Id;
    }

    public void setRoutineEq2Id(String routineEq2Id) {
        this.routineEq2Id = routineEq2Id;
    }

    public String getRoutineEq2Goal() {
        return routineEq2Goal;
    }

    public void setRoutineEq2Goal(String routineEq2Goal) {
        this.routineEq2Goal = routineEq2Goal;
    }

    public String getRoutineEq3Id() {
        return routineEq3Id;
    }

    public void setRoutineEq3Id(String routineEq3Id) {
        this.routineEq3Id = routineEq3Id;
    }

    public String getRoutineEq3Goal() {
        return routineEq3Goal;
    }

    public void setRoutineEq3Goal(String routineEq3Goal) {
        this.routineEq3Goal = routineEq3Goal;
    }

    public String getRoutineEq4Id() {
        return routineEq4Id;
    }

    public void setRoutineEq4Id(String routineEq4Id) {
        this.routineEq4Id = routineEq4Id;
    }

    public String getRoutineEq4Goal() {
        return routineEq4Goal;
    }

    public void setRoutineEq4Goal(String routineEq4Goal) {
        this.routineEq4Goal = routineEq4Goal;
    }

    public String getRoutineEq5Id() {
        return routineEq5Id;
    }

    public void setRoutineEq5Id(String routineEq5Id) {
        this.routineEq5Id = routineEq5Id;
    }

    public String getRoutineEq5Goal() {
        return routineEq5Goal;
    }

    public void setRoutineEq5Goal(String routineEq5Goal) {
        this.routineEq5Goal = routineEq5Goal;
    }
}
