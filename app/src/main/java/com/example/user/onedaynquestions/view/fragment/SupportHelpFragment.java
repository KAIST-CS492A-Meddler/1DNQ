package com.example.user.onedaynquestions.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.user.onedaynquestions.R;

/**
 * Created by ymbae on 2016-04-18.
 */
public class SupportHelpFragment extends Fragment{

    private View currentView;
    LinearLayout instruction_background;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        currentView = inflater.inflate(R.layout.fragment_instruction, container, false);

        instruction_background = (LinearLayout) currentView.findViewById(R.id.instruction_background);

        return currentView;
        //return super.onCreateView(inflater, container, savedInstanceState);


    }

//    public void mOnClick(View v) {
//        switch (v.getId()) {
//            case R.id.instruction_background:
////                onDetach();
//                onDestroyView();
//                break;
//        }
//    }


}
