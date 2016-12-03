package com.example.user.onedaynquestions.view.testactivity;

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

import com.example.user.onedaynquestions.R;
import com.example.user.onedaynquestions.model.MyHereAgent;
import com.example.user.onedaynquestions.model.MyRoutine;
import com.example.user.onedaynquestions.view.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ymbaek on 2016-04-18.
 */
public class DBLocalTestActivity extends AppCompatActivity {

    public static final String TAG = "MyAchievements";

    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testdb_local);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("Database Test: Local(Client) Side");
        toolbar.setNavigationIcon(R.mipmap.ic_menu_arrow_back);
        toolbar.setTitleTextColor(Color.WHITE);

        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("Database Test: Local(Client) Side");
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);


    }





    public void mOnClick(View v) {
        switch (v.getId()) {

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

}
