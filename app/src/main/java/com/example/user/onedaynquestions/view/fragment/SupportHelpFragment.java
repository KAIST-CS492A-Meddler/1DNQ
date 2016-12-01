package com.example.user.onedaynquestions.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.onedaynquestions.R;

/**
 * Created by ymbae on 2016-04-18.
 */
public class SupportHelpFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_instruction, container, false);
        //return super.onCreateView(inflater, container, savedInstanceState);


    }


}
