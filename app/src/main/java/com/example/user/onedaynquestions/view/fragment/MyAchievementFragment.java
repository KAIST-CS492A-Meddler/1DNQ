package com.example.user.onedaynquestions.view.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.onedaynquestions.R;
import com.example.user.onedaynquestions.controller.QuestionListAdapter;
import com.example.user.onedaynquestions.model.MyCard;
import com.example.user.onedaynquestions.model.MyInfo;
import com.example.user.onedaynquestions.model.UnitRecord;
import com.example.user.onedaynquestions.view.activity.MainActivity;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ymbae on 2016-04-18.
 */
public class MyAchievementFragment extends Fragment{
//    ListViewAdapter listViewAdapter;

//    TextView tv_noroutine;
//    HorizontalScrollView horizontalScrollView;
//
//    Button btn_refresh;
    View viewFragmentMyAchievement;

    private TextView myachievement_tv_submission;
    private TextView myachievement_tv_contribution;
    private TextView myachievement_tv_quality;
    private TextView myachievement_tv_correctness;
    private TextView myachievement_tv_regmessage;

    GraphView contribution, record;
    private ArrayList<UnitRecord> records;
    Date[] days;
    private ListView lvQuestionList;

    private ArrayList<MyCard> questionList;
    private QuestionListAdapter questionListAdapter;
    private int numOfShow = 7;
    private int lastIndexOfRecords;
    CountDownTimer waitWhileDBupdate;
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        listViewAdapter = new ListViewAdapter();
        viewFragmentMyAchievement = inflater.inflate(R.layout.fragment_myachievement, container, false);

        contribution = (GraphView)viewFragmentMyAchievement.findViewById(R.id.weekly_contribution_gv);
        record = (GraphView)viewFragmentMyAchievement.findViewById(R.id.weekly_record_gv);

        initWidgets(viewFragmentMyAchievement);
        initMyInfo();

        return viewFragmentMyAchievement;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }


    private void initGraphData() {


//        getMyDailyRecords();
//
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -numOfShow);
        days = new Date[numOfShow];
        for(int i =0; i  < numOfShow; i++){
            days[i] = calendar.getTime();
            calendar.add(Calendar.DATE, 1);
        }
/*
        calendar.add(Calendar.DATE, -20);
        Date[] daysTemp = new Date[20];
        for(int i =0; i  < 20; i++){
            daysTemp[i] = calendar.getTime();
            calendar.add(Calendar.DATE, 1);
        }

        records = new ArrayList<>();
//        //public UnitRecord(Date dailyRecordDateTime, int dailyRecordContribution, int dailyRecordStudyRight, int dailyRecordStudyWrong) {
//        records.add(new UnitRecord(daysTemp[0],5, 4,2 ));
//        records.add(new UnitRecord(daysTemp[1],6, 6,2 ));
//        records.add(new UnitRecord(daysTemp[2],15, 3,22 ));
//        records.add(new UnitRecord(daysTemp[3],25, 34,2 ));
//        records.add(new UnitRecord(daysTemp[4],15, 24,3 ));
//        records.add(new UnitRecord(daysTemp[5],45, 45,6 ));
//        records.add(new UnitRecord(daysTemp[6],56, 14,8 ));
//        records.add(new UnitRecord(daysTemp[7],50, 44,15 ));
//        records.add(new UnitRecord(daysTemp[8],67, 24,10 ));
//        records.add(new UnitRecord(daysTemp[9],78, 42,25 ));
//        records.add(new UnitRecord(daysTemp[10],90, 43,22 ));
//        records.add(new UnitRecord(daysTemp[11],105, 44,12 ));
//        records.add(new UnitRecord(daysTemp[12],145, 44,6 ));
//        records.add(new UnitRecord(daysTemp[14],205, 34,12 ));
//        records.add(new UnitRecord(daysTemp[15],255, 34,4 ));
//        records.add(new UnitRecord(daysTemp[17],405, 48,2 ));
//        records.add(new UnitRecord(daysTemp[18],565, 25,4 ));
//        records.add(new UnitRecord("2016-12-12 12:03:22",500, 34,7 ));
//
*/

        if (MainActivity.odnqDB != null) {
            if (MainActivity.odnqDB.getMyDailyRecords() != null) {
                records = MainActivity.odnqDB.getMyDailyRecords();

                if (records.size() > 3) {
                    for (int i = 0; i < 3; i++) {
                        Log.d("DailyRecord", "[Database-related] records~getDailyRecordDateTime: " + records.get(i).getDailyRecordDateTime());
                        Log.d("DailyRecord", "[Database-related] records~getDailyRecordDateTime_Date: " + records.get(i).getDailyRecordDateTime_Date());
                        Log.d("DailyRecord", "[Database-related] records~getContribution: " + records.get(i).getDailyRecordContribution());
                        Log.d("DailyRecord", "[Database-related] records~getDailyRecordStudyRight: " + records.get(i).getDailyRecordStudyRight());
                        Log.d("DailyRecord", "[Database-related] records~getDailyRecordStudyWrong: " + records.get(i).getDailyRecordStudyWrong());
                    }
                }

            } else {
                Log.d("DailyRecord", "[MyAchievementFragment] There is no DailyRecord DB");
            }
        }

        if(records == null){
            records = new ArrayList<>();
        }
        int recordSize = records.size();
//        for(int i  = records.size() ; i < numOfShow; i++){
//
//            records.add(i - recordSize, new UnitRecord());
//        }
        Date tempDateRecord;
        lastIndexOfRecords = records.size()-numOfShow;
        /*
        do{
            if(lastIndexOfRecords < 1)  break;
            lastIndexOfRecords--;
            tempDateRecord = records.get(lastIndexOfRecords).getDailyRecordDateTime_Date();
        }while(days[0].before(tempDateRecord));
*/

        LineGraphSeries<DataPoint> contributionSeries = getContributionSeries();

        BarGraphSeries<DataPoint> recordWrongSeries = getRecordWrongSeries();
        BarGraphSeries<DataPoint> recordTrueSeries = getRecordTrueSeries();

//        contribution.getViewport().setMinX(days[0].getTime());
//        contribution.getViewport().setMaxX(days[6].getTime());
        contribution.getViewport().setMinX(0);
        contribution.getViewport().setMaxX(numOfShow-1);
        contribution.getViewport().setXAxisBoundsManual(true);
        contribution.removeAllSeries();
        contribution.addSeries(contributionSeries);
        contribution.getGridLabelRenderer().setNumHorizontalLabels(0); // only 4 because of the space


//        record.getViewport().setMinX(days[0].getTime());
//        record.getViewport().setMaxX(days[6].getTime());
        record.getViewport().setMinX(0);
        record.getViewport().setMaxX(numOfShow-1);
        record.getViewport().setXAxisBoundsManual(true);
        recordTrueSeries.setColor(Color.BLUE);
        recordWrongSeries.setColor(Color.RED);
        record.removeAllSeries();
        record.addSeries(recordTrueSeries);
        record.addSeries(recordWrongSeries);
        record.getGridLabelRenderer().setNumHorizontalLabels(0); // only 4 because of the space
    }




    private LineGraphSeries<DataPoint> getContributionSeries() {
        LineGraphSeries<DataPoint> result = new LineGraphSeries<DataPoint>();
        if(records.size() < 1) return result;

        for(int i = 0; i < numOfShow && -1 < records.size() - i -1; i++){
            //Date temp = records.get(records.size() - i).getDailyRecordDateTime_Date();
            result.appendData(new DataPoint(i, records.get(records.size() - i -1).getDailyRecordContribution()), true, numOfShow);
        }
        int min  = records.get(records.size() - 1).getDailyRecordContribution();
        int max;
        if(records.size() > numOfShow) {
            max = records.get(records.size() - numOfShow - 1).getDailyRecordContribution();
        }else{
            max = records.get(0).getDailyRecordContribution();
        }
        int mid = (min + max )/2;
        int gap = mid - min;
        gap *= 1.3;
        min = min - gap;
        max = max + gap;
        if(min < 0) min = 0;
        if(max <= min) max += 1;

        contribution.getViewport().setYAxisBoundsManual(true);
        contribution.getViewport().setMinY(min);
        contribution.getViewport().setMaxY(max);
        return result;
    }

    private BarGraphSeries<DataPoint> getRecordWrongSeries() {
        BarGraphSeries<DataPoint> result = new BarGraphSeries<DataPoint>();
        if(records.size() < 1) return result;

        for(int i = 0; i < numOfShow&& -1 < records.size() - i -1; i++) {
            //Date temp = records.get(i).getDailyRecordDateTime_Date();
            result.appendData(new DataPoint(i, records.get(records.size() - i - 1).getDailyRecordStudyWrong()), true, numOfShow);
        }


        return result;
    }
    private BarGraphSeries<DataPoint> getRecordTrueSeries() {
        BarGraphSeries<DataPoint> result = new BarGraphSeries<DataPoint>();
        if(records.size() < 1) return result;
        for(int i = 0; i < numOfShow&& -1 < records.size() - i -1; i++){
            //Date temp = records.get(records.size() - i).getDailyRecordDateTime_Date();
            result.appendData(new DataPoint(i, records.get(records.size() - i -1).getDailyRecordStudyRight()), true, numOfShow);

        }

        int min1  = records.get(records.size() - 1).getDailyRecordStudyWrong();
        int min2  = records.get(records.size() - 1).getDailyRecordStudyRight();
        int max1, max2 ;
        if(records.size() > numOfShow) {
            max2  = records.get(records.size() -numOfShow - 1).getDailyRecordStudyRight();
            max1  = records.get(records.size() -numOfShow - 1).getDailyRecordStudyWrong();
        }else{
            max2  = records.get(0).getDailyRecordStudyRight();
            max1  = records.get(0).getDailyRecordStudyWrong();
        }
        int min = min1 < min2 ? min1 : min2;
        int max = max1 > max2 ? max1 : max2;
        int mid = (min + max )/2;
        mid = (min + max )/2;
        int gap  = mid - min;
        gap *= 1.2;
        min = min - gap;
        max = max + gap;
        if(min < 0) min = 0;
        if(max <= min) max += 1;
        record.getViewport().setYAxisBoundsManual(true);
        record.getViewport().setMinY(min);
        record.getViewport().setMaxY(max);
        return result;
    }


    @Override
    public void onStart() {
        initWidgets(viewFragmentMyAchievement);
        initMyInfo();
        //initGraphData();
        invalidate();
        super.onStart();
    }




    private void initWidgets(View fragmentView) {
        myachievement_tv_submission = (TextView) fragmentView.findViewById(R.id.f_myachievement_tv_submission);
        myachievement_tv_contribution = (TextView) fragmentView.findViewById(R.id.f_myachievement_tv_contribution);
        myachievement_tv_quality = (TextView) fragmentView.findViewById(R.id.f_myachievement_tv_quality);
        myachievement_tv_correctness = (TextView) fragmentView.findViewById(R.id.f_myachievement_tv_correctness);
        myachievement_tv_regmessage = (TextView) fragmentView.findViewById(R.id.f_myachievement_tv_regmessage);

    }

    private void initMyInfo() {
        MyInfo tmpMyInfo;
        if (MainActivity.odnqDB != null) {
            tmpMyInfo = MainActivity.odnqDB.getMyInfo();


            if (tmpMyInfo == null) {
                myachievement_tv_submission.setText("-");
                myachievement_tv_contribution.setText("-");
                myachievement_tv_quality.setText("-");
                myachievement_tv_correctness.setText("-");

                myachievement_tv_regmessage.setVisibility(View.VISIBLE);
            } else {
                float tmpMyCorrectness = (float) tmpMyInfo.getMyInfoAnswerRight() / (float) (tmpMyInfo.getMyInfoAnswerRight() + tmpMyInfo.getMyInfoAnswerWrong());
                float tmpMyCorrectnessPerc = tmpMyCorrectness * 100;

                String strQualityPoint = String.format("%.2f", tmpMyInfo.getMyInfoQuality());
                String strCorrectness = String.format("%.1f", tmpMyCorrectnessPerc);

                myachievement_tv_submission.setText(tmpMyInfo.getMyInfoCardNum() + "");
                myachievement_tv_contribution.setText(tmpMyInfo.getMyInfoExp() + "");
                myachievement_tv_quality.setText(strQualityPoint);
                myachievement_tv_correctness.setText(strCorrectness+"%");

                myachievement_tv_regmessage.setVisibility(View.GONE);

            }
        }

    }

    @Override
    public void onResume() {
        initMyInfo();
        initGraphData();
        invalidate();

        super.onResume();
    }



    public void refresh() {
        //yout code in refresh.
        initMyInfo();
        initGraphData();
        Log.i("Refresh", "YES");
    }

    public void invalidate() {
        waitWhileDBupdate = new CountDownTimer(2000,500) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(MainActivity.odnqDB != null) {
                    MyInfo tmpMyInfo = MainActivity.odnqDB.getMyInfo();
                    if (tmpMyInfo != null && myachievement_tv_contribution != null) {
                        if (myachievement_tv_contribution.getText().toString().compareTo(tmpMyInfo.getMyInfoExp() + "") != 0) {
                            viewFragmentMyAchievement.post(new Runnable() {
                                @Override
                                public void run() {
                                    refresh();
                                    viewFragmentMyAchievement.invalidate();
                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }

}
