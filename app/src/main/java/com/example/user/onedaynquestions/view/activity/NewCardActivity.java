package com.example.user.onedaynquestions.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.user.onedaynquestions.R;
import com.example.user.onedaynquestions.archive.MyRoutine;
import com.example.user.onedaynquestions.model.AsyncResponse;
import com.example.user.onedaynquestions.service.FloatingButtonService;
import com.example.user.onedaynquestions.utility.PostResponseAsyncTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by user on 2016-06-07.
 */
public class NewCardActivity extends AppCompatActivity implements AsyncResponse {

    MyRoutine selectedRoutine;

    public static Activity thisActivity;

    EditText newCard_et_maker;
    EditText newCard_et_datetime;
    EditText newCard_et_question;
    EditText newCard_et_answer;
    EditText newCard_et_hint;

    Spinner newCard_spinner_type;

    Button newCard_btn_add;

    private ArrayAdapter<String> mSpinnerAdapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newcard);

        Intent intent = getIntent();
        selectedRoutine = (MyRoutine) intent.getSerializableExtra("selectedRoutine");

        initWidgets();

        if (selectedRoutine == null) {
            Toast.makeText(getApplicationContext(), "Routine is not set.", Toast.LENGTH_SHORT).show();
        }

        initWidetValues();

        thisActivity = this;


    }


    private void initWidgets() {

        newCard_et_maker = (EditText) findViewById(R.id.newcard_et_maker);
        newCard_et_datetime = (EditText) findViewById(R.id.newcard_et_datetime);
        newCard_et_question = (EditText) findViewById(R.id.newcard_et_question);
        newCard_et_answer = (EditText) findViewById(R.id.newcard_et_answer);
        newCard_et_hint = (EditText) findViewById(R.id.newcard_et_hint);

        newCard_spinner_type = (Spinner) findViewById(R.id.newcard_spinner_type);

        newCard_btn_add = (Button) findViewById(R.id.newcard_btn_add);

    }

    private void initWidetValues() {

        if(MainActivity.odnqDB.getMyInfo() != null) {
            newCard_et_maker.setText(MainActivity.odnqDB.getMyInfo().getMyInfoName());
        }else{
            newCard_et_maker.setText("<empty>");
        }
        newCard_et_maker.setTextColor(getResources().getColor(R.color.colorSkyBlue));
        newCard_et_maker.setTypeface(null, Typeface.BOLD);

        newCard_et_datetime.setText(getDateTime());
        newCard_et_datetime.setTextColor(Color.DKGRAY);

        mSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
                (String[])getResources().getStringArray(R.array.spinner_list));
        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        newCard_spinner_type.setAdapter(mSpinnerAdapter);

        newCard_spinner_type.setSelection(0);
    }


    public String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

//    /**
//     *
//     * @param outputStyle   1:30개씩 2세트, 2:2세트 30개, 3:30 times X 2 sets
//     * @param rawGoal       Raw String
//     * @return              Parsed/Refined String
//     */
//    private String parseRoutineGoal(int outputStyle, String rawGoal) {
//
//        String goalSentence = "";
//
//        //Default: style 3
//        outputStyle = 3;
//
//        //Type 1. 3|15|-1
//        //Type 2. 2|-1|60
//
//        int goalType = 0;   //1: count times, 2: count secs, 3: count times & secs
//
//        String set = "";    //sets
//        String count = "";  //times
//        String time = "";   //secs
//
//        StringTokenizer tokens = new StringTokenizer(rawGoal, "|");
//        set = tokens.nextToken();
//        count = tokens.nextToken();
//        time = tokens.nextToken();
//
//        Log.d("TokenizerExercise", "set[" + set + "], count[" + count + "], time[" + "]");
//
//        //Single or multiple
//        int intSet = Integer.parseInt(set);
//        int intCount = Integer.parseInt(count);
//        int intTime = Integer.parseInt(time);
//
//        set += " SET";
//        count += " TIME";
//        time += " SEC";
//
//        //Check goal type
//        if (intCount == -1) {
//            goalType = 2;
//        }
//        if (intTime == -1) {
//            goalType = 1;
//        }
//        if (intTime != -1 && intCount != -1) {
//            goalType = 3;
//        }
//
//        if (intSet > 1) {
//            set += "S";
//        }
//        if (intCount > 1) {
//            count += "S";
//        }
//        if (intTime > 1) {
//            time += "S";
//        }
//
//        switch (goalType) {
//            //Count times (회수)
//            case 1:
//                goalSentence = count + " X " + set;
//                break;
//            //Count secs (시간)
//            case 2:
//                goalSentence = time + " X " + set;
//                break;
//            // Count both times & secs
//            case 3:
//                goalSentence = count + " X " + set + " (" + time + ")";
//                break;
//        }
//
//        return goalSentence;
//
//    }

    public void mOnClick(View v) {
        switch (v.getId()) {
            case R.id.newcard_btn_add:

                int cardType;

                cardType = newCard_spinner_type.getSelectedItemPosition();


                //Toast.makeText(getApplicationContext(), "Make a new card\nInsert card data to Server DB\nSelect saved card data\nInsert selected card data to local DB", Toast.LENGTH_LONG).show();

                HashMap postData = new HashMap();
                postData.put("cinfo_answer", newCard_et_answer.getText().toString());
                postData.put("cinfo_maker", newCard_et_maker.getText().toString());
                //postData.put(, newCard_et_datetime.getText());
                postData.put("cinfo_question", newCard_et_question.getText().toString());
                postData.put("cinfo_group", "Meddler");
                postData.put("cinfo_hint", newCard_et_hint.getText().toString());
                postData.put("cinfo_type", ""+cardType);

                PostResponseAsyncTask newCardTask =
                        new PostResponseAsyncTask(NewCardActivity.this, postData);


                newCardTask.execute("http://110.76.95.150/create_card.php");
                break;
        }
    }

    @Override
    public void processFinish(String output) {
        String temp = output.replaceAll("<br>", "\n");
        Toast.makeText(getApplicationContext(), temp, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume(){
        stopService(new Intent(this, FloatingButtonService.class));
        super.onResume();
    }


    @Override
    protected  void onPause(){
        //앱 꺼져 있을때 플로팅 버튼 뜨도록 서비스 on
        startService(new Intent(this, FloatingButtonService.class));
        super.onPause();
    }


}
