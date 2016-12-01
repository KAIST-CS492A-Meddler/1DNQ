package com.example.user.onedaynquestions.view.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.onedaynquestions.R;
import com.example.user.onedaynquestions.model.MyHereAgent;
import com.example.user.onedaynquestions.model.MyRoutine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ymbaek on 2016-04-18.
 */
public class MyRoutinesActivity extends AppCompatActivity {

    public static final String TAG = "MyRoutinesActivity";

    private Toolbar toolbar;

    NumberPicker np_set;
    NumberPicker np_count;
    NumberPicker np_time;

    int routineLength = 0;
    List<String> addRoutine;
    HorizontalScrollView scrollView;
    TextView textView;
    ListView routineListView;
    TextView routineTextView;

    ImageView iv_add_routine;
    ImageView iv_delete_routine;

    RoutineListViewAdapter routineListViewAdapter;
    List<MyRoutine> myRoutines;

    int routinePosition = -1;

    //public static List<MyHereAgent> currentAgents = MainActivity.hereDB.getAllMyHereAgents();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myroutines);
        scrollView = (HorizontalScrollView) findViewById(R.id.add_routine_hscrollview);
        textView = (TextView) findViewById(R.id.setting_myroutine_tv_nonewroutine);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("My Exercise Routines");
        toolbar.setNavigationIcon(R.mipmap.ic_menu_arrow_back);
        toolbar.setTitleTextColor(Color.WHITE);

        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("My Exercise Routines");
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);

        myRoutines = new ArrayList<MyRoutine>();

        scrollView.setVisibility(View.GONE);

        addRoutine = new ArrayList<String>();

        routineListView = (ListView) findViewById(R.id.setting_myroutine_lv_myroutines);
        routineTextView = (TextView) findViewById(R.id.setting_myroutine_tv_myroutines);
        routineListViewAdapter = new RoutineListViewAdapter();
        routineListView.setAdapter(routineListViewAdapter);
        routineListViewAdapter.notifyDataSetChanged();
        routineListView.setSelector(new PaintDrawable(Color.rgb(135,206,235)));
        routineListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                routinePosition = position;
            }
        });

        ListView listView = (ListView) findViewById(R.id.setting_myroutine_list_myroutines);
        final ListViewAdapter adapter = new ListViewAdapter();
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        iv_add_routine = (ImageView) findViewById(R.id.setting_myroutine_iv_addroutine);
        iv_delete_routine = (ImageView) findViewById(R.id.setting_myroutine_iv_deleteroutine);

        iv_add_routine.setBackgroundResource(R.drawable.effect_add_press);
        iv_delete_routine.setBackgroundResource(R.drawable.effect_delete_press);

        final TextView routineGoal = (TextView) findViewById(R.id.add_selected_routine_goal1);
        final TextView routineEquip = (TextView) findViewById(R.id.add_selected_routine_equip1);
        final ImageView equipImage = (ImageView) findViewById(R.id.add_routine_equipment1_image);
        final LinearLayout equipment1inList = (LinearLayout) findViewById(R.id.add_routine_equipment1);
        final TextView arrowOfEquipment1 = (TextView) findViewById(R.id.add_routine_equipment1_arrow);

        final TextView routineGoal2 = (TextView) findViewById(R.id.add_selected_routine_goal2);
        final TextView routineEquip2 = (TextView) findViewById(R.id.add_selected_routine_equip2);
        final ImageView equipImage2 = (ImageView) findViewById(R.id.add_routine_equipment2_image);
        final LinearLayout equipment2inList = (LinearLayout) findViewById(R.id.add_routine_equipment2);
        final TextView arrowOfEquipment2 = (TextView) findViewById(R.id.add_routine_equipment2_arrow);

        final TextView routineGoal3 = (TextView) findViewById(R.id.add_selected_routine_goal3);
        final TextView routineEquip3 = (TextView) findViewById(R.id.add_selected_routine_equip3);
        final ImageView equipImage3 = (ImageView) findViewById(R.id.add_routine_equipment3_image);
        final LinearLayout equipment3inList = (LinearLayout) findViewById(R.id.add_routine_equipment3);
        final TextView arrowOfEquipment3 = (TextView) findViewById(R.id.add_routine_equipment3_arrow);

        final TextView routineGoal4 = (TextView) findViewById(R.id.add_selected_routine_goal4);
        final TextView routineEquip4 = (TextView) findViewById(R.id.add_selected_routine_equip4);
        final ImageView equipImage4 = (ImageView) findViewById(R.id.add_routine_equipment4_image);
        final LinearLayout equipment4inList = (LinearLayout) findViewById(R.id.add_routine_equipment4);
        final TextView arrowOfEquipment4 = (TextView) findViewById(R.id.add_routine_equipment4_arrow);

        final TextView routineGoal5 = (TextView) findViewById(R.id.add_selected_routine_goal5);
        final TextView routineEquip5 = (TextView) findViewById(R.id.add_selected_routine_equip5);
        final ImageView equipImage5 = (ImageView) findViewById(R.id.add_routine_equipment5_image);
        final LinearLayout equipment5inList = (LinearLayout) findViewById(R.id.add_routine_equipment5);

        //TODO: 임시 구현 리스너
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                LayoutInflater inflater = getLayoutInflater();

                final View dialogView = inflater.inflate(R.layout.dialog_agentgoal, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(MyRoutinesActivity.this);
                builder.setTitle("Set Exercise Goals");
                builder.setView(dialogView);
                builder.setPositiveButton("Add to routine", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO: Routine을 DB에 추가
                        if (routineLength > 4) {
                            Toast.makeText(getApplicationContext(), "The maximum size of routine is 5", Toast.LENGTH_SHORT).show();
                        } else {
                            int count = np_count.getValue();
                            int time = np_time.getValue();

                            if (count == 0){
                                count = -1;
                            }
                            if (time == 0){
                                time = -1;
                            }
                            if (time == -1 && count == -1){
                                Toast.makeText(getApplicationContext(), "Count and Time are both 0. Select again!", Toast.LENGTH_SHORT).show();
                            }
                            else if (time >0 && count >0){
                                Toast.makeText(getApplicationContext(), "Count and Time are both bigger than 0. Select again!", Toast.LENGTH_SHORT).show();
                            } else {

                                addRoutine.add(adapter.getMyHereAgents().get(position).getMyeqMacId());
                                addRoutine.add(np_set.getValue() + "|" + count + "|" + time);

                                textView.setVisibility(View.GONE);

                                String text="";
                                if(count!=-1)
                                    text = text + count + " X ";
                                if(time != -1)
                                    text = text + time + "s X ";
                                text = text + np_set.getValue() + " SETS";
                                switch(routineLength){
                                    case 0:
                                        routineGoal.setText(text);
                                        routineEquip.setText(adapter.getMyHereAgents().get(position).getMyeqName());
                                        equipImage.setImageResource(findImage(adapter.getMyHereAgents().get(position).getMyeqType()));

                                        equipment1inList.setVisibility(View.VISIBLE);
                                        equipment2inList.setVisibility(View.GONE);
                                        equipment3inList.setVisibility(View.GONE);
                                        equipment4inList.setVisibility(View.GONE);
                                        equipment5inList.setVisibility(View.GONE);

                                        arrowOfEquipment1.setVisibility(View.GONE);
                                        arrowOfEquipment2.setVisibility(View.GONE);
                                        arrowOfEquipment3.setVisibility(View.GONE);
                                        arrowOfEquipment4.setVisibility(View.GONE);
                                        break;

                                    case 1:
                                        routineGoal2.setText(text);
                                        routineEquip2.setText(adapter.getMyHereAgents().get(position).getMyeqName());
                                        equipImage2.setImageResource(findImage(adapter.getMyHereAgents().get(position).getMyeqType()));
                                        equipment2inList.setVisibility(View.VISIBLE);
                                        arrowOfEquipment1.setVisibility(View.VISIBLE);
                                        break;

                                    case 2:
                                        routineGoal3.setText(text);
                                        routineEquip3.setText(adapter.getMyHereAgents().get(position).getMyeqName());
                                        equipImage3.setImageResource(findImage(adapter.getMyHereAgents().get(position).getMyeqType()));
                                        equipment3inList.setVisibility(View.VISIBLE);
                                        arrowOfEquipment2.setVisibility(View.VISIBLE);
                                        break;

                                    case 3:
                                        routineGoal4.setText(text);
                                        routineEquip4.setText(adapter.getMyHereAgents().get(position).getMyeqName());
                                        equipImage4.setImageResource(findImage(adapter.getMyHereAgents().get(position).getMyeqType()));
                                        equipment4inList.setVisibility(View.VISIBLE);
                                        arrowOfEquipment3.setVisibility(View.VISIBLE);
                                        break;

                                    case 4:
                                        routineGoal5.setText(text);
                                        routineEquip5.setText(adapter.getMyHereAgents().get(position).getMyeqName());
                                        equipImage5.setImageResource(findImage(adapter.getMyHereAgents().get(position).getMyeqType()));
                                        equipment5inList.setVisibility(View.VISIBLE);
                                        arrowOfEquipment4.setVisibility(View.VISIBLE);
                                        break;
                                }

                                scrollView.setVisibility(View.VISIBLE);
                                routineLength++;
                            }
                        }
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                np_set = (NumberPicker) dialogView.findViewById(R.id.dialog_agentgoal_picker_set);
                np_set.setMaxValue(20);
                np_set.setMinValue(1);
                np_set.setValue(1);
                np_count = (NumberPicker) dialogView.findViewById(R.id.dialog_agentgoal_picker_count);
                np_count.setMaxValue(100);
                np_count.setMinValue(0);
                np_count.setValue(0);
                np_time = (NumberPicker) dialogView.findViewById(R.id.dialog_agentgoal_picker_time);
                np_time.setMaxValue(600);
                np_time.setMinValue(0);
                np_time.setValue(0);

                AlertDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        });
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
                return R.drawable.list_routine_icon;
        }
        return 0;
    }
    private AlertDialog askDeletion() {
        AlertDialog myDeleteDialogBox = new AlertDialog.Builder(this)
                .setTitle("Delete a routine")
                .setMessage("Are you sure you want to delete the selected routine(s)?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO: Delete selected routines
                        if (routinePosition != -1) {
                            String deletedRoutineName = routineListViewAdapter.getRoutine().get(routinePosition).getRoutineId();
                            MainActivity.hereDB.deleteMyRoutine(deletedRoutineName);
                            routineListViewAdapter.setRoutine(MainActivity.hereDB.getAllMyRoutines());
                            routineListViewAdapter.notifyDataSetChanged();
                            Toast.makeText(getApplicationContext(), deletedRoutineName + "is deleted", Toast.LENGTH_SHORT).show();
                            routinePosition = -1;
                        } else {
                            Toast.makeText(getApplicationContext(),"Click a routine to delete first!",Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();

        return myDeleteDialogBox;
    }


    public void mOnClick(View v) {
        switch (v.getId()) {
//            case R.id.setting_myroutine_btn_newroutine:
//                LayoutInflater inflater = getLayoutInflater();
//
//                final View dialogView = inflater.inflate(R.layout.dialog_routine, null);
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setTitle("New Routine");
//                builder.setIcon(R.drawable.nav_icon_myroutines);
//                builder.setView(dialogView);
//                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
//
//                AlertDialog dialog = builder.create();
//                dialog.setCanceledOnTouchOutside(false);
//                dialog.show();
//
//                break;
//            case R.id.setting_myroutine_btn_modifyroutine:
//                break;
            case R.id.setting_myroutine_iv_deleteroutine:
                AlertDialog deleteAlert = askDeletion();
                deleteAlert.setCanceledOnTouchOutside(false);
                deleteAlert.show();
                break;
            case R.id.setting_myroutine_iv_addroutine:
                LayoutInflater inflater = getLayoutInflater();

                final View dialogView = inflater.inflate(R.layout.dialog_routine, null);
                final EditText editTextId = (EditText) dialogView.findViewById(R.id.dialog_routine_et_id);
                final EditText editTextName = (EditText) dialogView.findViewById(R.id.dialog_routine_et_name);

                int newRoutineId =1;

                if(MainActivity.hereDB.getAllMyRoutines()!=null) {
                    while (true) {
                        boolean isSame = false;

                        for (MyRoutine myRoutine : MainActivity.hereDB.getAllMyRoutines()) {
                            String num = myRoutine.getRoutineId().replaceAll("[^0-9]", "");
                            if (num.equals(""))
                                num = "0";
                            int routineId = Integer.parseInt(num);

                            if (routineId == newRoutineId) {
                                isSame = true;
                                break;
                            }
                        }
                        if (isSame)
                            newRoutineId++;
                        else
                            break;
                    }
                }

                editTextId.setText("ROUTINE"+newRoutineId);

                final int newRoutineID = newRoutineId;

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Add your new routine");
                builder.setView(dialogView);
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO: Routine을 DB에 추가
                        if(routineLength !=0) {
                            routineLength = 0;
                            scrollView.setVisibility(View.GONE);
                            textView.setVisibility(View.VISIBLE);

                            if (editTextName != null)
                                addRoutine.add(0, editTextName.getText().toString());
                            else
                                addRoutine.add(0, "RoutineName");

                            if (editTextId != null)
                                addRoutine.add(0, "ROUTINE"+newRoutineID);

                            MyRoutine addedRoutine = new MyRoutine();
                            addedRoutine.setRoutineId(addRoutine.get(0));
                            addedRoutine.setRoutineName(addRoutine.get(1));
                            if(addRoutine.size()>2) {
                                addedRoutine.setRoutineEq1Id(addRoutine.get(2));
                                addedRoutine.setRoutineEq1Goal(addRoutine.get(3));
                            }
                            if(addRoutine.size()>4) {
                                addedRoutine.setRoutineEq2Id(addRoutine.get(4));
                                addedRoutine.setRoutineEq2Goal(addRoutine.get(5));
                            }
                            if(addRoutine.size()>6) {
                                addedRoutine.setRoutineEq3Id(addRoutine.get(6));
                                addedRoutine.setRoutineEq3Goal(addRoutine.get(7));
                            }
                            if(addRoutine.size()>8) {
                                addedRoutine.setRoutineEq4Id(addRoutine.get(8));
                                addedRoutine.setRoutineEq4Goal(addRoutine.get(9));
                            }
                            if(addRoutine.size()>10) {
                                addedRoutine.setRoutineEq5Id(addRoutine.get(10));
                                addedRoutine.setRoutineEq5Goal(addRoutine.get(11));
                            }

                            MainActivity.hereDB.insertRoutine(addedRoutine);
                            myRoutines = MainActivity.hereDB.getAllMyRoutines();

                            routineTextView.setVisibility(View.GONE);
                            routineListView.setVisibility(View.VISIBLE);

                            routineListViewAdapter.setRoutine(MainActivity.hereDB.getAllMyRoutines());
                            routineListViewAdapter.notifyDataSetChanged();
                            addRoutine.clear();

                        } else {
                            Toast.makeText(getApplicationContext(), "There is no routine. Add exercises!", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public class ListViewAdapter extends BaseAdapter {

        public List<MyHereAgent> myHereAgents = new ArrayList<MyHereAgent>();

        public List<MyHereAgent> getMyHereAgents(){
            return myHereAgents;
        }

        public ListViewAdapter () {
            super();
            if(MainActivity.hereDB.getAllMyHereAgents() !=null)
                myHereAgents = MainActivity.hereDB.getAllMyHereAgents();
        }

        @Override
        public int getCount() {
                return myHereAgents.size();
        }

        @Override
        public Object getItem(int position) {
            return myHereAgents.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final int pos = position;
            final Context context = parent.getContext();

            if (convertView == null){
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.listitem_equipment_simple,parent,false);
            }
            ImageView eqTypeImage = (ImageView) convertView.findViewById(R.id.equiplist_img);
            TextView eqName = (TextView) convertView.findViewById(R.id.equiplist_name);
            TextView eqId = (TextView) convertView.findViewById(R.id.equiplist_id);
            TextView eqSensorType = (TextView) convertView.findViewById(R.id.equiplist_sensorid);

            eqTypeImage.setImageResource(findImage(myHereAgents.get(pos).getMyeqType()));

            eqName.setText(myHereAgents.get(pos).getMyeqName());
            eqId.setText(myHereAgents.get(pos).getMyeqMacId());
            //eqSensorType.setText(registeredAgents.get(pos).getMyeqType());

            return convertView;
        }
    }

    private class RoutineListViewAdapter extends BaseAdapter {


        public List<MyRoutine> getRoutine(){
            return myRoutines;
        }

        public void setRoutine(List<MyRoutine> routines){
            myRoutines.clear();
            myRoutines = routines;
        }

        public RoutineListViewAdapter() {
            super();
            if(MainActivity.hereDB.getAllMyRoutines() !=null) {
                myRoutines = MainActivity.hereDB.getAllMyRoutines();
                routineTextView.setVisibility(View.GONE);
                routineListView.setVisibility(View.VISIBLE);
            } else {
                myRoutines.clear();
                routineTextView.setVisibility(View.VISIBLE);
                routineListView.setVisibility(View.GONE);
            }
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
            final Context context = viewGroup.getContext();
            // General ListView optimization code.
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.listitem_routine, viewGroup, false);
            }

            ImageView routineImage = (ImageView) view.findViewById(R.id.equiplist_img);
            TextView routineName = (TextView) view.findViewById(R.id.equiplist_name);
            TextView routineId = (TextView) view.findViewById(R.id.equiplist_id);
            TextView routineSummary = (TextView) view.findViewById(R.id.equiplist_sensorid);

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
            //eqSensorId.setText(pairedEquipList.get(i).getEquipmentSensorID());

            return view;
        }
    }
}
