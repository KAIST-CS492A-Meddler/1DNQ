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
import com.example.user.onedaynquestions.model.Question;

import java.util.ArrayList;

/**
 * Created by ymbae on 2016-04-18.
 */
public class QuestionListFragment extends Fragment{

    private ListView lvQuestionList;

    private ArrayList<Question> questionList;
    private QuestionListAdapter questionListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        questionList = new ArrayList<>();
        questionListAdapter = new QuestionListAdapter(getActivity().getApplicationContext(), questionList);

        View view = inflater.inflate(R.layout.fragment_questionlist, container, false);
        lvQuestionList = (ListView) view.findViewById(R.id.fragment1_questionlist);

        lvQuestionList.setAdapter(questionListAdapter);

        Question eq1 = new Question("Q01", "hi", "안녕", "2016-11-18");
        Question eq2 = new Question("Q02", "hello", "안녕", "2016-11-18");
        Question eq3 = new Question("Q03", "ah", "아", "2016-11-15");
        Question eq4 = new Question("Q04", "good night", "잘자", "2016-11-16");

        questionList.add(eq1);
        questionList.add(eq2);
        questionList.add(eq3);
        questionList.add(eq4);

        questionListAdapter.notifyDataSetChanged();

        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }
}
