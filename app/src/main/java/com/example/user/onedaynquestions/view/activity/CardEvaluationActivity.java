package com.example.user.onedaynquestions.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.onedaynquestions.R;
import com.example.user.onedaynquestions.model.AsyncResponse;
import com.example.user.onedaynquestions.model.MyCard;
import com.example.user.onedaynquestions.service.FloatingButtonService;
import com.example.user.onedaynquestions.utility.LocalDBController;
import com.example.user.onedaynquestions.utility.PostResponseAsyncTask;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by user on 2016-06-07.
 */
public class CardEvaluationActivity extends AppCompatActivity implements AsyncResponse {

    public static final String TAG_DB = "LocalDatabase";


    public static Activity thisActivity;

    Context mContext;

    private RatingBar cardeval_rb_usefulness;
    private RatingBar cardeval_rb_difficulty;
    private ImageView cardeval_iv_star;

    private Button cardeval_btn_submit;

    private String card_id;
    private int self_eval;
    private int card_star = 0;

    int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        card_id = intent.getExtras().getString("card_id");
        self_eval = intent.getExtras().getInt("self_eval");

        count = 0;
        thisActivity = this;
        setContentView(R.layout.activity_cardevaluation);

        cardeval_rb_usefulness = (RatingBar) findViewById(R.id.cardeval_rb_usefulness);
        cardeval_rb_difficulty = (RatingBar) findViewById(R.id.cardeval_rb_difficulty);
        cardeval_iv_star = (ImageView) findViewById(R.id.cardeval_iv_star);

        cardeval_btn_submit = (Button) findViewById(R.id.cardeval_btn_submit);

        recordUserLog("CardEvaluationActivity", "onCreate");


    }

    @Override
    protected void onResume() {
        stopService(new Intent(this, FloatingButtonService.class));

        super.onResume();
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    public void mOnClick(View v) {
        switch (v.getId()) {
            case R.id.cardeval_btn_submit:
                recordUserLog("CardEvaluationActivity", "submitEvaluation");

//                Toast.makeText(getApplicationContext(), "card_id: " + card_id +
//                    "\nusefulness: " + cardeval_rb_usefulness.getRating() +
//                    "\ndifficulty: " + cardeval_rb_difficulty.getRating() +
//                    "\nself_eval: " + self_eval, Toast.LENGTH_SHORT).show();

                HashMap postData = new HashMap();
                postData.put("userinfo_id", MainActivity.odnqDB.getMyInfo().getMyInfoId());
                postData.put("cinfo_id", card_id);
                postData.put("cinfo_difficulty", cardeval_rb_usefulness.getRating() + "");
                postData.put("cinfo_quality", cardeval_rb_difficulty.getRating() + "");
                postData.put("cinfo_right", self_eval + "");

                PostResponseAsyncTask updateCardTask =
                        new PostResponseAsyncTask(CardEvaluationActivity.this, postData);

                updateCardTask.execute("http://110.76.95.150/solve_problem.php");

//                Toast.makeText(getApplicationContext(), "Card information is updated", Toast.LENGTH_SHORT).show();

                MainActivity.odnqDB = new LocalDBController(getApplicationContext());
                cardeval_btn_submit.setEnabled(false);

                if (MainActivity.odnqDB != null) {
                    ArrayList<MyCard> cardList = MainActivity.odnqDB.getMyCards(1, MainActivity.odnqDB.getMyInfo().getMyInfoId());

                    if (cardList != null) {
                        for (int i = 0; i < cardList.size(); i++) {
                            Log.d("CardEvaluation", "[" + i + "] card_id: " + cardList.get(i).getMyCardId());
                            Log.d("CardEvaluation", "[" + i + "] card_question: " + cardList.get(i).getMyCardQuestion());
                            Log.d("CardEvaluation", "[" + i + "] card_answer: " + cardList.get(i).getMyCardAnswer());
                        }
                    }

                    Log.d("CardEvaluation", "card_id: " + card_id);
                    MyCard tmpMyCard = MainActivity.odnqDB.getMyCardWithId(card_id);

                    if (tmpMyCard != null) {
                        tmpMyCard.setMyCardDifficulty((int) cardeval_rb_difficulty.getRating());
                        tmpMyCard.setMyCardQuality((int) cardeval_rb_usefulness.getRating());
                        if (self_eval == 0) {
                            tmpMyCard.setMyCardWrong(tmpMyCard.getMyCardWrong() + 1);
                        } else {
                            //Initialize wrong num
                            tmpMyCard.setMyCardWrong(0);
                        }


                        if (card_star == 0) {
                            tmpMyCard.setMyCardStarred(0);
                        } else {
                            tmpMyCard.setMyCardStarred(1);
                        }

                    } else {
                        Log.d("CardEvaluation", "tmpMyCard is null");
                    }


                    MainActivity.odnqDB.updateMyCard(tmpMyCard);
                    Log.d(TAG_DB, "[CardEvaluationActivity] Card information is updated");

                    showEndCardEvalDialog();
                } else {
                    Log.d("CardEvaluation", "MainActivity.odnqDB is null");

                }
                break;

            case R.id.cardeval_iv_star:
                recordUserLog("CardEvaluationActivity", "starCard");

                if (card_star == 0) {
                    //nostar -> star
                    Toast.makeText(getApplicationContext(), "This card is starred.", Toast.LENGTH_SHORT).show();
                    cardeval_iv_star.setImageResource(R.drawable.odnq_star_full);
                    card_star = 1;
                } else {
                    //star -> nostar
                    Toast.makeText(getApplicationContext(), "Star is removed.", Toast.LENGTH_SHORT).show();
                    cardeval_iv_star.setImageResource(R.drawable.odnq_star_empty);
                    card_star = 0;
                }
                break;
        }
    }

    private void showEndCardEvalDialog() {
        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.dialog_finishprocess, null);

        TextView dialog_endevaluation_tv_point = (TextView) dialogView.findViewById(R.id.dialog_finishprocess_point);
        TextView dialog_endevaluation_tv_content = (TextView) dialogView.findViewById(R.id.dialog_finishprocess_content);

        AlertDialog.Builder builder = new AlertDialog.Builder(CardEvaluationActivity.this);
        builder.setTitle("Thanks for the evaluation");
        builder.setView(dialogView);

        dialog_endevaluation_tv_point.setText("+30");


        // If MainActivity is ready to return
        if (MainActivity.isMainActivityReady) {
            dialog_endevaluation_tv_content.setText("Your experience is increased.\nReturn to 1DNQ.");
            builder.setPositiveButton("CLOSE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    recordUserLog("CardEvaluationActivity", "finishCardSolving - Go to 1DNQ");
                    finish();

                    dialog.dismiss();
                }
            });
        }
        // If MainActivity is not ready to return (making a new receivedCard from the floating button)
        else {
            dialog_endevaluation_tv_content.setText("Your experience is increased.\nDo you want to return to 1DNQ?");
            builder.setPositiveButton("Go to 1DNQ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    recordUserLog("CardEvaluationActivity", "finishCardSolving - Go to 1DNQ");

                    Intent intent_gomain = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent_gomain);
                    finish();

                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    recordUserLog("CardEvaluationActivity", "quitApp");
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
        Log.d("CardEvaluationActivity", "[DBServerTestActivity] output: " + output);

        if (output.contains("{\"result_solveproblem\":")) {
            Toast.makeText(getApplicationContext(), "Result is successfully reflected.", Toast.LENGTH_SHORT).show();
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
                new PostResponseAsyncTask(CardEvaluationActivity.this, postData);

        createUserLogTask.execute("http://110.76.95.150/create_userlog.php");
        Log.d("USER_LOG", "[" + logTimestamp + "] " + logCurActivity + " - " + logCurEvent);
    }
}
