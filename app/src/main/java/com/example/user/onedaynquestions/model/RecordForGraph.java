package com.example.user.onedaynquestions.model;

/**
 * Created by user on 2016-06-10.
 */
public class RecordForGraph {

    String recordId;
    String recordName;
    String recordDateTime;

    String recordEqId;
    int recordEqDone;

    public RecordForGraph() {
        this.recordId = "";
        this.recordName = "";
        this.recordDateTime = "";
        this.recordEqId = "";
        this.recordEqDone = 0;
    }

    public RecordForGraph(String recordId, String recordName, String recordDateTime) {
        this.recordId = recordId;
        this.recordName = recordName;
        this.recordDateTime = recordDateTime;
        this.recordEqId = "";
        this.recordEqDone = 0;
    }

    public RecordForGraph(String recordId, String recordName, String recordDateTime, String recordEqId, int recordEqDone) {
        this.recordId = recordId;
        this.recordName = recordName;
        this.recordDateTime = recordDateTime;
        this.recordEqId = recordEqId;
        this.recordEqDone = recordEqDone;
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

    public String getRecordEqId() {
        return recordEqId;
    }

    public void setRecordEqId(String recordEqId) {
        this.recordEqId = recordEqId;
    }

    public int getRecordEqDone() {
        return recordEqDone;
    }

    public void setRecordEqDone(int recordEqDone) {
        this.recordEqDone = recordEqDone;
    }
}
