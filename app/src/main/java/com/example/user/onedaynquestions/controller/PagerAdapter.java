package com.example.user.onedaynquestions.controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.user.onedaynquestions.view.fragment.EquipmentScanner;
import com.example.user.onedaynquestions.view.fragment.MyRoutinesFragment;
import com.example.user.onedaynquestions.view.fragment.MyRecordsFragment;

/**
 * Created by ymbae on 2016-04-18.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;
    Fragment deviceList;
    Fragment myRoutine;
    Fragment myRecord;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        deviceList = new EquipmentScanner();
        myRoutine = new MyRoutinesFragment();
        myRecord = new MyRecordsFragment();
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return deviceList;
            case 1:
                return myRoutine;
            case 2:
                return myRecord;
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
