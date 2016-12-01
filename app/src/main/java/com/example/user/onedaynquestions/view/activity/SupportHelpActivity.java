package com.example.user.onedaynquestions.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.user.onedaynquestions.R;

/**
 * Created by ymbaek on 2016-04-18.
 */
public class SupportHelpActivity extends AppCompatActivity {

    public static final String TAG = "SupportHelpActivity";

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_help);

//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//
//        toolbar.setTitle("도움말");
//        toolbar.setNavigationIcon(R.mipmap.ic_menu_arrow_back);
//        toolbar.setTitleTextColor(Color.WHITE);
//
//        setSupportActionBar(toolbar);
//
//        final ActionBar actionBar = getSupportActionBar();
//
//        actionBar.setTitle("도움말");
//        actionBar.setDefaultDisplayHomeAsUpEnabled(true);
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
