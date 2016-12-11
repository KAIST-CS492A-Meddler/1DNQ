package com.example.user.onedaynquestions.view.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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

/**
 * Created by user on 2016-06-07.
 */
public class NewCardActivity extends AppCompatActivity implements AsyncResponse {

    MyRoutine selectedRoutine;

    public static Activity thisActivity;

    EditText newCard_et_maker;
    EditText newCard_et_makerid;
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
        newCard_et_makerid = (EditText) findViewById(R.id.newcard_et_makerid);
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

        newCard_et_makerid.setText(MainActivity.odnqDB.getMyInfo().getMyInfoId());
        newCard_et_makerid.setTextColor(getResources().getColor(R.color.colorSkyBlue));
//        newCard_et_makerid.setTypeface(null, Typeface.BOLD);

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


                //Toast.makeText(getApplicationContext(), "Make a new receivedCard\nInsert receivedCard data to Server DB\nSelect saved receivedCard data\nInsert selected receivedCard data to local DB", Toast.LENGTH_LONG).show();

                HashMap postData = new HashMap();
                postData.put("cinfo_answer", newCard_et_answer.getText().toString());
                postData.put("cinfo_maker", newCard_et_makerid.getText().toString());
                //postData.put(, newCard_et_datetime.getText());
                postData.put("cinfo_question", newCard_et_question.getText().toString());
                postData.put("cinfo_group", "group-1");
                postData.put("cinfo_hint", newCard_et_hint.getText().toString());
                postData.put("cinfo_type", ""+cardType);

                PostResponseAsyncTask newCardTask =
                        new PostResponseAsyncTask(NewCardActivity.this, postData);


                newCardTask.execute("http://110.76.95.150/create_card.php");
                break;
        }
    }

    private void showEndNewCardDialog() {
        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.dialog_finishprocess, null);

        TextView dialog_endnewcard_tv_point = (TextView) dialogView.findViewById(R.id.dialog_finishprocess_point);
        TextView dialog_endnewcard_tv_content = (TextView) dialogView.findViewById(R.id.dialog_finishprocess_content);

        AlertDialog.Builder builder = new AlertDialog.Builder(NewCardActivity.this);
        builder.setTitle("A receivedCard is created successfully");
        builder.setView(dialogView);

        dialog_endnewcard_tv_point.setText("+100");

        // If MainActivity is ready to return
        if (MainActivity.isMainActivityReady) {
            dialog_endnewcard_tv_content.setText("Your experience is increased.\nReturn to 1DNQ.");
            builder.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();

                    dialog.dismiss();
                }
            });
        }
        // If MainActivity is not ready to return (making a new receivedCard from the floating button)
        else {
            dialog_endnewcard_tv_content.setText("Your experience is increased.\nDo you want to return to 1DNQ?");
            builder.setPositiveButton("Go to 1DNQ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    Intent intent_gomain = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent_gomain);
                    finish();

                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    dialog.dismiss();
                }
            });
        }

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    public void processFinish(String output) {
        String temp = output.replaceAll("<br>", "\n");
//        Toast.makeText(getApplicationContext(), temp, Toast.LENGTH_LONG).show();

        if (output.contains("Card created")) {
            Toast.makeText(getApplicationContext(), "A receivedCard is created.", Toast.LENGTH_SHORT).show();

            showEndNewCardDialog();
        }

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
