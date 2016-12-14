package com.example.user.onedaynquestions.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.user.onedaynquestions.R;
import com.example.user.onedaynquestions.model.AsyncResponse;
import com.example.user.onedaynquestions.utility.PostResponseAsyncTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by ymbaek on 2016-04-18.
 */
public class MyAchievements extends AppCompatActivity implements AsyncResponse {

    public static final String TAG = "MyAchievements";

    private Toolbar toolbar;

//    NumberPicker np_set;
//    NumberPicker np_count;
//    NumberPicker np_time;
//
//    int routineLength = 0;
//    List<String> addRoutine;
//    HorizontalScrollView scrollView;
//    TextView textView;
//    ListView routineListView;
//    TextView routineTextView;
//
//    ImageView iv_add_routine;
//    ImageView iv_delete_routine;
//
//    RoutineListViewAdapter routineListViewAdapter;
//    List<MyRoutine> myRoutines;
//
//    int routinePosition = -1;

    //public static List<MyHereAgent> currentAgents = MainActivity.hereDB.getAllMyHereAgents();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myachievements);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("My Achievements");
        toolbar.setNavigationIcon(R.mipmap.ic_menu_arrow_back);
        toolbar.setTitleTextColor(Color.WHITE);

        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("My Achievements");
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);

        recordUserLog("MyAchievements", "onCreate");

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
//    private AlertDialog askDeletion() {
//        AlertDialog myDeleteDialogBox = new AlertDialog.Builder(this)
//                .setTitle("Delete a routine")
//                .setMessage("Are you sure you want to delete the selected routine(s)?")
//                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        //TODO: Delete selected routines
//                        if (routinePosition != -1) {
//                            String deletedRoutineName = routineListViewAdapter.getRoutine().get(routinePosition).getRoutineId();
//                            MainActivity.hereDB.deleteMyRoutine(deletedRoutineName);
//                            routineListViewAdapter.setRoutine(MainActivity.hereDB.getAllMyRoutines());
//                            routineListViewAdapter.notifyDataSetChanged();
//                            Toast.makeText(getApplicationContext(), deletedRoutineName + "is deleted", Toast.LENGTH_SHORT).show();
//                            routinePosition = -1;
//                        } else {
//                            Toast.makeText(getApplicationContext(),"Click a routine to delete first!",Toast.LENGTH_SHORT).show();
//                        }
//                        dialog.dismiss();
//                    }
//                })
//                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                })
//                .create();
//
//        return myDeleteDialogBox;
//    }


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
//            case R.id.setting_myroutine_iv_deleteroutine:
//                AlertDialog deleteAlert = askDeletion();
//                deleteAlert.setCanceledOnTouchOutside(false);
//                deleteAlert.show();
//                break;
//            case R.id.setting_myroutine_iv_addroutine:
//                LayoutInflater inflater = getLayoutInflater();
//
//                final View dialogView = inflater.inflate(R.layout.dialog_routine, null);
//                final EditText editTextId = (EditText) dialogView.findViewById(R.id.dialog_routine_et_id);
//                final EditText editTextName = (EditText) dialogView.findViewById(R.id.dialog_routine_et_name);
//
//                int newRoutineId =1;
//
//                if(MainActivity.hereDB.getAllMyRoutines()!=null) {
//                    while (true) {
//                        boolean isSame = false;
//
//                        for (MyRoutine myRoutine : MainActivity.hereDB.getAllMyRoutines()) {
//                            String num = myRoutine.getRoutineId().replaceAll("[^0-9]", "");
//                            if (num.equals(""))
//                                num = "0";
//                            int routineId = Integer.parseInt(num);
//
//                            if (routineId == newRoutineId) {
//                                isSame = true;
//                                break;
//                            }
//                        }
//                        if (isSame)
//                            newRoutineId++;
//                        else
//                            break;
//                    }
//                }
//
//                editTextId.setText("ROUTINE"+newRoutineId);
//
//                final int newRoutineID = newRoutineId;
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.setTitle("Add your new routine");
//                builder.setView(dialogView);
//                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        //TODO: Routine을 DB에 추가
//                        if(routineLength !=0) {
//                            routineLength = 0;
//                            scrollView.setVisibility(View.GONE);
//                            textView.setVisibility(View.VISIBLE);
//
//                            if (editTextName != null)
//                                addRoutine.add(0, editTextName.getText().toString());
//                            else
//                                addRoutine.add(0, "RoutineName");
//
//                            if (editTextId != null)
//                                addRoutine.add(0, "ROUTINE"+newRoutineID);
//
//                            MyRoutine addedRoutine = new MyRoutine();
//                            addedRoutine.setRoutineId(addRoutine.get(0));
//                            addedRoutine.setRoutineName(addRoutine.get(1));
//                            if(addRoutine.size()>2) {
//                                addedRoutine.setRoutineEq1Id(addRoutine.get(2));
//                                addedRoutine.setRoutineEq1Goal(addRoutine.get(3));
//                            }
//                            if(addRoutine.size()>4) {
//                                addedRoutine.setRoutineEq2Id(addRoutine.get(4));
//                                addedRoutine.setRoutineEq2Goal(addRoutine.get(5));
//                            }
//                            if(addRoutine.size()>6) {
//                                addedRoutine.setRoutineEq3Id(addRoutine.get(6));
//                                addedRoutine.setRoutineEq3Goal(addRoutine.get(7));
//                            }
//                            if(addRoutine.size()>8) {
//                                addedRoutine.setRoutineEq4Id(addRoutine.get(8));
//                                addedRoutine.setRoutineEq4Goal(addRoutine.get(9));
//                            }
//                            if(addRoutine.size()>10) {
//                                addedRoutine.setRoutineEq5Id(addRoutine.get(10));
//                                addedRoutine.setRoutineEq5Goal(addRoutine.get(11));
//                            }
//
//                            MainActivity.hereDB.insertRoutine(addedRoutine);
//                            myRoutines = MainActivity.hereDB.getAllMyRoutines();
//
//                            routineTextView.setVisibility(View.GONE);
//                            routineListView.setVisibility(View.VISIBLE);
//
//                            routineListViewAdapter.setRoutine(MainActivity.hereDB.getAllMyRoutines());
//                            routineListViewAdapter.notifyDataSetChanged();
//                            addRoutine.clear();
//
//                        } else {
//                            Toast.makeText(getApplicationContext(), "There is no routine. Add exercises!", Toast.LENGTH_SHORT).show();
//                        }
//                        dialog.dismiss();
//                    }
//                });
//                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//
//                AlertDialog dialog = builder.create();
//                dialog.setCanceledOnTouchOutside(false);
//                dialog.show();
//                break;
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

    @Override
    public void processFinish(String output) {

    }

//    public class ListViewAdapter extends BaseAdapter {
//
//        public List<MyHereAgent> myHereAgents = new ArrayList<MyHereAgent>();
//
//        public List<MyHereAgent> getMyHereAgents(){
//            return myHereAgents;
//        }
//
//        public ListViewAdapter () {
//            super();
//            if(MainActivity.hereDB.getAllMyHereAgents() !=null)
//                myHereAgents = MainActivity.hereDB.getAllMyHereAgents();
//        }
//
//        @Override
//        public int getCount() {
//                return myHereAgents.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return myHereAgents.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            final int pos = position;
//            final Context context = parent.getContext();
//
//            if (convertView == null){
//                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                convertView = inflater.inflate(R.layout.listitem_equipment_simple,parent,false);
//            }
//            ImageView eqTypeImage = (ImageView) convertView.findViewById(R.id.equiplist_img);
//            TextView eqName = (TextView) convertView.findViewById(R.id.equiplist_name);
//            TextView eqId = (TextView) convertView.findViewById(R.id.equiplist_id);
//            TextView eqSensorType = (TextView) convertView.findViewById(R.id.equiplist_sensorid);
//
//            eqTypeImage.setImageResource(findImage(myHereAgents.get(pos).getMyeqType()));
//
//            eqName.setText(myHereAgents.get(pos).getMyeqName());
//            eqId.setText(myHereAgents.get(pos).getMyeqMacId());
//            //eqSensorType.setText(registeredAgents.get(pos).getMyeqType());
//
//            return convertView;
//        }
//    }
//
//    private class RoutineListViewAdapter extends BaseAdapter {
//
//
//        public List<MyRoutine> getRoutine(){
//            return myRoutines;
//        }
//
//        public void setRoutine(List<MyRoutine> routines){
//            myRoutines.clear();
//            myRoutines = routines;
//        }
//
//        public RoutineListViewAdapter() {
//            super();
//            if(MainActivity.hereDB.getAllMyRoutines() !=null) {
//                myRoutines = MainActivity.hereDB.getAllMyRoutines();
//                routineTextView.setVisibility(View.GONE);
//                routineListView.setVisibility(View.VISIBLE);
//            } else {
//                myRoutines.clear();
//                routineTextView.setVisibility(View.VISIBLE);
//                routineListView.setVisibility(View.GONE);
//            }
//        }
//
//        @Override
//        public int getCount() {
//            if (myRoutines != null)
//                return myRoutines.size();
//            else
//                return 0;
//        }
//
//        @Override
//        public Object getItem(int i) {
//            return myRoutines.get(i);
//        }
//
//        @Override
//        public long getItemId(int i) {
//            return i;
//        }
//
//        @Override
//        public View getView(int i, View view, ViewGroup viewGroup) {
//            final Context context = viewGroup.getContext();
//            // General ListView optimization code.
//            if (view == null) {
//                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                view = inflater.inflate(R.layout.listitem_routine, viewGroup, false);
//            }
//
//            ImageView routineImage = (ImageView) view.findViewById(R.id.equiplist_img);
//            TextView routineName = (TextView) view.findViewById(R.id.equiplist_name);
//            TextView routineId = (TextView) view.findViewById(R.id.equiplist_id);
//            TextView routineSummary = (TextView) view.findViewById(R.id.equiplist_sensorid);
//
//            routineImage.setImageResource(R.drawable.list_routine_icon);
//
//            routineName.setText(myRoutines.get(i).getRoutineId());
//            routineId.setText(myRoutines.get(i).getRoutineName());
//
//            String summary = "";
//
//            MyHereAgent myHereAgent;
//            if(!myRoutines.get(i).getRoutineEq1Id().equals("-1")) {
//                myHereAgent = MainActivity.hereDB.getMyHereAgent(myRoutines.get(i).getRoutineEq1Id());
//                if(myHereAgent !=null)
//                    summary += myHereAgent.getMyeqName();
//            }
//            if(!myRoutines.get(i).getRoutineEq2Id().equals("-1")) {
//                myHereAgent =  MainActivity.hereDB.getMyHereAgent(myRoutines.get(i).getRoutineEq2Id());
//                if(myHereAgent != null)
//                    summary += " - " + myHereAgent.getMyeqName();
//            }
//            if(!myRoutines.get(i).getRoutineEq3Id().equals("-1")) {
//                myHereAgent = MainActivity.hereDB.getMyHereAgent(myRoutines.get(i).getRoutineEq3Id());
//                if(myHereAgent != null)
//                    summary += " - " + myHereAgent.getMyeqName();
//            }
//            if(!myRoutines.get(i).getRoutineEq4Id().equals("-1")) {
//                myHereAgent = MainActivity.hereDB.getMyHereAgent(myRoutines.get(i).getRoutineEq4Id());
//                if(myHereAgent != null)
//                    summary += " - " + myHereAgent.getMyeqName();
//            }
//            if(!myRoutines.get(i).getRoutineEq5Id().equals("-1")) {
//                myHereAgent =  MainActivity.hereDB.getMyHereAgent(myRoutines.get(i).getRoutineEq5Id());
//                if (myHereAgent != null)
//                    summary += " - " + myHereAgent.getMyeqName();
//            }
//
//            routineSummary.setText(summary);
//            //eqSensorId.setText(pairedEquipList.get(i).getEquipmentSensorID());
//
//            return view;
//        }
//    }

    public void recordUserLog(String argActivity, String argEvent) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
//        Toast.makeText(getApplicationContext(), "current time: " + dateFormat.format(calendar.getTime()), Toast.LENGTH_SHORT).show();

        String logTimestamp = dateFormat.format(calendar.getTime());
        int logDuration = 0;
        String logCurActivity = "";
        if (argActivity == null || argActivity.equals("")) {
            logCurActivity = "unknown";
        } else {
            logCurActivity = argActivity;
        }
        String logCurEvent = "";
        if (argEvent == null || argEvent.equals("")) {
            logCurEvent = "unknown";
        } else {
            logCurEvent = argEvent;
        }

        HashMap postData = new HashMap();

        if (MainActivity.odnqDB != null) {
            if (MainActivity.odnqDB.getMyInfo() != null) {
                postData.put("userinfo_id", MainActivity.odnqDB.getMyInfo().getMyInfoId());
            } else {
                postData.put("userinfo_id", "unknown");
            }
        } else {
            postData.put("userinfo_id", "unknown");
        }
        postData.put("log_timestamp", logTimestamp);
        postData.put("log_duration", logDuration+"");
        postData.put("log_curactivity", logCurActivity);
        postData.put("log_curevent", logCurEvent);

//        postData.put("userinfo_id", "ididididid");
//        postData.put("log_timestamp", "timetime");
//        postData.put("log_duration", "duration");
//        postData.put("log_curactivity", "activity");
//        postData.put("log_curevent", "event");

        PostResponseAsyncTask createUserLogTask =
                new PostResponseAsyncTask(MyAchievements.this, postData);

        createUserLogTask.execute("http://110.76.95.150/create_userlog.php");
        Log.d("USER_LOG", "[" + logTimestamp + "] " + logCurActivity + " - " + logCurEvent);
    }
}
