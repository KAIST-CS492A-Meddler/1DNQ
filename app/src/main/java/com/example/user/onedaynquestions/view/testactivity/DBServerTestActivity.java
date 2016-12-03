package com.example.user.onedaynquestions.view.testactivity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.user.onedaynquestions.R;

/**
 * Created by ymbaek on 2016-04-18.
 */
public class DBServerTestActivity extends AppCompatActivity {

    public static final String TAG = "MyAchievements";

    private Toolbar toolbar;


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
