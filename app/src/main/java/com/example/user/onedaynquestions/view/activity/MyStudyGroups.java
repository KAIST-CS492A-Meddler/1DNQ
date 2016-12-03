package com.example.user.onedaynquestions.view.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.onedaynquestions.R;
import com.example.user.onedaynquestions.model.MyHereAgent;
import com.example.user.onedaynquestions.model.MyRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ymbaek on 2016-04-18.
 */
public class MyStudyGroups extends AppCompatActivity {

    public static final String TAG = "MyStudyGroups";

    private Toolbar toolbar;

//    private ListViewAdapter adapter;

//    //Change
//    int i = 0;
//
//    TextView textView;
//    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mystudygroups);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("My Study Groups");
        toolbar.setNavigationIcon(R.mipmap.ic_menu_arrow_back);
        toolbar.setTitleTextColor(Color.WHITE);

        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("My Study Groups");
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);

//        textView = (TextView) findViewById(R.id.setting_myrecord_tv_myrecord);
//        listView = (ListView) findViewById(R.id.daily_record_list);
//        adapter = new ListViewAdapter();
//        listView.setAdapter(adapter);
//        adapter.notifyDataSetChanged();

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

//    public class ListViewAdapter extends BaseAdapter {
//
//        public List<MyRecord> myRecords = new ArrayList<MyRecord>();
//        public List<MyHereAgent> myHereAgents = new ArrayList<MyHereAgent>();
//
//        public List<MyRecord> getMyRecords(){
//            return myRecords;
//        }
//
//        public ListViewAdapter () {
//            super();
//            if(MainActivity.hereDB.getAllMyRecords() !=null) {
//                myRecords = MainActivity.hereDB.getAllMyRecords();
//            }
//            if(MainActivity.hereDB.getAllMyHereAgents() != null) {
//                myHereAgents = MainActivity.hereDB.getAllMyHereAgents();
//            }
//        }
//
//        @Override
//        public int getCount() {
//            return myRecords.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return myRecords.get(position);
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
//                convertView = inflater.inflate(R.layout.listitem_record,parent,false);
//            }
//
//
//            if(myRecords.size() <= 0){
//                textView.setVisibility(View.VISIBLE);
//                listView.setVisibility(View.GONE);
//            } else {
//                textView.setVisibility(View.GONE);
//                listView.setVisibility(View.VISIBLE);
//
//                TextView recordDate = (TextView) convertView.findViewById(R.id.recordlist_datetime);
//                TextView recordTime = (TextView) convertView.findViewById(R.id.recordlist_time);
//                TextView recordName = (TextView) convertView.findViewById(R.id.recordlist_name);
//                TextView recordEq1Name = (TextView) convertView.findViewById(R.id.recordlist_eq1_name);
//                TextView recordEq1Record = (TextView) convertView.findViewById(R.id.recordlist_eq1_done);
//                TextView recordEq2Name = (TextView) convertView.findViewById(R.id.recordlist_eq2_name);
//                TextView recordEq2Record = (TextView) convertView.findViewById(R.id.recordlist_eq2_done);
//                TextView recordEq3Name = (TextView) convertView.findViewById(R.id.recordlist_eq3_name);
//                TextView recordEq3Record = (TextView) convertView.findViewById(R.id.recordlist_eq3_done);
//                TextView recordEq4Name = (TextView) convertView.findViewById(R.id.recordlist_eq4_name);
//                TextView recordEq4Record = (TextView) convertView.findViewById(R.id.recordlist_eq4_done);
//                TextView recordEq5Name = (TextView) convertView.findViewById(R.id.recordlist_eq5_name);
//                TextView recordEq5Record = (TextView) convertView.findViewById(R.id.recordlist_eq5_done);
//
//                String onlyDate = parseOnlyDate(parseDateWithYear(myRecords.get(pos).getRecordDateTime()));
//                String onlyTime = parseOnlyTime(myRecords.get(pos).getRecordDateTime());
//
//                recordDate.setText(onlyDate);
//                recordTime.setText(onlyTime);
//                recordName.setText(myRecords.get(pos).getRecordName());
//
//
//
//
//                MyHereAgent myHereAgent;
//                if(!myRecords.get(pos).getRecordEq1Id().equals("-1")){
//                    myHereAgent = MainActivity.hereDB.getMyHereAgent(myRecords.get(pos).getRecordEq1Id());
//                    if(myHereAgent != null) {
//                        recordEq1Name.setText(myHereAgent.getMyeqName());
//                        recordEq1Record.setText(myRecords.get(pos).getRecordEq1Done() + makeUnitString(myHereAgent));
//                    }
//                } else {
//                    recordEq1Name.setVisibility(View.GONE);
//                    recordEq1Record.setVisibility(View.GONE);
//                }
//
//                if(!myRecords.get(pos).getRecordEq2Id().equals("-1")){
//                    myHereAgent = MainActivity.hereDB.getMyHereAgent(myRecords.get(pos).getRecordEq2Id());
//                    if(myHereAgent != null) {
//                        recordEq2Name.setText(myHereAgent.getMyeqName());
//                        recordEq2Record.setText(myRecords.get(pos).getRecordEq2Done() + makeUnitString(myHereAgent));
//                    }
//                } else {
//                    recordEq2Name.setVisibility(View.GONE);
//                    recordEq2Record.setVisibility(View.GONE);
//                }
//
//                if(!myRecords.get(pos).getRecordEq3Id().equals("-1")){
//                    myHereAgent = MainActivity.hereDB.getMyHereAgent(myRecords.get(pos).getRecordEq3Id());
//                    if (myHereAgent != null) {
//                        recordEq3Name.setText(myHereAgent.getMyeqName());
//                        recordEq3Record.setText(myRecords.get(pos).getRecordEq3Done() + makeUnitString(myHereAgent));
//                    }
//                } else {
//                    recordEq3Name.setVisibility(View.GONE);
//                    recordEq3Record.setVisibility(View.GONE);
//                }
//
//                if(!myRecords.get(pos).getRecordEq4Id().equals("-1")){
//                    myHereAgent =MainActivity.hereDB.getMyHereAgent(myRecords.get(pos).getRecordEq4Id());
//                    if(myHereAgent != null) {
//                        recordEq4Name.setText(myHereAgent.getMyeqName());
//                        recordEq4Record.setText(myRecords.get(pos).getRecordEq4Done() + makeUnitString(myHereAgent));
//                    }
//                } else {
//                    recordEq4Name.setVisibility(View.GONE);
//                    recordEq4Record.setVisibility(View.GONE);
//                }
//
//                if(!myRecords.get(pos).getRecordEq5Id().equals("-1")){
//                    myHereAgent = MainActivity.hereDB.getMyHereAgent(myRecords.get(pos).getRecordEq5Id());
//                    if (myHereAgent != null) {
//                        recordEq5Name.setText(myHereAgent.getMyeqName());
//                        recordEq5Record.setText(myRecords.get(pos).getRecordEq5Done() + makeUnitString(myHereAgent));
//                    }
//                } else {
//                    recordEq5Name.setVisibility(View.GONE);
//                    recordEq5Record.setVisibility(View.GONE);
//                }
//
//            }
//
//            return convertView;
//        }
//    }

//    public int findImage (int type){
//        switch (type) {
//            case 1:
//                return R.drawable.eq_01_dumbbell;
//            case 2:
//                return R.drawable.eq_02_pushupbar;
//            case 3:
//                return R.drawable.eq_03_jumprope;
//            case 4:
//                return R.drawable.eq_04_hoolahoop;
//            case 5:
//                return R.mipmap.ic_setting_user_information;
//        }
//        return 0;
//    }

//    private String makeUnitString(MyHereAgent agent) {
//        switch (agent.getMyeqType()) {
//            case MyHereAgent.TYPE_PUSH_UP:
//            case MyHereAgent.TYPE_DUMBEL:
//                return " TIMES";
//            case MyHereAgent.TYPE_HOOLA_HOOP:
//            case MyHereAgent.TYPE_JUMP_ROPE:
//                return " SECONDS";
//            default:
//                return " TIMES";
//        }
//    }

    private String parseDateWithYear(String datetimeData) {
        //dateTimeData: 2016-06-10 00:21:32

        int spaceLoc = datetimeData.indexOf(" ");
        return datetimeData.substring(0, spaceLoc);
    }

    private String parseOnlyTime(String datetimeData) {
        int spaceLoc = datetimeData.indexOf(" ");
        return datetimeData.substring(spaceLoc + 1, datetimeData.length());
    }

    private String parseOnlyDate(String dateWithYear) {
        //dateWithYear: 2016-06-10

        int hyphenLoc = dateWithYear.indexOf("-");
        return dateWithYear.substring(hyphenLoc + 1, hyphenLoc + 6);
    }


}
