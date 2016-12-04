package com.example.user.onedaynquestions.view.testactivity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.onedaynquestions.R;
import com.example.user.onedaynquestions.controller.CardAdapter;
import com.example.user.onedaynquestions.model.MyCard;

import java.util.ArrayList;

/**
 * Created by ymbaek on 2016-04-18.
 */
public class WidgetTestActivity extends AppCompatActivity {

    public static final String TAG = "MyAchievements";

    private Toolbar toolbar;

    private RecyclerView rv;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testwidget);
//        scrollView = (HorizontalScrollView) findViewById(R.id.add_routine_hscrollview);
//        textView = (TextView) findViewById(R.id.setting_myroutine_tv_nonewroutine);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("Widget Test Activity");
        toolbar.setNavigationIcon(R.mipmap.ic_menu_arrow_back);
        toolbar.setTitleTextColor(Color.WHITE);

        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle("Widget Test Activity");
        actionBar.setDefaultDisplayHomeAsUpEnabled(true);

        initWidget();

    }


    private void initWidget() {
        rv = (RecyclerView) findViewById(R.id.widgettest_rv);
        rv.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(mLayoutManager);

        ArrayList<MyCard> cardListSample = new ArrayList<MyCard>();

        MyCard card1 = new MyCard();
        MyCard card2 = new MyCard();
        MyCard card3 = new MyCard();

        cardListSample.add(card1); cardListSample.add(card2); cardListSample.add(card3);

        CardAdapter cardAdapter = new CardAdapter(cardListSample);
        rv.setAdapter(cardAdapter);
        cardAdapter.notifyDataSetChanged();
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
