package com.example.user.onedaynquestions.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.onedaynquestions.R;
import com.example.user.onedaynquestions.model.MyCard;
import com.example.user.onedaynquestions.service.WakefulPushReceiver;

/**
 * Created by user on 2016-06-07.
 */
public class CardEvaluationActivity extends AppCompatActivity {


    public static Activity thisActivity;

    Context mContext;


    int count;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        count = 0;
        thisActivity = this;
        setContentView(R.layout.activity_cardevaluation);


    }


    @Override
    public void onPause(){
        super.onPause();
    }

    public void mOnClick(View v) {
        switch (v.getId()) {
            case R.id.cardeval_btn_submit:
                showAddAgentDialog();
                break;
        }
    }

    private void showAddAgentDialog() {
        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.dialog_endevaluation, null);

//        EditText et_addagent_macid = (EditText) dialogView.findViewById(R.id.dialog_agent_macid);
//        final EditText et_addagent_name = (EditText) dialogView.findViewById(R.id.dialog_agent_name);
//        EditText et_addagent_majorid = (EditText) dialogView.findViewById(R.id.dialog_agent_majorid);
//        EditText et_addagent_minorid = (EditText) dialogView.findViewById(R.id.dialog_agent_minorid);
//
//        et_addagent_macid.setText(selectedNewAgent.getMyeqMacId());
//        et_addagent_majorid.setText(selectedNewAgent.getMyeqBeaconMajorId());
//        et_addagent_minorid.setText(selectedNewAgent.getMyeqBeaconMinorId());

        AlertDialog.Builder builder = new AlertDialog.Builder(CardEvaluationActivity.this);
        builder.setTitle("");
        builder.setView(dialogView);
        builder.setPositiveButton("Add agent", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

//                if (selectedNewAgent.getMyeqBeaconMajorId() == "") {
//                    Toast.makeText(getApplicationContext(), "This device is not compatible for HERE", Toast.LENGTH_SHORT).show();
//                } else {
//                    selectedNewAgent.setMyeqName(et_addagent_name.getText().toString());
//                    MainActivity.hereDB.insertHereAgent(selectedNewAgent);
//                    Toast.makeText(getApplicationContext(), "An agent is added into DB", Toast.LENGTH_SHORT).show();
//
//                    myHereAgents.clear();
//                    if (MainActivity.hereDB.getAllMyHereAgents() != null)
//                        myHereAgents = MainActivity.hereDB.getAllMyHereAgents();
//                    adapter.notifyDataSetChanged();
//
//                    TextView textView = (TextView) findViewById(R.id.setting_myeq_tv_registered);
//                    if (myHereAgents.size() != 0) {
//                        textView.setVisibility(View.GONE);
//                    }
//                }

//                ((Activity) getApplicationContext()).finish();
                finish();


                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
                Intent intent_gomain = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent_gomain);

                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
}
