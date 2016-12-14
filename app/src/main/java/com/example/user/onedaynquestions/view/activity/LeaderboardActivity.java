package com.example.user.onedaynquestions.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.onedaynquestions.R;
import com.example.user.onedaynquestions.controller.MemberListAdapter;
import com.example.user.onedaynquestions.controller.QuestionListAdapter;
import com.example.user.onedaynquestions.model.AsyncResponse;
import com.example.user.onedaynquestions.model.MyCard;
import com.example.user.onedaynquestions.service.FloatingButtonService;
import com.example.user.onedaynquestions.utility.PostResponseAsyncTask;
import com.example.user.onedaynquestions.view.testactivity.DBServerTestActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Member;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import static com.example.user.onedaynquestions.R.id.container;

/**
 * Created by ymbaek on 2016-04-18.
 */
public class LeaderboardActivity extends AppCompatActivity implements AsyncResponse {

    public static final String TAG = "Leaderboard";

    private Toolbar toolbar;

    private TextView leaderboard_tv_groupname;
    private TextView leaderboard_tv_groupmemnum;
    private TextView leaderboard_tv_groupleader;
    private TextView leaderboard_tv_groupgoal;

    private Button leaderboard_btn_findgroup;
    private ArrayList<UserInfo> memberList;
    private MemberListAdapter memberListAdapter;
    private ListView lvMemberList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("Group Leaderboard");
        toolbar.setNavigationIcon(R.mipmap.ic_menu_arrow_back);
        toolbar.setTitleTextColor(Color.WHITE);

        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("Group Leaderboard");
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);


        lvMemberList = (ListView) findViewById(R.id.leaderboard_lv_users);

        memberList = new ArrayList<>();
        memberListAdapter = new MemberListAdapter(this, memberList);
        lvMemberList.setAdapter(memberListAdapter);


//        memberList.add(new UserInfo(true, "SG", "topmaze", "sunggeun", 231));
//        memberList.add(new UserInfo(false, "GS", "top", "sung", 31));
//        memberList.add(new UserInfo(false, "1S2G", "maze", "geun", 21));
//        memberList.add(new UserInfo(true, "S4G", "A431t9", "soony", 281));
//        memberList.add(new UserInfo(true, "S464G", "apsov93", "hyang", 631));

        memberListAdapter.notifyDataSetChanged();

        recordUserLog("LeaderboardActivity", "onCreate");


        initWidget();
        initWidgetValue();

        initLeaderBoard();
    }


    public void initWidget() {
        leaderboard_btn_findgroup = (Button) findViewById(R.id.leaderboard_btn_findgroup);

        leaderboard_tv_groupname = (TextView) findViewById(R.id.leaderboard_tv_groupname);
        leaderboard_tv_groupmemnum = (TextView) findViewById(R.id.leaderboard_tv_groupmemnum);
        leaderboard_tv_groupleader = (TextView) findViewById(R.id.leaderboard_tv_groupleader);
        leaderboard_tv_groupgoal = (TextView) findViewById(R.id.leaderboard_tv_groupgoal);
    }

    public void initWidgetValue() {
        HashMap postData = new HashMap();
        postData.put("ginfo_id", "group-1");

        PostResponseAsyncTask getGroupTask =
                new PostResponseAsyncTask(LeaderboardActivity.this, postData);

        getGroupTask.execute("http://110.76.95.150/get_group.php");

//        Toast.makeText(getApplicationContext(), "Group information is extracted", Toast.LENGTH_SHORT).show();
    }

    public void initLeaderBoard() {
        HashMap postData = new HashMap();
        postData.put("ginfo_id", "group-1");

        PostResponseAsyncTask getGroupUsersTask =
                new PostResponseAsyncTask(LeaderboardActivity.this, postData);

        getGroupUsersTask.execute("http://110.76.95.150/get_groupusers.php");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                recordUserLog("LeaderboardActivity", "goHome");
                onBackPressed();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        stopService(new Intent(this, FloatingButtonService.class));

        super.onResume();
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


    public void mOnClick(View v) {
        switch (v.getId()) {
            case R.id.leaderboard_btn_findgroup:
                Toast.makeText(getApplicationContext(), "We are sorry.\nThis version of prototype\ndoes not support\n<Find a new group> function.", Toast.LENGTH_LONG).show();
                leaderboard_btn_findgroup.setEnabled(false);
                leaderboard_btn_findgroup.setBackgroundColor(Color.GRAY);
                leaderboard_btn_findgroup.setTextColor(Color.LTGRAY);
                break;
        }
    }

    @Override
    public void processFinish(String output) {
        String temp = output.replaceAll("<br>", "\n");
        Log.d("ExtractGroupUsers", "[LeaderboardActivity] output: " + output);

        Log.d("ResultGroupUsers", "output: " + output);


//        memberList.add(new UserInfo(true, "SG", "topmaze", "sunggeun", 231));
//        memberList.add(new UserInfo(false, "GS", "top", "sung", 31));
//        memberList.add(new UserInfo(false, "1S2G", "maze", "geun", 21));
//        memberList.add(new UserInfo(true, "S4G", "A431t9", "soony", 281));
//        memberList.add(new UserInfo(true, "S464G", "apsov93", "hyang", 631));
//
//        memberListAdapter.notifyDataSetChanged();

        if (output.contains("{\"result_groupusers\":")) {
            String jsonString = output.replace("{\"result_groupusers\":", "");
            jsonString = jsonString.substring(0, jsonString.length() - 1);

            Log.d("ResultGroupUsers", "jsonString: " + jsonString);

            try {
                JSONArray jarray = new JSONArray(jsonString);
                Log.d("ResultGroupUsers", "jarray size: " + jarray.length());

                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject jObject = jarray.getJSONObject(i);

                    UserInfo tmpUserInfo = new UserInfo();

                    if (jObject.getInt("user_gender") == 0) {
                        tmpUserInfo.setMale(true);
                    } else {
                        tmpUserInfo.setMale(false);
                    }
                    tmpUserInfo.setUserId(jObject.getString("user_id"));
                    tmpUserInfo.setUserName(jObject.getString("user_name"));
                    tmpUserInfo.setUserNickName(jObject.getString("user_nick"));
                    tmpUserInfo.setUserExp(jObject.getInt("user_exp"));

                    memberList.add(tmpUserInfo);
                }

                memberListAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "Group leaderboard is updated", Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        else if (output.contains("{\"result_group\":")) {

            String jsonString = output.replace("{\"result_group\":", "");
            jsonString = jsonString.substring(0, jsonString.length() - 1);

            try {
                JSONArray jarray = new JSONArray(jsonString);

                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject jObject = jarray.getJSONObject(i);

                    String tmpGroupId = jObject.getString("group_id");
                    String tmpGroupGoal = jObject.getString("group_goal");
                    String tmpGroupName = jObject.getString("group_name");
                    String tmpGroupLeader = jObject.getString("group_leader");
                    int tmpGroupUserNum = jObject.getInt("group_usernum");
                    String tmpGroupDate = jObject.getString("group_date");
                    String tmpGroupDesc = jObject.getString("group_desc");

                    leaderboard_tv_groupname.setText(tmpGroupName);
                    leaderboard_tv_groupgoal.setText(" - Group's goal: " + tmpGroupGoal);
                    leaderboard_tv_groupleader.setText(" - Group's leader: " + tmpGroupLeader);
                    leaderboard_tv_groupmemnum.setText(tmpGroupUserNum + "");

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public class UserInfo{
        public String userName;
        public boolean isMale;
        public String userNickName;
        public String userId;
        public int userExp;

        public UserInfo() {
        }

        public UserInfo(boolean isMale, String userNickName, String userId, String userName, int userExp){
            this.isMale =  isMale;
            this.userNickName = userNickName;
            this.userId = userId;
            this.userExp = userExp;
            this.userName = userName;

        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public boolean isMale() {
            return isMale;
        }

        public void setMale(boolean male) {
            isMale = male;
        }

        public String getUserNickName() {
            return userNickName;
        }

        public void setUserNickName(String userNickName) {
            this.userNickName = userNickName;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public int getUserExp() {
            return userExp;
        }

        public void setUserExp(int userExp) {
            this.userExp = userExp;
        }
    }

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
                new PostResponseAsyncTask(LeaderboardActivity.this, postData);

        createUserLogTask.execute("http://110.76.95.150/create_userlog.php");
        Log.d("USER_LOG", "[" + logTimestamp + "] " + logCurActivity + " - " + logCurEvent);
    }
}
