package com.example.user.onedaynquestions.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.onedaynquestions.R;
import com.example.user.onedaynquestions.archive.MyHereAgent;
import com.example.user.onedaynquestions.archive.MyRoutine;
import com.example.user.onedaynquestions.view.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by ymbae on 2016-04-18.
 */
public class MyAchievementFragment extends Fragment{

    public List<Goal> goals;
    ListViewAdapter listViewAdapter;

//    TextView tv_noroutine;
//    HorizontalScrollView horizontalScrollView;
//
//    Button btn_refresh;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        goals = new ArrayList<Goal>();

//        listViewAdapter = new ListViewAdapter();
        final View viewFragmentRoutine = inflater.inflate(R.layout.fragment_myachievement, container, false);
//
//        initWidgets(viewFragmentRoutine);
//
//        ListView listView = (ListView) viewFragmentRoutine.findViewById(R.id.routine_list);
//        listView.setAdapter(listViewAdapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                List<MyRoutine> routines = listViewAdapter.getRoutine();
//                TextView routineName = (TextView) viewFragmentRoutine.findViewById(R.id.selected_routine_name);
//                boolean isRoutineOk = true;
//                routineName.setText("Selected routine: " + routines.get(position).getRoutineName());
//
//                goals.clear();
//
//                TextView routineGoal = (TextView) viewFragmentRoutine.findViewById(R.id.selected_routine_goal1);
//                TextView routineEquip = (TextView) viewFragmentRoutine.findViewById(R.id.selected_routine_equip1);
//                ImageView equipImage = (ImageView) viewFragmentRoutine.findViewById(R.id.routine_equipment1_image);
//                LinearLayout equipment1inList = (LinearLayout) viewFragmentRoutine.findViewById(R.id.routine_equipment1);
//                TextView arrowOfEquipment1 = (TextView) viewFragmentRoutine.findViewById(R.id.routine_equipment1_arrow);
//
//                TextView routineGoal2 = (TextView) viewFragmentRoutine.findViewById(R.id.selected_routine_goal2);
//                TextView routineEquip2 = (TextView) viewFragmentRoutine.findViewById(R.id.selected_routine_equip2);
//                ImageView equipImage2 = (ImageView) viewFragmentRoutine.findViewById(R.id.routine_equipment2_image);
//                LinearLayout equipment2inList = (LinearLayout) viewFragmentRoutine.findViewById(R.id.routine_equipment2);
//                TextView arrowOfEquipment2 = (TextView) viewFragmentRoutine.findViewById(R.id.routine_equipment2_arrow);
//
//                TextView routineGoal3 = (TextView) viewFragmentRoutine.findViewById(R.id.selected_routine_goal3);
//                TextView routineEquip3 = (TextView) viewFragmentRoutine.findViewById(R.id.selected_routine_equip3);
//                ImageView equipImage3 = (ImageView) viewFragmentRoutine.findViewById(R.id.routine_equipment3_image);
//                LinearLayout equipment3inList = (LinearLayout) viewFragmentRoutine.findViewById(R.id.routine_equipment3);
//                TextView arrowOfEquipment3 = (TextView) viewFragmentRoutine.findViewById(R.id.routine_equipment3_arrow);
//
//                TextView routineGoal4 = (TextView) viewFragmentRoutine.findViewById(R.id.selected_routine_goal4);
//                TextView routineEquip4 = (TextView) viewFragmentRoutine.findViewById(R.id.selected_routine_equip4);
//                ImageView equipImage4 = (ImageView) viewFragmentRoutine.findViewById(R.id.routine_equipment4_image);
//                LinearLayout equipment4inList = (LinearLayout) viewFragmentRoutine.findViewById(R.id.routine_equipment4);
//                TextView arrowOfEquipment4 = (TextView) viewFragmentRoutine.findViewById(R.id.routine_equipment4_arrow);
//
//                TextView routineGoal5 = (TextView) viewFragmentRoutine.findViewById(R.id.selected_routine_goal5);
//                TextView routineEquip5 = (TextView) viewFragmentRoutine.findViewById(R.id.selected_routine_equip5);
//                ImageView equipImage5 = (ImageView) viewFragmentRoutine.findViewById(R.id.routine_equipment5_image);
//                LinearLayout equipment5inList = (LinearLayout) viewFragmentRoutine.findViewById(R.id.routine_equipment5);
//
//                btn_refresh = (Button) viewFragmentRoutine.findViewById(R.id.routine_btn_refresh);
//                btn_refresh.setBackgroundResource(R.drawable.effect_refresh_press);
//
//                btn_refresh.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        listViewAdapter.notifyDataSetChanged();
//                        Toast.makeText(viewFragmentRoutine.getContext(), "List is updated", Toast.LENGTH_SHORT).show();
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

        return viewFragmentRoutine;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }


    private void initWidgets(View fragmentView) {
//        tv_noroutine = (TextView) fragmentView.findViewById(R.id.routine_noroutine);
//        horizontalScrollView = (HorizontalScrollView) fragmentView.findViewById(R.id.routine_hscrollview);
//
//        tv_noroutine.setVisibility(View.VISIBLE);
//        horizontalScrollView.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
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

}
