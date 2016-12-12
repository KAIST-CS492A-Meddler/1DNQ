package com.example.user.onedaynquestions.view.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.onedaynquestions.R;
import com.example.user.onedaynquestions.archive.MyHereAgent;
import com.example.user.onedaynquestions.archive.MyRoutine;
import com.example.user.onedaynquestions.controller.QuestionListAdapter;
import com.example.user.onedaynquestions.model.MyCard;
import com.example.user.onedaynquestions.model.MyInfo;
import com.example.user.onedaynquestions.model.UnitRecord;
import com.example.user.onedaynquestions.view.activity.MainActivity;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by ymbae on 2016-04-18.
 */
public class MyAchievementFragment extends Fragment{

    MyReceiver r;

    public List<Goal> goals;
    ListViewAdapter listViewAdapter;

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
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        goals = new ArrayList<Goal>();



//        listViewAdapter = new ListViewAdapter();
        viewFragmentMyAchievement = inflater.inflate(R.layout.fragment_myachievement, container, false);

        contribution = (GraphView)viewFragmentMyAchievement.findViewById(R.id.weekly_contribution_gv);
        record = (GraphView)viewFragmentMyAchievement.findViewById(R.id.weekly_record_gv);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -numOfShow);
        days = new Date[numOfShow];
        for(int i =0; i  < numOfShow; i++){
            days[i] = calendar.getTime();
            calendar.add(Calendar.DATE, 1);
        }

        calendar.add(Calendar.DATE, -20);
        Date[] daysTemp = new Date[20];
        for(int i =0; i  < 20; i++){
            daysTemp[i] = calendar.getTime();
            calendar.add(Calendar.DATE, 1);
        }

        records = new ArrayList<>();
        //public UnitRecord(Date dailyRecordDateTime, int dailyRecordContribution, int dailyRecordStudyRight, int dailyRecordStudyWrong) {
        records.add(new UnitRecord(daysTemp[0],5, 4,2 ));
        records.add(new UnitRecord(daysTemp[1],6, 6,2 ));
        records.add(new UnitRecord(daysTemp[2],15, 3,22 ));
        records.add(new UnitRecord(daysTemp[3],25, 34,2 ));
        records.add(new UnitRecord(daysTemp[4],15, 24,3 ));
        records.add(new UnitRecord(daysTemp[5],45, 45,6 ));
        records.add(new UnitRecord(daysTemp[6],56, 14,8 ));
        records.add(new UnitRecord(daysTemp[7],50, 44,15 ));
        records.add(new UnitRecord(daysTemp[8],67, 24,10 ));
        records.add(new UnitRecord(daysTemp[9],78, 42,25 ));
        records.add(new UnitRecord(daysTemp[10],90, 43,22 ));
        records.add(new UnitRecord(daysTemp[11],105, 44,12 ));
        records.add(new UnitRecord(daysTemp[12],145, 44,6 ));
        records.add(new UnitRecord(daysTemp[14],205, 34,12 ));
        records.add(new UnitRecord(daysTemp[15],255, 34,4 ));
        records.add(new UnitRecord(daysTemp[17],405, 48,2 ));
        records.add(new UnitRecord(daysTemp[18],565, 25,4 ));
        records.add(new UnitRecord("2016-12-12 12:03:22",500, 34,7 ));

        Date tempDateRecord;
        lastIndexOfRecords = records.size();
        do{
            if(lastIndexOfRecords < 1)  break;
            lastIndexOfRecords--;
            tempDateRecord = records.get(lastIndexOfRecords).getDailyRecordDateTime_Date();
        }while(days[0].before(tempDateRecord));


        LineGraphSeries<DataPoint> contributionSeries = getContributionSeries();

        BarGraphSeries<DataPoint> recordWrongSeries = getRecordWrongSeries();
        BarGraphSeries<DataPoint> recordTrueSeries = getRecordTrueSeries();

        contribution.getViewport().setMinX(days[0].getTime());
        contribution.getViewport().setMaxX(days[6].getTime());
        contribution.getViewport().setXAxisBoundsManual(true);
        contribution.addSeries(contributionSeries);
        contribution.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        contribution.getGridLabelRenderer().setNumHorizontalLabels(0); // only 4 because of the space


        record.getViewport().setMinX(days[0].getTime());
        record.getViewport().setMaxX(days[6].getTime());
        record.getViewport().setXAxisBoundsManual(true);
        recordTrueSeries.setColor(Color.BLUE);
        recordWrongSeries.setColor(Color.RED);
        record.addSeries(recordTrueSeries);
        record.addSeries(recordWrongSeries);
        record.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        record.getGridLabelRenderer().setNumHorizontalLabels(0); // only 4 because of the space

//
//        initWidgets(viewFragmentMyAchievement);
//
//        ListView listView = (ListView) viewFragmentMyAchievement.findViewById(R.id.routine_list);
//        listView.setAdapter(listViewAdapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                List<MyRoutine> routines = listViewAdapter.getRoutine();
//                TextView routineName = (TextView) viewFragmentMyAchievement.findViewById(R.id.selected_routine_name);
//                boolean isRoutineOk = true;
//                routineName.setText("Selected routine: " + routines.get(position).getRoutineName());
//
//                goals.clear();
//
//                TextView routineGoal = (TextView) viewFragmentMyAchievement.findViewById(R.id.selected_routine_goal1);
//                TextView routineEquip = (TextView) viewFragmentMyAchievement.findViewById(R.id.selected_routine_equip1);
//                ImageView equipImage = (ImageView) viewFragmentMyAchievement.findViewById(R.id.routine_equipment1_image);
//                LinearLayout equipment1inList = (LinearLayout) viewFragmentMyAchievement.findViewById(R.id.routine_equipment1);
//                TextView arrowOfEquipment1 = (TextView) viewFragmentMyAchievement.findViewById(R.id.routine_equipment1_arrow);
//
//                TextView routineGoal2 = (TextView) viewFragmentMyAchievement.findViewById(R.id.selected_routine_goal2);
//                TextView routineEquip2 = (TextView) viewFragmentMyAchievement.findViewById(R.id.selected_routine_equip2);
//                ImageView equipImage2 = (ImageView) viewFragmentMyAchievement.findViewById(R.id.routine_equipment2_image);
//                LinearLayout equipment2inList = (LinearLayout) viewFragmentMyAchievement.findViewById(R.id.routine_equipment2);
//                TextView arrowOfEquipment2 = (TextView) viewFragmentMyAchievement.findViewById(R.id.routine_equipment2_arrow);
//
//                TextView routineGoal3 = (TextView) viewFragmentMyAchievement.findViewById(R.id.selected_routine_goal3);
//                TextView routineEquip3 = (TextView) viewFragmentMyAchievement.findViewById(R.id.selected_routine_equip3);
//                ImageView equipImage3 = (ImageView) viewFragmentMyAchievement.findViewById(R.id.routine_equipment3_image);
//                LinearLayout equipment3inList = (LinearLayout) viewFragmentMyAchievement.findViewById(R.id.routine_equipment3);
//                TextView arrowOfEquipment3 = (TextView) viewFragmentMyAchievement.findViewById(R.id.routine_equipment3_arrow);
//
//                TextView routineGoal4 = (TextView) viewFragmentMyAchievement.findViewById(R.id.selected_routine_goal4);
//                TextView routineEquip4 = (TextView) viewFragmentMyAchievement.findViewById(R.id.selected_routine_equip4);
//                ImageView equipImage4 = (ImageView) viewFragmentMyAchievement.findViewById(R.id.routine_equipment4_image);
//                LinearLayout equipment4inList = (LinearLayout) viewFragmentMyAchievement.findViewById(R.id.routine_equipment4);
//                TextView arrowOfEquipment4 = (TextView) viewFragmentMyAchievement.findViewById(R.id.routine_equipment4_arrow);
//
//                TextView routineGoal5 = (TextView) viewFragmentMyAchievement.findViewById(R.id.selected_routine_goal5);
//                TextView routineEquip5 = (TextView) viewFragmentMyAchievement.findViewById(R.id.selected_routine_equip5);
//                ImageView equipImage5 = (ImageView) viewFragmentMyAchievement.findViewById(R.id.routine_equipment5_image);
//                LinearLayout equipment5inList = (LinearLayout) viewFragmentMyAchievement.findViewById(R.id.routine_equipment5);
//
//                btn_refresh = (Button) viewFragmentMyAchievement.findViewById(R.id.routine_btn_refresh);
//                btn_refresh.setBackgroundResource(R.drawable.effect_refresh_press);
//
//                btn_refresh.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        listViewAdapter.notifyDataSetChanged();
//                        Toast.makeText(viewFragmentMyAchievement.getContext(), "List is updated", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//
//                if (!routines.get(position).getRoutineEq1Id().equals("-1")) {
//                    routineGoal.setText(goalParser(routines.get(position).getRoutineEq1Goal()));
//                    MyHereAgent agent = MainActivity.hereDB.getMyHereAgent(routines.get(position).getRoutineEq1Id());
//                    if (agent !=null) {
//                        routineEquip.setText(agent.getMyeqName());
//                        equipImage.setImageResource(findImage(agent.getMyeqType()));
//                        equipment1inList.setVisibility(View.VISIBLE);
//                    } else {
//                        isRoutineOk = false;
//                    }
//                } else {
//                    equipment1inList.setVisibility(View.GONE);
//                }
//
//                if (!routines.get(position).getRoutineEq2Id().equals("-1")) {
//                    routineGoal2.setText(goalParser(routines.get(position).getRoutineEq2Goal()));
//                    MyHereAgent agent = MainActivity.hereDB.getMyHereAgent(routines.get(position).getRoutineEq2Id());
//                    if(agent != null) {
//                        routineEquip2.setText(agent.getMyeqName());
//                        equipImage2.setImageResource(findImage(agent.getMyeqType()));
//                        arrowOfEquipment1.setVisibility(View.VISIBLE);
//                        equipment2inList.setVisibility(View.VISIBLE);
//                    } else {
//                        isRoutineOk = false;
//                    }
//                } else {
//                    equipment2inList.setVisibility(View.GONE);
//                    arrowOfEquipment1.setVisibility(View.GONE);
//
//                }
//
//                if (!routines.get(position).getRoutineEq3Id().equals("-1")) {
//                    routineGoal3.setText(goalParser(routines.get(position).getRoutineEq3Goal()));
//                    MyHereAgent agent = MainActivity.hereDB.getMyHereAgent(routines.get(position).getRoutineEq3Id());
//                    if(agent != null) {
//                        routineEquip3.setText(agent.getMyeqName());
//                        equipImage3.setImageResource(findImage(agent.getMyeqType()));
//                        arrowOfEquipment2.setVisibility(View.VISIBLE);
//                        equipment3inList.setVisibility(View.VISIBLE);
//                    } else {
//                        isRoutineOk = false;
//                    }
//                } else {
//                    equipment3inList.setVisibility(View.GONE);
//                    arrowOfEquipment2.setVisibility(View.GONE);
//                }
//
//                if (!routines.get(position).getRoutineEq4Id().equals("-1")) {
//                    routineGoal4.setText(goalParser(routines.get(position).getRoutineEq4Goal()));
//                    MyHereAgent agent = MainActivity.hereDB.getMyHereAgent(routines.get(position).getRoutineEq4Id());
//                    if(isRoutineOk) {
//                        routineEquip4.setText(agent.getMyeqName());
//                        equipImage4.setImageResource(findImage(agent.getMyeqType()));
//                        arrowOfEquipment3.setVisibility(View.VISIBLE);
//                        equipment4inList.setVisibility(View.VISIBLE);
//                    } else {
//                        isRoutineOk = false;
//                    }
//                } else {
//                    equipment4inList.setVisibility(View.GONE);
//                    arrowOfEquipment3.setVisibility(View.GONE);
//
//                }
//
//                if (!routines.get(position).getRoutineEq5Id().equals("-1")) {
//                    routineGoal5.setText(goalParser(routines.get(position).getRoutineEq5Goal()));
//                    MyHereAgent agent = MainActivity.hereDB.getMyHereAgent(routines.get(position).getRoutineEq5Id());
//                    if(agent != null) {
//                        routineEquip5.setText(agent.getMyeqName());
//                        equipImage5.setImageResource(findImage(agent.getMyeqType()));
//                        arrowOfEquipment4.setVisibility(View.VISIBLE);
//                        equipment5inList.setVisibility(View.VISIBLE);
//                    } else {
//                        isRoutineOk = false;
//                    }
//                } else {
//                    equipment5inList.setVisibility(View.GONE);
//                    arrowOfEquipment4.setVisibility(View.GONE);
//                }
//
//                if(isRoutineOk){
//                    MainActivity.mySelectedRoutine = routines.get(position);
//
//                    tv_noroutine.setVisibility(View.GONE);
//                    horizontalScrollView.setVisibility(View.VISIBLE);
//                }
//            }
//        });
//        listViewAdapter.notifyDataSetChanged();

        initWidgets(viewFragmentMyAchievement);
        initMyInfo();

        return viewFragmentMyAchievement;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }


    private LineGraphSeries<DataPoint> getContributionSeries() {
        LineGraphSeries<DataPoint> result = new LineGraphSeries<DataPoint>();
        for(int i = lastIndexOfRecords; i < records.size(); i++){
            Date temp = records.get(i).getDailyRecordDateTime_Date();
            result.appendData(new DataPoint(temp, records.get(i).getDailyRecordContribution()), true, numOfShow);
        }
        return result;
    }

    private BarGraphSeries<DataPoint> getRecordWrongSeries() {

        BarGraphSeries<DataPoint> result = new BarGraphSeries<DataPoint>();
        for(int i = lastIndexOfRecords; i < records.size(); i++){
            Date temp = records.get(i).getDailyRecordDateTime_Date();
            result.appendData(new DataPoint(records.get(i).getDailyRecordDateTime_Date(),records.get(i).getDailyRecordStudyWrong()), true, numOfShow);

        }
        return result;
    }
    private BarGraphSeries<DataPoint> getRecordTrueSeries() {

        BarGraphSeries<DataPoint> result = new BarGraphSeries<DataPoint>();
        for(int i = lastIndexOfRecords; i < records.size(); i++){
            Date temp = records.get(i).getDailyRecordDateTime_Date();
            result.appendData(new DataPoint(records.get(i).getDailyRecordDateTime_Date(),records.get(i).getDailyRecordStudyRight()), true, numOfShow);

        }
        return result;
    }


    @Override
    public void onStart() {
        initWidgets(viewFragmentMyAchievement);
        initMyInfo();
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
        MyInfo tmpMyInfo = MainActivity.odnqDB.getMyInfo();

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

    @Override
    public void onResume() {
        initMyInfo();

        super.onResume();

        r = new MyReceiver();
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(r, new IntentFilter("TAG_REFRESH"));
//        //if(MainActivity.hereDB.getAllMyRoutines() !=null) {
//            listViewAdapter.setRoutine(MainActivity.hereDB.getAllMyRoutines());
////        } else {
////            myRoutines.clear();
////        }
//        listViewAdapter.notifyDataSetChanged();
    }

    public int findImage (int type){
        switch (type) {
            case 1:
                return R.drawable.eq_01_dumbbell;
            case 2:
                return R.drawable.eq_02_pushupbar;
            case 3:
                return R.drawable.eq_03_jumprope;
            case 4:
                return R.drawable.eq_04_hoolahoop;
            case 5:
                return R.mipmap.ic_setting_user_information;
        }
        return 0;
    }

    public String goalParser(String goal){
        StringTokenizer st = new StringTokenizer(goal,"|");
        String token = "";
        Goal exerciseGoal = new Goal();
        String text = "";
        while(st.hasMoreElements()){
            token = st.nextToken();
            exerciseGoal.set = token;
            token = st.nextToken();
            if(token.equals("-1")){
                exerciseGoal.number = null;
            } else {
                exerciseGoal.number = token;
            }
            token = st.nextToken();
            if(token.equals("-1")){
                exerciseGoal.time = null;
            } else {
                exerciseGoal.time = token;
            }
        }
        goals.add(exerciseGoal);

        if(exerciseGoal.number!=null)
            text = text + exerciseGoal.number + " X ";
        if(exerciseGoal.time !=null)
            text = text + exerciseGoal.time + "s X ";
        if(exerciseGoal.set != null)
            text = text +exerciseGoal.set + " SETS";

        return text;
    }

    public class Goal{
        String set;
        String number;
        String time;
    }

    private class ListViewAdapter extends BaseAdapter {
        private LayoutInflater mInflator;
        private List<MyRoutine> myRoutines = new ArrayList<MyRoutine>();

        public List<MyRoutine> getRoutine(){
            return myRoutines;
        }

        public void setRoutine(List<MyRoutine> routines){
            this.myRoutines = routines;
        }

        public ListViewAdapter() {
            super();
            if(MainActivity.hereDB.getAllMyRoutines() !=null) {
                myRoutines = MainActivity.hereDB.getAllMyRoutines();
            } else {
                myRoutines.clear();
            }

            mInflator = getActivity().getLayoutInflater();
        }

        @Override
        public int getCount() {
            if (myRoutines != null)
                return myRoutines.size();
            else
                return 0;
        }

        @Override
        public Object getItem(int i) {
            return myRoutines.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            // General ListView optimization code.
            if (view == null) {
                int res = 0;
                res = R.layout.listitem_routine;
                view = mInflator.inflate(res, viewGroup, false);

            }

            ImageView routineImage = (ImageView) view.findViewById(R.id.equiplist_img);
            TextView routineName = (TextView) view.findViewById(R.id.equiplist_name);
            TextView routineId = (TextView) view.findViewById(R.id.equiplist_id);
            TextView routineSummary = (TextView) view.findViewById(R.id.equiplist_sensorid);

//            switch (pairedEquipList.get(i).getEquipmentType()) {
//                case 0:
//                    eqTypeImage.setImageResource(R.mipmap.ic_setting_update_alarm);
//                    break;
//                case 1:
//                    eqTypeImage.setImageResource(R.mipmap.ic_setting_best_interest);
//                    break;
//                case 2:
//                    eqTypeImage.setImageResource(R.mipmap.ic_setting_user_information);
//                    break;
//                case 3:
//                    break;
//                default:
//                    break;
//            }

            routineImage.setImageResource(R.drawable.list_routine_icon);

            routineName.setText(myRoutines.get(i).getRoutineId());
            routineId.setText(myRoutines.get(i).getRoutineName());
            String summary = "";
            MyHereAgent myHereAgent;
            if(!myRoutines.get(i).getRoutineEq1Id().equals("-1")) {
                myHereAgent = MainActivity.hereDB.getMyHereAgent(myRoutines.get(i).getRoutineEq1Id());
                if(myHereAgent !=null)
                    summary += myHereAgent.getMyeqName();
            }
            if(!myRoutines.get(i).getRoutineEq2Id().equals("-1")) {
                myHereAgent =  MainActivity.hereDB.getMyHereAgent(myRoutines.get(i).getRoutineEq2Id());
                if(myHereAgent != null)
                    summary += " - " + myHereAgent.getMyeqName();
            }
            if(!myRoutines.get(i).getRoutineEq3Id().equals("-1")) {
                myHereAgent = MainActivity.hereDB.getMyHereAgent(myRoutines.get(i).getRoutineEq3Id());
                if(myHereAgent != null)
                    summary += " - " + myHereAgent.getMyeqName();
            }
            if(!myRoutines.get(i).getRoutineEq4Id().equals("-1")) {
                myHereAgent = MainActivity.hereDB.getMyHereAgent(myRoutines.get(i).getRoutineEq4Id());
                if(myHereAgent != null)
                    summary += " - " + myHereAgent.getMyeqName();
            }
            if(!myRoutines.get(i).getRoutineEq5Id().equals("-1")) {
                myHereAgent =  MainActivity.hereDB.getMyHereAgent(myRoutines.get(i).getRoutineEq5Id());
                if (myHereAgent != null)
                    summary += " - " + myHereAgent.getMyeqName();
            }

            routineSummary.setText(summary);

            return view;
        }
    }

    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            MyAchievementFragment.this.refresh();
        }
    }

    public void refresh() {
        //yout code in refresh.
        Log.i("Refresh", "YES");
    }

}
