package com.example.user.onedaynquestions.view.testactivity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.onedaynquestions.R;
import com.example.user.onedaynquestions.controller.QuestionListAdapter;
import com.example.user.onedaynquestions.model.AsyncResponse;
import com.example.user.onedaynquestions.model.MyCard;
import com.example.user.onedaynquestions.service.WakefulPushReceiver;
import com.example.user.onedaynquestions.utility.PostResponseAsyncTask;
import com.example.user.onedaynquestions.view.activity.SettingMyInfoActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ymbaek on 2016-04-18.
 */
public class DBServerTestActivity extends AppCompatActivity implements AsyncResponse {

    public static final String TAG = "MyAchievements";

    private Toolbar toolbar;
    private ListView lvQuestionList;

    /* Get user information */
    private EditText dbtest_server_et_userid;
    private Button dbtest_server_btn_getuser;
    private TextView dbtest_server_tv_selecteduser;

    /* Get card information */
    private EditText dbtest_server_et_cardid;
    private Button dbtest_server_btn_getcard;
    private TextView dbtest_server_tv_selectedcard;

    /* Get group information */
    private EditText dbtest_server_et_groupid;
    private Button dbtest_server_btn_getgroup;
    private TextView dbtest_server_tv_selectedgroup;

    /* Get group information */
    private EditText dbtest_server_et_cardsolveid;
    private EditText dbtest_server_et_carddifficulty;
    private EditText dbtest_server_et_cardquality;
    private EditText dbtest_server_et_cardsolved;
    private Button dbtest_server_btn_solvecard;
    private TextView dbtest_server_tv_updatedcard;

    /* Get user's cards */
    private EditText dbtest_server_et_useridforcards;
    private Button dbtest_server_btn_getuserscards;
    private TextView dbtest_server_tv_userscards;

    /* Get user's groups */
    private EditText dbtest_server_et_useridforgroups;
    private Button dbtest_server_btn_getusersgroups;
    private TextView dbtest_server_tv_usersgroups;

    private ArrayList<MyCard> questionList;
    private QuestionListAdapter questionListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testdb_server);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("Database Test: Server Side");
        toolbar.setNavigationIcon(R.mipmap.ic_menu_arrow_back);
        toolbar.setTitleTextColor(Color.WHITE);

        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("Database Test: Server Side");
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
        questionList = new ArrayList<>();
        questionListAdapter = new QuestionListAdapter(this, questionList);

        lvQuestionList = (ListView) findViewById(R.id.questionlistTest);


        lvQuestionList.setAdapter(questionListAdapter);

//        Question eq1 = new Question("Q01", "hi", "안녕", "2016-11-18");
//        Question eq2 = new Question("Q02", "hello", "안녕", "2016-11-18");
//        Question eq3 = new Question("Q03", "ah", "아", "2016-11-15");
//        Question eq4 = new Question("Q04", "good night", "잘자", "2016-11-16");

//        questionList.add(eq1);
//        questionList.add(eq2);
//        questionList.add(eq3);
//        questionList.add(eq4);

//        while(!TemporalStorage.isEmpty()){
//            questionList.add(TemporalStorage.consumeReceivedQuestions());
//        }
        questionList.addAll(WakefulPushReceiver.getAllReceivedQuestions());

        questionListAdapter.notifyDataSetChanged();

        dbtest_server_et_userid = (EditText) findViewById(R.id.dbtest_server_et_userid);
        dbtest_server_btn_getuser = (Button) findViewById(R.id.dbtest_server_btn_getuser);
        dbtest_server_tv_selecteduser = (TextView) findViewById(R.id.dbtest_server_tv_selecteduser);

        dbtest_server_et_cardid = (EditText) findViewById(R.id.dbtest_server_et_cardid);
        dbtest_server_btn_getcard = (Button) findViewById(R.id.dbtest_server_btn_getcard);
        dbtest_server_tv_selectedcard = (TextView) findViewById(R.id.dbtest_server_tv_selectedcard);

        dbtest_server_et_groupid = (EditText) findViewById(R.id.dbtest_server_et_groupid) ;
        dbtest_server_btn_getgroup = (Button) findViewById(R.id.dbtest_server_btn_getgroup);
        dbtest_server_tv_selectedgroup = (TextView) findViewById(R.id.dbtest_server_tv_selectedgroup);

        dbtest_server_et_cardsolveid = (EditText) findViewById(R.id.dbtest_server_et_cardsolveid);
        dbtest_server_et_carddifficulty = (EditText) findViewById(R.id.dbtest_server_et_carddifficulty);
        dbtest_server_et_cardquality = (EditText) findViewById(R.id.dbtest_server_et_cardquality);
        dbtest_server_et_cardsolved = (EditText) findViewById(R.id.dbtest_server_et_cardsolved);
        dbtest_server_btn_solvecard = (Button) findViewById(R.id.dbtest_server_btn_solvecard);
        dbtest_server_tv_updatedcard = (TextView) findViewById(R.id.dbtest_server_tv_updatedcard);

        dbtest_server_et_useridforcards = (EditText) findViewById(R.id.dbtest_server_et_useridforcards);
        dbtest_server_btn_getuserscards = (Button) findViewById(R.id.dbtest_server_btn_getuserscards);
        dbtest_server_tv_userscards = (TextView) findViewById(R.id.dbtest_server_tv_userscards);

        dbtest_server_et_useridforgroups = (EditText) findViewById(R.id.dbtest_server_et_useridforgroups);
        dbtest_server_btn_getusersgroups = (Button) findViewById(R.id.dbtest_server_btn_getusersgroups);
        dbtest_server_tv_usersgroups = (TextView) findViewById(R.id.dbtest_server_tv_usersgroups);
    }





    public void mOnClick(View v) {
        switch (v.getId()) {
            case R.id.dbtest_server_btn_getuser:
                if (dbtest_server_et_userid.getText().toString().length() != 0) {
                    HashMap postData = new HashMap();
                    postData.put("userinfo_id", dbtest_server_et_userid.getText().toString());

                    PostResponseAsyncTask getUserTask =
                            new PostResponseAsyncTask(DBServerTestActivity.this, postData);

                    getUserTask.execute("http://110.76.95.150/get_user.php");

                    Toast.makeText(getApplicationContext(), "User information is extracted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Please fill out user_id to be selected", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.dbtest_server_btn_getcard:
                if (dbtest_server_et_cardid.getText().toString().length() != 0) {
                    HashMap postData = new HashMap();
                    postData.put("cinfo_id", dbtest_server_et_cardid.getText().toString());

                    PostResponseAsyncTask getCardTask =
                            new PostResponseAsyncTask(DBServerTestActivity.this, postData);

                    getCardTask.execute("http://110.76.95.150/get_card.php");

                    Toast.makeText(getApplicationContext(), "Card information is extracted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Please fill out card_id to be selected", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.dbtest_server_btn_getgroup:
                if (dbtest_server_et_groupid.getText().toString().length() != 0) {
                    HashMap postData = new HashMap();
                    postData.put("ginfo_id", dbtest_server_et_groupid.getText().toString());

                    PostResponseAsyncTask getGroupTask =
                            new PostResponseAsyncTask(DBServerTestActivity.this, postData);

                    getGroupTask.execute("http://110.76.95.150/get_group.php");

                    Toast.makeText(getApplicationContext(), "Group information is extracted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Please fill out group_id to be selected", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.dbtest_server_btn_solvecard:
                String cardSolveId = "";
                cardSolveId = dbtest_server_et_cardsolveid.getText().toString();
//                int cardDifficulty = Integer.parseInt(dbtest_server_et_carddifficulty.getText().toString());
//                int cardQuality = Integer.parseInt(dbtest_server_et_cardquality.getText().toString());
//                int cardSolved = Integer.parseInt(dbtest_server_et_cardsolveid.getText().toString());

                if (!cardSolveId.equals("")) {
                    HashMap postData = new HashMap();
                    postData.put("cinfo_id", dbtest_server_et_cardsolveid.getText().toString());
                    postData.put("cinfo_difficulty", dbtest_server_et_carddifficulty.getText().toString());
                    postData.put("cinfo_quality", dbtest_server_et_cardquality.getText().toString());
                    postData.put("cinfo_right", dbtest_server_et_cardsolveid.getText().toString());

                    PostResponseAsyncTask updateCardTask =
                            new PostResponseAsyncTask(DBServerTestActivity.this, postData);

                    updateCardTask.execute("http://110.76.95.150/solve_problem.php");

                    Toast.makeText(getApplicationContext(), "Card information is updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Please fill out card_id to be updated", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.dbtest_server_btn_getuserscards:
                if (dbtest_server_et_useridforcards.getText().toString().length() != 0) {
                    HashMap postData = new HashMap();
                    postData.put("cinfo_maker", dbtest_server_et_useridforcards.getText().toString());

                    PostResponseAsyncTask getUsersCardsTask =
                            new PostResponseAsyncTask(DBServerTestActivity.this, postData);

                    getUsersCardsTask.execute("http://110.76.95.150/get_cardbymaker.php");

                    Toast.makeText(getApplicationContext(), "User's cards are selected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Please fill out user_id to be selected", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.dbtest_server_btn_getusersgroups:
                if (dbtest_server_et_useridforgroups.getText().toString().length() != 0) {
                    HashMap postData = new HashMap();
                    postData.put("userinfo_id", dbtest_server_et_useridforgroups.getText().toString());

                    PostResponseAsyncTask getUsersGroupsTask =
                            new PostResponseAsyncTask(DBServerTestActivity.this, postData);

                    getUsersGroupsTask.execute("http://110.76.95.150/get_groupbyuser.php");

                    Toast.makeText(getApplicationContext(), "User's groups are selected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Please fill out user_id to be selected", Toast.LENGTH_SHORT).show();
                }
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

    @Override
    public void processFinish(String output) {
        String temp = output.replaceAll("<br>", "\n");
        Log.d("JSONParser", "[DBServerTestActivity] output: " + output);
//        Log.d("JSONParser", "[DBServerTestActivity] output.substring(0, 2): " + output.substring(0, 2));
//        Log.d("JSONParser", "[DBServerTestActivity] output.substring(0): " + output.substring(0));
//        Log.d("JSONParser", "[DBServerTestActivity] output.substring(1): " + output.substring(1));


        // PARSE USER
        if (output.contains("{\"result_user\":")) {
            Log.d("JSONParser", "[DBServerTestActivity] 1");

            dbtest_server_tv_selecteduser.setText(temp + "\n");

            String jsonString = output.replace("{\"result_user\":", "");
            Log.d("JSONParser", "[DBServerTestActivity] (user) jsonString-1: " + jsonString);
            jsonString = jsonString.substring(0, jsonString.length() - 1);
            Log.d("JSONParser", "[DBServerTestActivity] (user) jsonString-2: " + jsonString);

            try {
                Log.d("JSONParser", "[DBServerTestActivity] (user) jarray is created.");
                JSONArray jarray = new JSONArray(jsonString);

                Log.d("JSONParser", "[DBServerTestActivity] (user) jarray.length(): " + jarray.length());

                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject jObject = jarray.getJSONObject(i);

                    String tmpUserId = jObject.getString("user_id");
                    String tmpUserName = jObject.getString("user_name");
                    String tmpUserNick = jObject.getString("user_nick");
                    int tmpUserAge = jObject.getInt("user_age");
                    int tmpUserGender = jObject.getInt("user_gender");
                    String tmpUserDeviceId = jObject.getString("user_deviceid");
                    String tmpUserToken = jObject.getString("user_token");

                    dbtest_server_tv_selecteduser.append("\n" + tmpUserId);
                    dbtest_server_tv_selecteduser.append("\n" + tmpUserName);
                    dbtest_server_tv_selecteduser.append("\n" + tmpUserNick);
                    dbtest_server_tv_selecteduser.append("\n" + tmpUserAge);
                    dbtest_server_tv_selecteduser.append("\n" + tmpUserGender);
                    dbtest_server_tv_selecteduser.append("\n" + tmpUserDeviceId);
                    dbtest_server_tv_selecteduser.append("\n" + tmpUserToken);

                    Log.d("JSONParser", "[DBServerTestActivity] user_name: " + tmpUserName);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        // PASRE CARD
        else if (output.contains("{\"result_card\":")) {

            dbtest_server_tv_selectedcard.setText(temp + "\n");

            String jsonString = output.replace("{\"result_card\":", "");
            Log.d("JSONParser", "[DBServerTestActivity] (card) jsonString-1: " + jsonString);
            jsonString = jsonString.substring(0, jsonString.length() - 1);
            Log.d("JSONParser", "[DBServerTestActivity] (card) jsonString-2: " + jsonString);

            try {
                Log.d("JSONParser", "[DBServerTestActivity] (card) jarray is created.");
                JSONArray jarray = new JSONArray(jsonString);

                Log.d("JSONParser", "[DBServerTestActivity] (card) jarray.length(): " + jarray.length());

                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject jObject = jarray.getJSONObject(i);

                    String tmpCardId = jObject.getString("card_id");
                    String tmpCardDateTime = jObject.getString("card_datetime");
                    int tmpCardType = jObject.getInt("card_type");
                    String tmpCardMaker = jObject.getString("card_maker");
                    String tmpCardGroup = jObject.getString("card_group");
                    String tmpCardQuestion = jObject.getString("card_question");
                    String tmpCardAnswer = jObject.getString("card_answer");
                    String tmpCardHint = jObject.getString("card_hint");
                    float tmpCardDifficulty = (float) jObject.getDouble("card_difficulty");
                    float tmpCardQuality = (float) jObject.getDouble("card_quality");
                    int tmpCardSolvedNum = jObject.getInt("card_solvednum");
                    int tmpCardEvalNum = jObject.getInt("card_evalnum");


                    dbtest_server_tv_selectedcard.append("\n" + tmpCardId);
                    dbtest_server_tv_selectedcard.append("\n" + tmpCardDateTime);
                    dbtest_server_tv_selectedcard.append("\n" + tmpCardType);
                    dbtest_server_tv_selectedcard.append("\n" + tmpCardMaker);
                    dbtest_server_tv_selectedcard.append("\n" + tmpCardGroup);
                    dbtest_server_tv_selectedcard.append("\n" + tmpCardQuestion);
                    dbtest_server_tv_selectedcard.append("\n" + tmpCardAnswer);
                    dbtest_server_tv_selectedcard.append("\n" + tmpCardHint);
                    dbtest_server_tv_selectedcard.append("\n" + tmpCardDifficulty);
                    dbtest_server_tv_selectedcard.append("\n" + tmpCardQuality);
                    dbtest_server_tv_selectedcard.append("\n" + tmpCardSolvedNum);
                    dbtest_server_tv_selectedcard.append("\n" + tmpCardEvalNum);


//                    Log.d("JSONParser", "[DBServerTestActivity] user_name: " + tmpUserName);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // PARSE GROUP
        else if (output.contains("{\"result_group\":")) {
            dbtest_server_tv_selectedgroup.setText(temp + "\n");

            String jsonString = output.replace("{\"result_group\":", "");
            Log.d("JSONParser", "[DBServerTestActivity] (group) jsonString-1: " + jsonString);
            jsonString = jsonString.substring(0, jsonString.length() - 1);
            Log.d("JSONParser", "[DBServerTestActivity] (group) jsonString-2: " + jsonString);

            try {
                Log.d("JSONParser", "[DBServerTestActivity] (card) jarray is created.");
                JSONArray jarray = new JSONArray(jsonString);

                Log.d("JSONParser", "[DBServerTestActivity] (card) jarray.length(): " + jarray.length());

                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject jObject = jarray.getJSONObject(i);

                    String tmpGroupId = jObject.getString("group_id");
                    String tmpGroupGoal = jObject.getString("group_goal");
                    String tmpGroupName = jObject.getString("group_name");
                    String tmpGroupLeader = jObject.getString("group_leader");
                    int tmpGroupUserNum = jObject.getInt("group_usernum");
                    String tmpGroupDate = jObject.getString("group_date");
                    String tmpGroupDesc = jObject.getString("group_desc");


                    dbtest_server_tv_selectedgroup.append("\n" + tmpGroupId);
                    dbtest_server_tv_selectedgroup.append("\n" + tmpGroupGoal);
                    dbtest_server_tv_selectedgroup.append("\n" + tmpGroupName);
                    dbtest_server_tv_selectedgroup.append("\n" + tmpGroupLeader);
                    dbtest_server_tv_selectedgroup.append("\n" + tmpGroupUserNum);
                    dbtest_server_tv_selectedgroup.append("\n" + tmpGroupDate);
                    dbtest_server_tv_selectedgroup.append("\n" + tmpGroupDesc);


//                    Log.d("JSONParser", "[DBServerTestActivity] user_name: " + tmpUserName);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // PARSE USER-GROUP
        else if (output.contains("{\"result_usergroup\":")) {

        }        // PARSE USER-GROUP
        else if (output.contains("{\"result_solveproblem\":")) {

            dbtest_server_tv_updatedcard.setText(temp + "\n");

            String jsonString = output.replace("{\"result_solveproblem\":", "");
            Log.d("JSONParser", "[DBServerTestActivity] (solveproblem) jsonString-1: " + jsonString);
            jsonString = jsonString.substring(0, jsonString.length() - 1);
            Log.d("JSONParser", "[DBServerTestActivity] (solveproblem) jsonString-2: " + jsonString);

            try {
                Log.d("JSONParser", "[DBServerTestActivity] (solveproblem) jarray is created.");
                JSONArray jarray = new JSONArray(jsonString);

                Log.d("JSONParser", "[DBServerTestActivity] (solveproblem) jarray.length(): " + jarray.length());

                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject jObject = jarray.getJSONObject(i);

                    String tmpCardId = jObject.getString("card_id");
                    String tmpCardDateTime = jObject.getString("card_datetime");
                    int tmpCardType = jObject.getInt("card_type");
                    String tmpCardMaker = jObject.getString("card_maker");
                    String tmpCardGroup = jObject.getString("card_group");
                    String tmpCardQuestion = jObject.getString("card_question");
                    String tmpCardAnswer = jObject.getString("card_answer");
                    String tmpCardHint = jObject.getString("card_hint");
                    float tmpCardDifficulty = (float) jObject.getDouble("card_difficulty");
                    float tmpCardQuality = (float) jObject.getDouble("card_quality");
                    int tmpCardSolvedNum = jObject.getInt("card_solvednum");
                    int tmpCardEvalNum = jObject.getInt("card_evalnum");


                    dbtest_server_tv_updatedcard.append("\n" + tmpCardId);
                    dbtest_server_tv_updatedcard.append("\n" + tmpCardDateTime);
                    dbtest_server_tv_updatedcard.append("\n" + tmpCardType);
                    dbtest_server_tv_updatedcard.append("\n" + tmpCardMaker);
                    dbtest_server_tv_updatedcard.append("\n" + tmpCardGroup);
                    dbtest_server_tv_updatedcard.append("\n" + tmpCardQuestion);
                    dbtest_server_tv_updatedcard.append("\n" + tmpCardAnswer);
                    dbtest_server_tv_updatedcard.append("\n" + tmpCardHint);
                    dbtest_server_tv_updatedcard.append("\n" + tmpCardDifficulty);
                    dbtest_server_tv_updatedcard.append("\n" + tmpCardQuality);
                    dbtest_server_tv_updatedcard.append("\n" + tmpCardSolvedNum);
                    dbtest_server_tv_updatedcard.append("\n" + tmpCardEvalNum);


//                    Log.d("JSONParser", "[DBServerTestActivity] user_name: " + tmpUserName);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // PARSE USER'S CARDS
        else if (output.contains("{\"result_userscards\":")) {
            dbtest_server_tv_userscards.setText(temp + "\n");

            String jsonString = output.replace("{\"result_userscards\":", "");
            Log.d("JSONParser", "[DBServerTestActivity] (userscards) jsonString-1: " + jsonString);
            jsonString = jsonString.substring(0, jsonString.length() - 1);
            Log.d("JSONParser", "[DBServerTestActivity] (userscards) jsonString-2: " + jsonString);

            //TODO: JSON Parsing
            try {
                Log.d("JSONParser", "[DBServerTestActivity] (userscards) jarray is created.");
                JSONArray jarray = new JSONArray(jsonString);

                Log.d("JSONParser", "[DBServerTestActivity] (userscards) jarray.length(): " + jarray.length());

                dbtest_server_tv_userscards.setText("");

                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject jObject = jarray.getJSONObject(i);

                    String tmpCardId = jObject.getString("card_id");
                    String tmpCardDateTime = jObject.getString("card_datetime");
                    int tmpCardType = jObject.getInt("card_type");
                    String tmpCardMaker = jObject.getString("card_maker");
                    String tmpCardGroup = jObject.getString("card_group");
                    String tmpCardQuestion = jObject.getString("card_question");
                    String tmpCardAnswer = jObject.getString("card_answer");
                    String tmpCardHint = jObject.getString("card_hint");
                    float tmpCardDifficulty = (float) jObject.getDouble("card_difficulty");
                    float tmpCardQuality = (float) jObject.getDouble("card_quality");
                    int tmpCardSolvedNum = jObject.getInt("card_solvednum");
                    int tmpCardEvalNum = jObject.getInt("card_evalnum");

                    dbtest_server_tv_userscards.append("\n" + tmpCardId);
                    dbtest_server_tv_userscards.append(" " + tmpCardDateTime);
                    dbtest_server_tv_userscards.append(" " + tmpCardType);
                    dbtest_server_tv_userscards.append(" " + tmpCardMaker);
                    dbtest_server_tv_userscards.append(" " + tmpCardGroup);
                    dbtest_server_tv_userscards.append(" " + tmpCardQuestion);
                    dbtest_server_tv_userscards.append(" " + tmpCardAnswer);
                    dbtest_server_tv_userscards.append(" " + tmpCardHint);
                    dbtest_server_tv_userscards.append(" " + tmpCardDifficulty);
                    dbtest_server_tv_userscards.append(" " + tmpCardQuality);
                    dbtest_server_tv_userscards.append(" " + tmpCardSolvedNum);
                    dbtest_server_tv_userscards.append(" " + tmpCardEvalNum + "\n");


//                    Log.d("JSONParser", "[DBServerTestActivity] user_name: " + tmpUserName);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // PARSE USER'S CARDS
        else if (output.contains("{\"result_usersgroups\":")) {
            dbtest_server_tv_usersgroups.setText(temp + "\n");


            String jsonString = output.replace("{\"result_usersgroups\":", "");
            Log.d("JSONParser", "[DBServerTestActivity] (usersgroups) jsonString-1: " + jsonString);
            jsonString = jsonString.substring(0, jsonString.length() - 1);
            Log.d("JSONParser", "[DBServerTestActivity] (usersgroups) jsonString-2: " + jsonString);

            try {
                Log.d("JSONParser", "[DBServerTestActivity] (usersgroups) jarray is created.");
                JSONArray jarray = new JSONArray(jsonString);

                Log.d("JSONParser", "[DBServerTestActivity] (usersgroups) jarray.length(): " + jarray.length());

                if (jarray.length() != 0) {
                    dbtest_server_tv_usersgroups.setText("");
                }

                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject jObject = jarray.getJSONObject(i);

                    String tmpGroupId = jObject.getString("group_id");
                    String tmpGroupGoal = jObject.getString("group_goal");
                    String tmpGroupName = jObject.getString("group_name");
                    String tmpGroupLeader = jObject.getString("group_leader");
                    int tmpGroupUserNum = jObject.getInt("group_usernum");
                    String tmpGroupDate = jObject.getString("group_date");
                    String tmpGroupDesc = jObject.getString("group_desc");


                    dbtest_server_tv_usersgroups.append("\n" + tmpGroupId);
                    dbtest_server_tv_usersgroups.append(" " + tmpGroupGoal);
                    dbtest_server_tv_usersgroups.append(" " + tmpGroupName);
                    dbtest_server_tv_usersgroups.append(" " + tmpGroupLeader);
                    dbtest_server_tv_usersgroups.append(" " + tmpGroupUserNum);
                    dbtest_server_tv_usersgroups.append(" " + tmpGroupDate);
                    dbtest_server_tv_usersgroups.append(" " + tmpGroupDesc + "\n");


//                    Log.d("JSONParser", "[DBServerTestActivity] user_name: " + tmpUserName);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }



//        Toast.makeText(getApplicationContext(), temp, Toast.LENGTH_LONG).show();
    }
}
