package com.example.user.onedaynquestions.archive;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by user on 2016-04-18.
 */
public class Equipment {


    //Minor ID of HERE devices
    public final static String DUMBEL = "0000A";
    public final static String PLANK = "00009";
    public final static String PUSH_UP = "00008";
    public final static String HOOP = "00007";
    public final static String JUMP_ROPE = "00006";

    String equipmentID;

    String equipmentName;
    //MAC address
    String equipmentSensorID;

    Date equipmentRegisterDate;
    Date equipmentLastUseDate;

    //Equipment type
    //0: NO NAME, 1: Dumbbells, 2: Pushup bars, 3: Jumprope, 4: Hoola-hoop, 5: Others
    public final static int TYPE_DUMBEL = 1;
    public final static int TYPE_PUSH_UP = 2;
    public final static int TYPE_JUMP_ROPE = 3;
    public final static int TYPE_HOOLA_HOOP = 4;
    public final static int TYPE_OTHERS = 5;

    int equipmentType;


    public Equipment() {
        this.equipmentID = "NO ID";
        this.equipmentSensorID = "NO SENSOR ID";
        this.equipmentRegisterDate = Calendar.getInstance(Locale.KOREA).getTime();
        this.equipmentName = "NO NAME";
        this.equipmentType = 0;
    }

    public Equipment(String equipmentName, int equipmentType) {
        this.equipmentID = "NO ID";
        this.equipmentSensorID = "NO SENSOR ID";
        this.equipmentRegisterDate =Calendar.getInstance(Locale.KOREA).getTime();
        this.equipmentName = equipmentName;
        this.equipmentType = equipmentType;

    }

    public Equipment(String equipmentID, String equipmentName, int equipmentType) {
        this.equipmentSensorID = "NO SENSOR ID";
        this.equipmentRegisterDate =Calendar.getInstance(Locale.KOREA).getTime();
        this.equipmentID = equipmentID;
        this.equipmentName = equipmentName;
        this.equipmentType = equipmentType;
    }

    public Equipment(String equipmentID, String equipmentName, String equipmentRegisterDate, int equipmentType) {
        this.equipmentSensorID = "NO SENSOR ID";
        this.equipmentID = equipmentID;
        this.equipmentName = equipmentName;
        this.equipmentRegisterDate =Calendar.getInstance(Locale.KOREA).getTime();
        this.equipmentType = equipmentType;
    }

    public Equipment(String equipmentID, String equipmentName, String equipmentSensorID, String equipmentRegisterDate, int equipmentType) {
        this.equipmentID = equipmentID;
        this.equipmentName = equipmentName;
        this.equipmentSensorID = equipmentSensorID;
        this.equipmentRegisterDate =Calendar.getInstance(Locale.KOREA).getTime();
        this.equipmentType = equipmentType;
    }

    public String getEquipmentID() {
        return equipmentID;
    }

    public void setEquipmentID(String equipmentID) {
        this.equipmentID = equipmentID;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentSensorID() {
        return equipmentSensorID;
    }

    public void setEquipmentSensorID(String equipmentSensorID) {
        this.equipmentSensorID = equipmentSensorID;
    }

    public String getEquipmentRegisterDate() {
        return equipmentRegisterDate.toString();
    }

    public void setEquipmentRegisterDate(Date equipmentRegisterDate) {
        this.equipmentRegisterDate = equipmentRegisterDate;
    }

    public int getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(int equipmentType) {
        this.equipmentType = equipmentType;
    }

    @Override
    public boolean equals(Object obj){
        return this.equipmentID.equals(((Equipment)obj).equipmentID);
    }
}
