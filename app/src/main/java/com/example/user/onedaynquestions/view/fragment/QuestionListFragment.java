package com.example.user.onedaynquestions.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.user.onedaynquestions.R;
import com.example.user.onedaynquestions.controller.QuestionListAdapter;
import com.example.user.onedaynquestions.model.MyCard;

import java.util.ArrayList;

/**
 * Created by ymbae on 2016-04-18.
 */
public class QuestionListFragment extends Fragment{

    private ListView lvQuestionList;

    private ArrayList<MyCard> questionList;
    private QuestionListAdapter questionListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        questionList = new ArrayList<>();
        questionListAdapter = new QuestionListAdapter(getActivity().getApplicationContext(), questionList);

        View view = inflater.inflate(R.layout.fragment_questionlist, container, false);
        lvQuestionList = (ListView) view.findViewById(R.id.fragment1_questionlist);

        lvQuestionList.setAdapter(questionListAdapter);


        questionListAdapter.notifyDataSetChanged();

        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }
}
