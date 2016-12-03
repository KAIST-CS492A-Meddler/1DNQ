package com.example.user.onedaynquestions.controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.user.onedaynquestions.view.fragment.MyAchievementFragment;
import com.example.user.onedaynquestions.view.fragment.MyRecordsFragment;

/**
 * Created by ymbae on 2016-04-18.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;
    Fragment myAchievement;
    Fragment myStudyNote;
    //Fragment myRecord;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        myAchievement = new MyAchievementFragment();
        myStudyNote = new MyRecordsFragment();
        //myRecord = new MyRecordsFragment();
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return myAchievement;
            case 1:
                return myStudyNote;
            default:
                return null;

        }
        //return null;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
