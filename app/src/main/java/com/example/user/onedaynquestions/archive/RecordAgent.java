package com.example.user.onedaynquestions.archive;

import android.util.Log;

import com.example.user.onedaynquestions.view.activity.MainActivity;

import java.io.Serializable;
import java.util.StringTokenizer;

/**
 * Created by user on 2016-06-08.
 */
public class RecordAgent implements Serializable{
    String agentName;
    String agentMacId;
    int agentType;

    int goalSet;
    int goalCount;
    int goalTime;

    int recordCount;
    int recordTime;

    public RecordAgent() {
        agentName = "-1";
        agentMacId = "-1";

        goalSet = -1;
        goalCount = -1;
        goalTime = -1;

        agentType = 1;
        recordCount = 0;
        recordTime = 0;
    }

    public RecordAgent(String agentMacId) {
        this.agentMacId = agentMacId;

//        MyHereAgent tmpAgent = MainActivity.hereDB.getMyHereAgent(agentMacId);
//        this.agentName = tmpAgent.getMyeqName();
//        this.agentType = tmpAgent.getMyeqType();

        goalSet = -1;
        goalCount = -1;
        goalTime = -1;

        recordCount = 0;
        recordTime = 0;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentMacId() {
        return agentMacId;
    }

    public void setAgentMacId(String agentMacId) {
        this.agentMacId = agentMacId;
    }

    public int getAgentType() {
        return agentType;
    }

    public void setAgentType(int agentType) {
        this.agentType = agentType;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public int getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(int recordTime) {
        this.recordTime = recordTime;
    }

    public int getGoalSet() {
        return goalSet;
    }

    public void setGoalSet(int goalSet) {
        this.goalSet = goalSet;
    }

    public int getGoalCount() {
        return goalCount;
    }

    public void setGoalCount(int goalCount) {
        this.goalCount = goalCount;
    }

    public int getGoalTime() {
        return goalTime;
    }

    public void setGoalTime(int goalTime) {
        this.goalTime = goalTime;
    }

    public void setGoal(int goalSet, int goalCount, int goalTime) {
        this.goalSet = goalSet;
        this.goalCount = goalCount;
        this.goalTime = goalTime;
    }

    /**
     * @return              Parsed/Refined String
     */
    public String makeGoalString() {

        String rawGoal = "";
        rawGoal += String.format("%d", goalSet) + "|";
        rawGoal += String.format("%d", goalCount) + "|";
        rawGoal += String.format("%d", goalTime);

        String goalSentence = "";

        int goalType = 0;   //1: count times, 2: count secs, 3: count times & secs

        String set = "";    //sets
        String count = "";  //times
        String time = "";   //secs

        StringTokenizer tokens = new StringTokenizer(rawGoal, "|");
        set = tokens.nextToken();
        count = tokens.nextToken();
        time = tokens.nextToken();

        Log.d("TokenizerExercise", "set[" + set + "], count[" + count + "], time[" + "]");

        //Single or multiple
        int intSet = Integer.parseInt(set);
        int intCount = Integer.parseInt(count);
        int intTime = Integer.parseInt(time);

        set += " SET";
        count += " TIME";
        time += " SEC";

        //Check goal type
        if (intCount == -1) {
            goalType = 2;
        }
        if (intTime == -1) {
            goalType = 1;
        }
        if (intTime != -1 && intCount != -1) {
            goalType = 3;
        }

        if (intSet > 1) {
            set += "S";
        }
        if (intCount > 1) {
            count += "S";
        }
        if (intTime > 1) {
            time += "S";
        }

        switch (goalType) {
            //Count times (회수)
            case 1:
                goalSentence = count + " X " + set;
                break;
            //Count secs (시간)
            case 2:
                goalSentence = time + " X " + set;
                break;
            // Count both times & secs
            case 3:
                goalSentence = count + " X " + set + " (" + time + ")";
                break;
        }

        return goalSentence;
    }
}
