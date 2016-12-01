package com.example.user.onedaynquestions.model;

import android.util.Log;

import java.util.Calendar;

/**
 * Created by user on 2016-06-10.
 */
public class RecordDateCalorie {
    String recordDate;
    double recordCalorie;

    public RecordDateCalorie() {
    }

    public RecordDateCalorie(String recordDate, double recordCalorie) {
        this.recordDate = recordDate;
        this.recordCalorie = recordCalorie;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public double getRecordCalorie() {
        return recordCalorie;
    }

    public void setRecordCalorie(double recordCalorie) {
        this.recordCalorie = recordCalorie;
    }

    public int getDaysGap() {
        int gap = 0;

        //Calendar for date
        Calendar calendar = Calendar.getInstance();

        int calendarDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int calendarMonth = calendar.get(Calendar.MONTH) + 1;

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String currentDateTime = sdf.format(new Date());                        //2016-06-10 06:09:43
//        String currentDate = parseOnlyDate(parseDateWithYear(currentDateTime)); //06-10

        String parsedRecordDate = parseOnlyDate(recordDate);

        Log.d("currentDateTime", "recordDate: " + recordDate);

        int recordDateDayOfMonth = getDay(parsedRecordDate);
        int recordDateMonth = getMonth(parsedRecordDate);

        gap += (calendarMonth - recordDateMonth) * 30;
        gap += (calendarDayOfMonth - recordDateDayOfMonth);

        return gap;
    }

    private int getMonth(String currentDate) {
        String monthString = currentDate.substring(0,2);
        Log.d("currentDateTime", "monthString: " + monthString);

        return Integer.parseInt(monthString);
    }

    private int getDay(String currentDate) {
        String dayString = currentDate.substring(3, 5);
        Log.d("currentDateTime", "dayString: " + dayString);

        return Integer.parseInt(dayString);
    }

//    private String parseDateWithYear(String datetimeData) {
//        //dateTimeData: 2016-06-10 00:21:32
//
//        int spaceLoc = datetimeData.indexOf(" ");
//        Log.d("TestDateParse", "spaceLoc: " + spaceLoc);
//        return datetimeData.substring(0, spaceLoc);
//    }

    private String parseOnlyDate(String dateWithYear) {
        //dateWithYear: 2016-06-10

        int hyphenLoc = dateWithYear.indexOf("-");
        Log.d("TestDateParse", "hyphenLoc: " + hyphenLoc + " (" + dateWithYear.charAt(hyphenLoc) + ")");
        return dateWithYear.substring(hyphenLoc + 1, hyphenLoc + 6);
    }
}
