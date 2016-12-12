package com.example.user.onedaynquestions.view.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.onedaynquestions.R;
import com.example.user.onedaynquestions.controller.CardAdapter;
import com.example.user.onedaynquestions.model.MyCard;
import com.example.user.onedaynquestions.service.WakefulPushReceiver;
import com.example.user.onedaynquestions.view.activity.MainActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by ymbae on 2016-04-18.
 */
public class StudyNoteFragment extends Fragment{

    private static final String TAG_RECORD_DATA = "StudyNoteFragment";

    private View currentView;

//    private GraphView gv;
//
//    private HERE_DeviceListAdapter equipListAdapter;
//    private ListView lvEquipList;
//    private ArrayList<MyHereAgent> equipmentList;
//
//    private ImageView iv_character_img;
//    private TextView tv_nick_name;
//    private TextView tv_msg;
//    private Button btn_seemore;
//
//    private ArrayList<MyRecord> arrMyRecords;
//    private ArrayList<RecordForGraph> arrGraphElements;
//    //private ArrayList<Double> dailyCalories;
//    private ArrayList<RecordDateCalorie> arrDateCalorie;
//
//    HashMap<String, List<RecordEqDateDone>> mapGroupByEq;
//    ArrayList<String> agentIDs;

    private Context inflatorContext;
    public static final int FREQUENTLY_WRONG = 0;
    public static final int STARRED = 1;
    public static final int RECOMMENDED = 2;

    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<MyCard> frequentlyWrongQuestionList, starredQuestionList,recommendedQuestionList;

    private CardAdapter frequentlyWrongQuestionListAdapter,starredQuestionListAdapter,recommendedQuestionListAdapter;
    private RecyclerView frequentlyWrongList, starredList, recommendList;

    private float dist;
    private int prevX, prevY;
    private final float distThreshold = 100;
    public CountDownTimer cdt;
    private View scaledView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        currentView = inflater.inflate(R.layout.fragment_mystudynote, container, false);
        /**
         * Youngmin
         */
        //Initialize widgets
        initWidgets(currentView);


        initQuestionList();
        initCards();

        cdt = new CountDownTimer(5000, 100) {
            @Override
            public void onTick(long l) {
                if(WakefulPushReceiver.updated){
                    cdt.cancel();
                    resetAllList();
                }
            }

            @Override
            public void onFinish() {

            }
        };

        return currentView;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void initCards() {
        // opt1: get all cards
        // opt2: get cards without mine
        // opt3: get only my cards
        // opt4: get starred cards
        // opt5: get wrong cards
        // opt6: get recommended cards
        String myInfoId = MainActivity.odnqDB.getMyInfo().getMyInfoId();

        Log.d("AppendCardList", "MyCard table size: " + MainActivity.odnqDB.countTableMyCard());

        ArrayList<MyCard> allCardList = new ArrayList<MyCard>();
        ArrayList<MyCard> starredCardList = new ArrayList<MyCard>();
        ArrayList<MyCard> wrongCardList = new ArrayList<MyCard>();
        ArrayList<MyCard> recommededCardList = new ArrayList<MyCard>();

        allCardList = MainActivity.odnqDB.getMyCards(1, myInfoId);
        if (allCardList != null) Log.d("AppendCardList", "allCardList - size: " + allCardList.size());

        // STARRED CARD
        starredCardList = MainActivity.odnqDB.getMyCards(4, myInfoId);
        if (starredCardList != null){
            Log.d("AppendCardList", "starredCardList - size: " + starredCardList.size());
            for (int i = 0; i < starredCardList.size(); i++) {
                appendQuestion(STARRED, starredCardList.get(i));
            }

        }

        // WRONG CARD
        wrongCardList = MainActivity.odnqDB.getMyCards(5, myInfoId);
        if (wrongCardList != null){
            Log.d("AppendCardList", "wrongCardList - size: " + wrongCardList.size());
            for (int i = 0; i < wrongCardList.size(); i++) {
                appendQuestion(FREQUENTLY_WRONG, wrongCardList.get(i));
            }
        }

        // RECOMMENDED CARD
        recommededCardList = MainActivity.odnqDB.getMyCards(6, myInfoId);
        if (recommededCardList != null) {
            Log.d("AppendCardList", "recommededCardList - size: " + recommededCardList.size());
            for (int i = 0; i < recommededCardList.size(); i++) {
                appendQuestion(RECOMMENDED, recommededCardList.get(i));
            }
        }
    }


    public void initQuestionList(){
        mLayoutManager = new LinearLayoutManager(currentView.getContext(), LinearLayoutManager.HORIZONTAL, false);


        starredList = (RecyclerView) currentView.findViewById(R.id.starred_question_lv);
        starredList.setLayoutManager(mLayoutManager);
        starredList.setHasFixedSize(true);
        starredQuestionList = new ArrayList<>();
        starredQuestionListAdapter = new CardAdapter(starredQuestionList);
        starredList.setAdapter(starredQuestionListAdapter);
        starredList.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                switch (e.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        dist = 0;
                        prevX = (int)e.getX();
                        prevY = (int)e.getY();

                        break;
                    case MotionEvent.ACTION_MOVE:
                        int tempX = prevX - (int)e.getX();
                        int tempY = prevY - (int)e.getY();
                        dist += Math.sqrt(tempX * tempX + tempY * tempY);
                        prevX = (int)e.getX();
                        prevY = (int)e.getY();

                        break;
                    case MotionEvent.ACTION_UP:
                        if(dist < distThreshold) {
                            int id = frequentlyWrongList.getChildAdapterPosition(frequentlyWrongList.findChildViewUnder(e.getX(), e.getY()));
                            if (id > -1) {
                                startActivity(new Intent(frequentlyWrongQuestionList.get(id).getCardSolvingIntent(getActivity().getBaseContext())));

                            }
                        }

                        break;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });




        mLayoutManager = new LinearLayoutManager(currentView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        frequentlyWrongList = (RecyclerView) currentView.findViewById(R.id.frequently_wrong_question_lv);
        frequentlyWrongList.setLayoutManager(mLayoutManager);
        frequentlyWrongList.setHasFixedSize(true);
        frequentlyWrongQuestionList = new ArrayList<>();
        frequentlyWrongQuestionListAdapter = new CardAdapter(frequentlyWrongQuestionList);
        frequentlyWrongList.setAdapter(frequentlyWrongQuestionListAdapter);
        frequentlyWrongList.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                switch (e.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        dist = 0;
                        prevX = (int)e.getX();
                        prevY = (int)e.getY();

                        break;
                    case MotionEvent.ACTION_MOVE:
                        int tempX = prevX - (int)e.getX();
                        int tempY = prevY - (int)e.getY();
                        dist += Math.sqrt(tempX * tempX + tempY * tempY);
                        prevX = (int)e.getX();
                        prevY = (int)e.getY();

                        break;
                    case MotionEvent.ACTION_UP:
                        if(dist < distThreshold) {
                            int id = frequentlyWrongList.getChildAdapterPosition(frequentlyWrongList.findChildViewUnder(e.getX(), e.getY()));
                            if (id > -1) {
                                startActivity(new Intent(frequentlyWrongQuestionList.get(id).getCardSolvingIntent(getActivity().getBaseContext())));
                            }
                        }

                        break;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });


        mLayoutManager = new LinearLayoutManager(currentView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recommendList = (RecyclerView) currentView.findViewById(R.id.recommended_question_lv);
        recommendList.setLayoutManager(mLayoutManager);
        recommendList.setHasFixedSize(true);
        recommendedQuestionList = new ArrayList<>();
        recommendedQuestionListAdapter = new CardAdapter(recommendedQuestionList);
        recommendList.setAdapter(recommendedQuestionListAdapter);
        recommendList.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                switch (e.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        dist = 0;
                        prevX = (int)e.getX();
                        prevY = (int)e.getY();

                        break;
                    case MotionEvent.ACTION_MOVE:
                        int tempX = prevX - (int)e.getX();
                        int tempY = prevY - (int)e.getY();
                        dist += Math.sqrt(tempX * tempX + tempY * tempY);
                        prevX = (int)e.getX();
                        prevY = (int)e.getY();

                        break;
                    case MotionEvent.ACTION_UP:
                        if(dist < distThreshold) {
                            int id = recommendList.getChildAdapterPosition(recommendList.findChildViewUnder(e.getX(), e.getY()));
                            if (id > -1) {
                                startActivity(new Intent(recommendedQuestionList.get(id).getCardSolvingIntent(getActivity().getBaseContext())));
                            }
                        }

                        break;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });



        if(WakefulPushReceiver.getAllRecommendedQuestions().size() > 0) {
            setQuestions(RECOMMENDED, WakefulPushReceiver.getAllRecommendedQuestions());
        }
    }

    public void resetAllList(){
        if(WakefulPushReceiver.getAllRecommendedQuestions().size() > 0) {
            setQuestions(RECOMMENDED, WakefulPushReceiver.getAllRecommendedQuestions());
        }
    }

    public boolean setQuestion(int where, int position, MyCard question){
        switch (where){
            case STARRED:
                starredQuestionList.add(position, question);
                break;
            case FREQUENTLY_WRONG:
                frequentlyWrongQuestionList.add(position, question);
                break;
            case RECOMMENDED:
                recommendedQuestionList.add(position, question);
                break;
            default:
                return false;
        }
        currentView.postInvalidate();
        return true;
    };

    public boolean appendQuestion(int where, MyCard question){
        switch (where){
            case STARRED:
                starredQuestionList.add( question);
                starredQuestionListAdapter.notifyDataSetChanged();
                break;
            case FREQUENTLY_WRONG:
                frequentlyWrongQuestionList.add( question);
                frequentlyWrongQuestionListAdapter.notifyDataSetChanged();
                break;
            case RECOMMENDED:
                recommendedQuestionList.add( question);
                recommendedQuestionListAdapter.notifyDataSetChanged();
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    };

    public boolean deleteQuestion(int where, int position){
        switch (where){
            case STARRED:
                starredQuestionList.remove(position);
                starredQuestionListAdapter.notifyDataSetChanged();
                break;
            case FREQUENTLY_WRONG:
                frequentlyWrongQuestionList.remove(position);
                frequentlyWrongQuestionListAdapter.notifyDataSetChanged();
                break;
            case RECOMMENDED:
                recommendedQuestionList.remove(position);
                recommendedQuestionListAdapter.notifyDataSetChanged();
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    };


    public boolean setQuestions(int where, ArrayList<MyCard> list){
        switch (where){
            case STARRED:
                starredQuestionList.clear();
                starredQuestionList.addAll(list);
                starredQuestionListAdapter.notifyDataSetChanged();
                break;
            case FREQUENTLY_WRONG:
                frequentlyWrongQuestionList.clear();
                frequentlyWrongQuestionList.addAll(list);
                frequentlyWrongQuestionListAdapter.notifyDataSetChanged();
                break;
            case RECOMMENDED:
                recommendedQuestionList.clear();
                recommendedQuestionList.addAll(list);
                recommendedQuestionListAdapter.notifyDataSetChanged();
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    };

    public void invalidate() {
        currentView.post(new Runnable() {
            @Override
            public void run() {
                currentView.invalidate();
            }
        });
    }
    @Override
    public void onResume() {
        initArrayListFromDB();
        initDailyRecord();
        drawDailyCalorieGraph(getView());
        initEquipRecord();
        initQuestionList();
        initCards();

//        equipListAdapter.notifyDataSetChanged();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * Check if
     */
    private void checkPrizeAchievement() {

    }


    private void drawDailyCalorieGraph(View fragmentView) {

//        //Calendar for date
//        Calendar calendar = Calendar.getInstance();
////        if(arrDateCalorie.size() >0 ) {
////            Log.d("arrDateCalorie", "arrDateCalorie-gap(0): " + arrDateCalorie.get(0).getDaysGap());
////            Log.d("arrDateCalorie", "arrDateCalorie-gap(1): " + arrDateCalorie.get(1).getDaysGap());
////            Log.d("arrDateCalorie", "arrDateCalorie-gap(2): " + arrDateCalorie.get(2).getDaysGap());
////        }
//
//        //If we redraw, we first remove all series
//        gv.removeAllSeries();
//
//        //Make data points for a recent 1 week
//        DataPoint[] dataPoints = new DataPoint[7];
//
//        //Initialize datapoints
//        for (int i = 0; i < 7; i++) {
//            DataPoint tmpDataPoint = new DataPoint(i + 1, 0.0);
//            dataPoints[i] = tmpDataPoint;
//        }
//
//
//        for (int i = 0; i < 7; i++) {
//            int ptRecord = -1;
//
//            for (int j = 0; j < arrDateCalorie.size(); j++) {
//                if (arrDateCalorie.get(j).getDaysGap() == (6 - i)) {
//                    ptRecord = j;
//                }
//            }
//
//            if (ptRecord != -1) {
//                DataPoint tmpDataPoint = new DataPoint(i + 1, arrDateCalorie.get(ptRecord).getRecordCalorie());
//                dataPoints[i] = tmpDataPoint;
//            } else {
//                DataPoint tmpDataPoint = new DataPoint(i + 1, 0.0);
//                dataPoints[i] = tmpDataPoint;
//            }
//        }
////
////        //There is a record more than one week ago
////        if (arrDateCalorie.get(0).getDaysGap() > 6) {
////
////            for (int i = 0; i < arrDateCalorie.size(); i++) {
////
////                //boolean isHere = false;
////                int theDay = -1;
////
////                for (int j = 0; j < 7; j++) {
////                    if (arrDateCalorie.get(i).getDaysGap() == j) {
////                        theDay = j;
////                    }
////                }
////
////                //If there is the date
////                if (theDay == -1) {
////                    DataPoint tmpDataPoint = new DataPoint(i + 1, arrDateCalorie.get(i).getRecordCalorie());
////                } else {
////
////                }
////
////                //Only for recent 1 week
////                if (arrDateCalorie.get(i).getDaysGap() < 7) {
////                    DataPoint tmpDataPoint = new DataPoint(i + 1, arrDateCalorie.get(i).getRecordCalorie());
////                    dataPoints[6 - arrDateCalorie.get(i).getDaysGap()] = tmpDataPoint;
////                }
////            }
////
////        }
////        //There are only records in this recent week
////        else {
////            for (int i = 0; i < arrDateCalorie.size(); i++) {
////                //Only for recent 1 week
////                if (arrDateCalorie.get(i).getDaysGap() < 7) {
////                    DataPoint tmpDataPoint = new DataPoint(i + 1, arrDateCalorie.get(i).getRecordCalorie());
////                    dataPoints[6-arrDateCalorie.get(i).getDaysGap()] = tmpDataPoint;
////                }
////            }
////        }
//
//        for (int i = 0; i < 7; i++) {
//            Log.d("DataPoints", "[" + i + "] " + dataPoints[i].getX() + "/" + dataPoints[i].getY());
//        }
//
//
//        BarGraphSeries<DataPoint> calorieSeries = new BarGraphSeries<DataPoint>(dataPoints);
//
//        gv.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
//            @Override
//            public String formatLabel(double value, boolean isValueX) {
//                if (isValueX) {
//                    return super.formatLabel(value, isValueX);
//                } else {
//                    return super.formatLabel(value, isValueX) + "kcal";
//                }
//            }
//        });
//
//        //Initialize StaticLabelsFormatter
//        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(gv);
//        staticLabelsFormatter.setHorizontalLabels(new String[]{
//                getDay(calendar.get(Calendar.DAY_OF_WEEK) - 6),
//                getDay(calendar.get(Calendar.DAY_OF_WEEK) - 5),
//                getDay(calendar.get(Calendar.DAY_OF_WEEK) - 4),
//                getDay(calendar.get(Calendar.DAY_OF_WEEK) - 3),
//                getDay(calendar.get(Calendar.DAY_OF_WEEK) - 2),
//                getDay(calendar.get(Calendar.DAY_OF_WEEK) - 1),
//                getDay(calendar.get(Calendar.DAY_OF_WEEK))});
//        gv.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
//
//
//        calorieSeries.setSpacing(50);
//        calorieSeries.setDrawValuesOnTop(true);
//        calorieSeries.setValuesOnTopColor(Color.BLUE);
//        calorieSeries.setValueDependentColor(new ValueDependentColor<DataPoint>() {
//            @Override
//            public int get(DataPoint data) {
//                if (data.getY() < 50.0) {
//                    return Color.rgb(227, 21, 0);
//                } else if (data.getY() >= 50.0 && data.getY() < 100.0) {
//                    return Color.rgb(239, 107, 0);
//                } else if (data.getY() >= 100.0 && data.getY() < 150.0) {
//                    return Color.rgb(239, 197, 0);
//                } else if (data.getY() >= 150.0 && data.getY() < 200.0) {
//                    return Color.rgb(159, 199, 0);
//                } else {
//                    return Color.rgb(28, 199, 0);
//                }
////                return Color.rgb(135, 206, 235);
//            }
//        });
//        gv.addSeries(calorieSeries);

    }


    private void initEquipRecord() {
//        mapGroupByEq = new HashMap<String, List<RecordEqDateDone>>();
//
//        for (int i = 0; i < arrGraphElements.size(); i++) {
//            String equipName = arrGraphElements.get(i).getRecordEqId();
//
//            RecordEqDateDone tmpEqDateDone = new RecordEqDateDone();
//            tmpEqDateDone.setRecordEqDate(arrGraphElements.get(i).getRecordDateTime());
//            tmpEqDateDone.setRecordEqDone(arrGraphElements.get(i).getRecordEqDone());
//
//            if (!mapGroupByEq.containsKey(equipName)) {
//                List<RecordEqDateDone> listEqDateDone = new ArrayList<>();
//                listEqDateDone.add(tmpEqDateDone);
//                mapGroupByEq.put(equipName, listEqDateDone);
//            } else {
//                mapGroupByEq.get(equipName).add(tmpEqDateDone);
//            }
//        }
//
////        mapGroupByEq.values().size();
//        Log.d("HashMapEq", "mapGroupByEq.values().size(): " +  mapGroupByEq.values().size());
//        Log.d("HashMapEq", "mapGroupByEq.size(): " + mapGroupByEq.size());
//        Log.d("HashMapEq", "mapGroupByEq.keySet().size(): " + mapGroupByEq.keySet().size());
//
//        ArrayList<MyHereAgent> myAllAgents = new ArrayList<MyHereAgent>();
//        if(MainActivity.hereDB.getAllMyHereAgents() != null)
//            myAllAgents = (ArrayList<MyHereAgent>) MainActivity.hereDB.getAllMyHereAgents();
////        Log.d("HashMapEq", "myAllAgent.size(): " + myAllAgents.size());
////        Log.d("HashMapEq", "myAllAgent.get(0).getMyeqMacId: " + myAllAgents.get(0).getMyeqMacId());
//        agentIDs = new ArrayList<>();
//
//        for (int i = 0; i < myAllAgents.size(); i++) {
//            agentIDs.add(myAllAgents.get(i).getMyeqMacId());
//        }
//
//        for (int i = 0; i < agentIDs.size(); i++) {
//            if(mapGroupByEq.get(agentIDs.get(i)) != null) {
//                Log.d("HashMapEq", "Agent[" + agentIDs.get(i) + "] size: " + mapGroupByEq.get(agentIDs.get(i)).size());
//
//                for (int j = 0; j < mapGroupByEq.get(agentIDs.get(i)).size(); j++) {
//                    Log.d("HashMapEq", "  - Date: " + mapGroupByEq.get(agentIDs.get(i)).get(j).getRecordEqDate());
//                }
//            }
//
//        }

    }


    private void initArrayListFromDB() {
//        //Initialize my records
//        arrMyRecords = new ArrayList<>();
//
//        if(MainActivity.hereDB.getAllMyRecords() != null)
//            arrMyRecords.addAll(MainActivity.hereDB.getAllMyRecords());
//        Log.d(TAG_RECORD_DATA, "arrMyRecords.size(): " + arrMyRecords.size());
//
//        //Initialize arrGraphElements using arrMyRecords; Group records by each equipment
//        arrGraphElements = new ArrayList<>();
//        analyzeMyRecords();
//
//        logArrGraphElements();
    }


    private void analyzeMyRecords() {

//        int arrGraphElementCount = 0;
//
//        for (int i = 0; i < arrMyRecords.size(); i++) {
//            RecordForGraph tmpRecord = new RecordForGraph();
//
//            String recordId = arrMyRecords.get(i).getRecordId();
//            String recordName = arrMyRecords.get(i).getRecordName();
//            String recordDateTime = arrMyRecords.get(i).getRecordDateTime();
//
//            tmpRecord.setRecordId(recordId);
//            tmpRecord.setRecordName(recordName);
//            tmpRecord.setRecordDateTime(recordDateTime);
//
//            /* EQ1 */
//            //If there is no equipment record
//            if (arrMyRecords.get(i).getRecordEq1Id().equals("-1")
//                    || arrMyRecords.get(i).getRecordEq1Id().equals("")) {
//                continue;
//            }
//            //If there is at least one equipment record
//            else {
//                tmpRecord = new RecordForGraph(recordId, recordName, recordDateTime);
//                tmpRecord.setRecordEqId(arrMyRecords.get(i).getRecordEq1Id());
//                tmpRecord.setRecordEqDone(arrMyRecords.get(i).getRecordEq1Done());
//
//                //Add a new element to arrGraphElements
//                arrGraphElements.add(tmpRecord);
////                logArrGraphElements();
//
//                arrGraphElementCount ++;
//
//                /* EQ2 */
//                if (arrMyRecords.get(i).getRecordEq2Id().equals("-1")
//                        || arrMyRecords.get(i).getRecordEq2Id().equals("")) {
//                    continue;
//                } else {
//                    tmpRecord = new RecordForGraph(recordId, recordName, recordDateTime);
//                    tmpRecord.setRecordEqId(arrMyRecords.get(i).getRecordEq2Id());
//                    tmpRecord.setRecordEqDone(arrMyRecords.get(i).getRecordEq2Done());
//
//                    //Add a new element to arrGraphElements
//                    arrGraphElements.add(tmpRecord);
////                    logArrGraphElements();
//
//                    arrGraphElementCount ++;
//
//                    /* EQ3 */
//                    if (arrMyRecords.get(i).getRecordEq3Id().equals("-1")
//                            || arrMyRecords.get(i).getRecordEq3Id().equals("")) {
//                        continue;
//                    } else {
//                        tmpRecord = new RecordForGraph(recordId, recordName, recordDateTime);
//                        tmpRecord.setRecordEqId(arrMyRecords.get(i).getRecordEq3Id());
//                        tmpRecord.setRecordEqDone(arrMyRecords.get(i).getRecordEq3Done());
//
//                        //Add a new element to arrGraphElements
//                        arrGraphElements.add(tmpRecord);
////                        logArrGraphElements();
//
//                        arrGraphElementCount ++;
//
//                        /* EQ4 */
//                        if (arrMyRecords.get(i).getRecordEq4Id().equals("-1")
//                                || arrMyRecords.get(i).getRecordEq4Id().equals("")) {
//                            continue;
//                        } else {
//                            tmpRecord = new RecordForGraph(recordId, recordName, recordDateTime);
//                            tmpRecord.setRecordEqId(arrMyRecords.get(i).getRecordEq4Id());
//                            tmpRecord.setRecordEqDone(arrMyRecords.get(i).getRecordEq4Done());
//
//                            //Add a new element to arrGraphElements
//                            arrGraphElements.add(tmpRecord);
////                            logArrGraphElements();
//
//                            arrGraphElementCount ++;
//
//                            /* EQ5 */
//                            if (arrMyRecords.get(i).getRecordEq5Id().equals("-1")
//                                    || arrMyRecords.get(i).getRecordEq5Id().equals("")) {
//                                continue;
//                            } else {
//                                tmpRecord = new RecordForGraph(recordId, recordName, recordDateTime);
//                                tmpRecord.setRecordEqId(arrMyRecords.get(i).getRecordEq5Id());
//                                tmpRecord.setRecordEqDone(arrMyRecords.get(i).getRecordEq5Done());
//
//                                //Add a new element to arrGraphElements
//                                arrGraphElements.add(tmpRecord);
////                                logArrGraphElements();
//
//                                arrGraphElementCount ++;
//                            }
//                        }
//                    }
//                }
//            }
//
//        }

    }


    private void initDailyRecord() {

//        arrDateCalorie = new ArrayList<>();
//        //dailyCalories = new ArrayList<>();
//
//        CalorieCalculator calorieCalculator = new CalorieCalculator();
//
//        double dailyCalorie = 0.0;
//
//        RecordDateCalorie dateCalorie = new RecordDateCalorie();
//
//        String currentDate;
//        int day = 0;
//
//        if (arrGraphElements.size() == 0) {
//            return;
//        } else {
//            currentDate = parseDateWithYear(arrGraphElements.get(0).getRecordDateTime());
//            dateCalorie.setRecordDate(currentDate);
//
//            Log.d("DateDebugging", "currentDate: " + currentDate);
//
//            for (int i = 0; i < arrGraphElements.size(); i++) {
//
//                //Get the date of record
//                String tmpElementDate = parseDateWithYear(arrGraphElements.get(i).getRecordDateTime());
//                Log.d("DateDebugging", "tmpElementDate: " + tmpElementDate);
//
//                //Get agent by Id
//                MyHereAgent tmpAgent = MainActivity.hereDB.getMyHereAgent(arrGraphElements.get(i).getRecordEqId());
//                //Get my information (height, weight)
//                MyInformation tmpMyInfo = MainActivity.hereDB.getMyInformation();
//
//                //TODO: Done, Time
//                int recordNum = 0;
//                int recordTime = 0;
//                if(tmpAgent!=null) {
//                    switch (tmpAgent.getMyeqType()) {
//                        case MyHereAgent.TYPE_DUMBEL:
//                            recordNum = arrGraphElements.get(i).getRecordEqDone();
//                            recordTime = (int) (recordNum * 1.67);
//                            break;
//                        case MyHereAgent.TYPE_PUSH_UP:
//                            recordNum = arrGraphElements.get(i).getRecordEqDone();
//                            recordTime = (int) (recordNum * 3.0);
//                            break;
//                        case MyHereAgent.TYPE_HOOLA_HOOP:
//                            recordTime = arrGraphElements.get(i).getRecordEqDone();
//                            recordNum = (int) (recordTime * 1.35);
//                            break;
//                    }
//                }
//
//                double tmpColorie = calorieCalculator.getCalorie(tmpAgent, tmpMyInfo, recordNum, recordTime);
//
//                //Record in same date
//                if (!currentDate.equals(tmpElementDate)) {
//                    //Add this day's accumulated colorie
//                    //dailyCalories.add(dailyCalorie);
//                    dateCalorie.setRecordCalorie(dailyCalorie);
//                    arrDateCalorie.add(dateCalorie);
//
//                    //Reinitialize dateCalorie (new instance)
//                    dateCalorie = new RecordDateCalorie();
//
//                    //Update currentDate
//                    currentDate = tmpElementDate;
//                    dateCalorie.setRecordDate(currentDate);
//
//                    //Initialize daily calorie
//                    dailyCalorie = 0;
//                    dailyCalorie += tmpColorie;
//
//                    day++;
//                } else {
//                    //Add the calorie of this record
//                    dailyCalorie += tmpColorie;
//                }
//
//
//            }
//
//            //For last day
//            //dailyCalories.add(dailyCalorie);
//            dateCalorie.setRecordCalorie(dailyCalorie);
//            arrDateCalorie.add(dateCalorie);
//
//        }
//
//        Log.d("arrDateCalorie", "arrDateCalorie.size(): " + arrDateCalorie.size());
//
//        logArrDateCalorie();
//
////        Log.d("DailyCalorie", "dailyColories.size(): " + dailyCalories.size());
////
////        for (int i = 0; i < dailyCalories.size(); i++) {
////            Log.d("DailyCalorie", "dailyColories[" + i + "]: " + dailyCalories.get(i) + "kcal");
////        }

    }



    private String parseDateWithYear(String datetimeData) {
        //dateTimeData: 2016-06-10 00:21:32

        int spaceLoc = datetimeData.indexOf(" ");
        Log.d("TestDateParse", "spaceLoc: " + spaceLoc);
        return datetimeData.substring(0, spaceLoc);
    }

    private String parseOnlyDate(String dateWithYear) {
        //dateWithYear: 2016-06-10

        int hyphenLoc = dateWithYear.indexOf("-");
        Log.d("TestDateParse", "hyphenLoc: " + hyphenLoc + " (" + dateWithYear.charAt(hyphenLoc) + ")");
        return dateWithYear.substring(hyphenLoc + 1, hyphenLoc + 6);
    }

    private void logArrDateCalorie() {
//        Log.d("arrDateCalorie", "arrDateCalorie.size(): " + arrDateCalorie.size());
//        Log.d("arrDateCalorie", "-------------------------------------------------");
//
//        for (int i = 0; i < arrDateCalorie.size(); i++) {
//            Log.d("arrDateCalorie", "arrDateCalorie[" + i + "]: " + arrDateCalorie.get(i).getRecordDate());
//            Log.d("arrDateCalorie", "arrDateCalorie[" + i + "]: " + arrDateCalorie.get(i).getRecordCalorie());
//            Log.d("arrDateCalorie", " ");
//        }
//
//        Log.d("arrDateCalorie", "-------------------------------------------------");
    }

    private void logArrGraphElements() {

//        Log.d(TAG_RECORD_DATA, "arrGraphElements.size(): " + arrGraphElements.size());
//        Log.d(TAG_RECORD_DATA, "-------------------------------------------------");
//
//        for (int i = 0; i < arrGraphElements.size(); i++) {
//            Log.d(TAG_RECORD_DATA, "arrGraphElement[" + i + "]: " + arrGraphElements.get(i).getRecordId());
//            Log.d(TAG_RECORD_DATA, "arrGraphElement[" + i + "]: " + arrGraphElements.get(i).getRecordName());
//            Log.d(TAG_RECORD_DATA, "arrGraphElement[" + i + "]: " + arrGraphElements.get(i).getRecordDateTime());
//            Log.d(TAG_RECORD_DATA, "arrGraphElement[" + i + "]: " + arrGraphElements.get(i).getRecordEqId());
//            Log.d(TAG_RECORD_DATA, "arrGraphElement[" + i + "]: " + arrGraphElements.get(i).getRecordEqDone());
//            Log.d(TAG_RECORD_DATA, " ");
//        }
//
//        Log.d(TAG_RECORD_DATA, "-------------------------------------------------");
    }

    private void initWidgets(View fragmentView) {
//        //Initialize GraphView
//        gv = (GraphView) fragmentView.findViewById(R.id.week_bar_graph);
//
//        btn_seemore = (Button) fragmentView.findViewById(R.id.record_btn_seemore);
//        btn_seemore.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent_myrecords = new Intent(getContext(), MyStudyGroups.class);
//                startActivity(intent_myrecords);
//            }
//        });
//
//        iv_character_img = (ImageView) fragmentView.findViewById(R.id.record_character_img);
//        tv_nick_name = (TextView) fragmentView.findViewById(R.id.record_nick_name);
//        tv_msg = (TextView) fragmentView.findViewById(R.id.record_msg);
//
//        if (iv_character_img == null | tv_nick_name == null | tv_msg == null) {
//            Log.d("RecordInitWidgets", "Widget initialization is failed.");
//        } else {
//            MyInformation tmpMyInformation = MainActivity.hereDB.getMyInformation();
//
//            if (tmpMyInformation == null) {
//                iv_character_img.setImageResource(R.drawable.here_logo_character_notitle);
//                tv_nick_name.setText("User (not registered)");
//                tv_msg.setText("Insert user information");
//            } else {
//                Log.d("RecordInitWidgets", "initMyInfo() is called");
//                Log.d("RecordInitWidgets", "user_sex: " + tmpMyInformation.getUserSex());
//                Log.d("RecordInitWidgets", "user_name: " + tmpMyInformation.getUserName());
//                Log.d("RecordInitWidgets", "user_nick: " + tmpMyInformation.getUserNick());
//
//                if (tmpMyInformation.getUserSex() == 2) {
//                    iv_character_img.setImageResource(R.drawable.here_character_simple_girl);
//                } else {
//                    iv_character_img.setImageResource(R.drawable.here_character_simple_boy);
//                }
//
//                tv_nick_name.setText(tmpMyInformation.getUserNick() + " (" + tmpMyInformation.getUserName() + ")");
//
//                //TODO: Record를 분석하여 Message를 출력해줄 수 있도록 수정
//                tv_msg.setText("Lazy! Do your best!");
//            }
//        }
    }



    String getDay (int date){
        int modulo = date%7;
        if (modulo < 0) modulo+=7;
        switch(modulo){
            case 1:
                return "Sun";
            case 2:
                return "Mon";
            case 3:
                return "Tue";
            case 4:
                return "Wed";
            case 5:
                return "Thu";
            case 6:
                return "Fri";
            case 0:
                return "Sat";
        }
        return "unknown";
    }

//    private class HERE_DeviceListAdapter extends BaseAdapter {
//        private LayoutInflater mInflator;
//
//        public HERE_DeviceListAdapter() {
//            super();
//            equipmentList = new ArrayList<MyHereAgent>();
//            mInflator = getActivity().getLayoutInflater();
//        }
//
//        public void addDevice(MyHereAgent device) {
//            equipmentList.add(device);
//        }
//
//        @Override
//        public int getCount() {
//            return equipmentList.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return equipmentList.get(position);
//        }
//
//        @Override
//        public long getItemId(int i) {
//            return i;
//        }
//
//        @Override
//        public View getView(int i, View view, ViewGroup viewGroup) {
//            // General ListView optimization code.
//            if (view == null) {
//                int res = 0;
//                res = R.layout.listitem_record_graph;
//                view = mInflator.inflate(res, viewGroup, false);
//
//            }
//
//            ImageView eqTypeImage = (ImageView)view.findViewById(R.id.equipment_img);
//            TextView eqName = (TextView)view.findViewById(R.id.equipment_name);
//            GraphView gvLinear = (GraphView) view.findViewById(R.id.weekly_linear_graph);
//            Calendar calendar = Calendar.getInstance();
//
//            List<Double> workoutData = new ArrayList<Double>();
//
//            //TODO: ArrayList(가변길이): Equipment 개수, Array(고정길이): 7개의 DataPoint
//
//            gvLinear.removeAllSeries();
//
//            //Make data points for a recent 1 week
//            DataPoint[] dataPoints = new DataPoint[7];
//
//            for (int data_pt = 0; data_pt < 7; data_pt++) {
//                //int ptRecord = -1;
//
//                String eqId = equipmentList.get(i).getMyeqMacId();
//
//                Log.d("EquipRecord", "eqId: " + eqId);
//
//                if (mapGroupByEq.get(eqId) != null) {
//                    int accumulatedValue = 0;
//                    for (int j = 0; j < mapGroupByEq.get(eqId).size(); j++) {
//                        if (mapGroupByEq.get(eqId).get(j).getDaysGap() == (6 - data_pt)) {
//                            //ptRecord = j;
//                            accumulatedValue += mapGroupByEq.get(eqId).get(j).getRecordEqDone();
//                        }
//                    }
//
//                    if (accumulatedValue > 0) {
//                        DataPoint tmpDataPoint = new DataPoint(data_pt + 1, accumulatedValue);
//                        dataPoints[data_pt] = tmpDataPoint;
//                    } else {
//                        DataPoint tmpDataPoint = new DataPoint(data_pt + 1, 0.0);
//                        dataPoints[data_pt] = tmpDataPoint;
//                    }
//                } else {
//                    DataPoint tmpDataPoint = new DataPoint(data_pt + 1, 0.0);
//                    dataPoints[data_pt] = tmpDataPoint;
//                }
//            }
//
//            LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(dataPoints);
//
//            //Initialize StaticLabelsFormatter
//            StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(gvLinear);
//            staticLabelsFormatter.setHorizontalLabels(new String[]{
//                    getDay(calendar.get(Calendar.DAY_OF_WEEK) - 6),
//                    getDay(calendar.get(Calendar.DAY_OF_WEEK) - 5),
//                    getDay(calendar.get(Calendar.DAY_OF_WEEK) - 4),
//                    getDay(calendar.get(Calendar.DAY_OF_WEEK) - 3),
//                    getDay(calendar.get(Calendar.DAY_OF_WEEK) - 2),
//                    getDay(calendar.get(Calendar.DAY_OF_WEEK) - 1),
//                    getDay(calendar.get(Calendar.DAY_OF_WEEK))});
//            gvLinear.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
//
//            series.setDrawDataPoints(true);
//            series.setColor(Color.rgb(0, 162, 232));
//            series.setDataPointsRadius(5);
//            series.setThickness(4);
//            gvLinear.addSeries(series);
//
////            //here to input data from DB
////            workoutData.add(300.0);workoutData.add(500.0);workoutData.add(100.0);workoutData.add(300.0);workoutData.add(800.0);workoutData.add(400.0);workoutData.add(700.0);
////
////            LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {new DataPoint(1,workoutData.get(0)),
////                    new DataPoint(2,workoutData.get(1)),
////                    new DataPoint(3,workoutData.get(2)),
////                    new DataPoint(4,workoutData.get(3)),
////                    new DataPoint(5,workoutData.get(4)),
////                    new DataPoint(6,workoutData.get(5)),
////                    new DataPoint(7,workoutData.get(6))});
////
////            StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(gvLinear);
////            staticLabelsFormatter.setHorizontalLabels(new String[] {getDay(calendar.get(Calendar.DAY_OF_WEEK)-6), getDay(calendar.get(Calendar.DAY_OF_WEEK)-5), getDay(calendar.get(Calendar.DAY_OF_WEEK)-4), getDay(calendar.get(Calendar.DAY_OF_WEEK)-3), getDay(calendar.get(Calendar.DAY_OF_WEEK)-2), getDay(calendar.get(Calendar.DAY_OF_WEEK)-1), getDay(calendar.get(Calendar.DAY_OF_WEEK))});
////            gvLinear.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
////
////            series.setDrawDataPoints(true);
////            series.setColor(Color.rgb(135, 206, 235));
////            series.setDataPointsRadius(5);
////            series.setThickness(4);
////            gvLinear.addSeries(series);
//
//            switch (equipmentList.get(i).getMyeqType()) {
//                case 1:
//                    eqTypeImage.setImageResource(R.drawable.eq_01_dumbbell);
//                    break;
//                case 2:
//                    eqTypeImage.setImageResource(R.drawable.eq_02_pushupbar);
//                    break;
//                case 3:
//                    eqTypeImage.setImageResource(R.drawable.eq_03_jumprope);
//                    break;
//                case 4:
//                    eqTypeImage.setImageResource(R.drawable.eq_04_hoolahoop);
//                    break;
//                case 5:
//                    eqTypeImage.setImageResource(R.drawable.eq_03_jumprope);
//                    break;
//                default:
//                    break;
//            }
//
//            eqName.setText(equipmentList.get(i).getMyeqName());
//            return view;
//        }
//    }

    private int getMonth(String currentDate) {
        String monthString = currentDate.substring(0,2);
        //Log.d("currentDateTime", "monthString: " + monthString);

        return Integer.parseInt(monthString);
    }

    private int getDay(String currentDate) {
        String dayString = currentDate.substring(3,5);
        //Log.d("currentDateTime", "dayString: " + dayString);

        return Integer.parseInt(dayString);
    }


}
