package com.example.user.onedaynquestions.controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;

import com.example.user.onedaynquestions.view.fragment.MyAchievementFragment;
import com.example.user.onedaynquestions.view.fragment.StudyNoteFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ymbae on 2016-04-18.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {

    private Map<Integer, String> mFragmentTags;
    int mNumOfTabs;
    Fragment myAchievement;
    FragmentManager mFragmentManager;
    StudyNoteFragment myStudyNote;
    //Fragment myRecord;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        mFragmentTags = new HashMap<Integer, String>();
        myAchievement = new MyAchievementFragment();
        myStudyNote = new StudyNoteFragment();
        //myRecord = new StudyNoteFragment();
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

    @Override
    public void notifyDataSetChanged() {
        if (myStudyNote != null && myStudyNote.cdt != null) {
            myStudyNote.cdt.start();
        }
        super.notifyDataSetChanged();
    }

    @Override
    public Object instantiateItem(View container, int position) {
//        return super.instantiateItem(container, position);
        Object obj = super.instantiateItem(container, position);

        if (obj instanceof Fragment) {
            Fragment f = (Fragment) obj;
            String tag = f.getTag();
            mFragmentTags.put(position, tag);
        }
        return obj;
    }

    public Fragment getFragment (int position) {
        String tag = mFragmentTags.get(position);
        if (tag == null)
            return null;
        return mFragmentManager.findFragmentByTag(tag);
    }
}
