package com.example.user.onedaynquestions.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.onedaynquestions.R;
import com.example.user.onedaynquestions.model.Question;

import java.util.ArrayList;

/**
 * Created by user on 2016-04-18.
 */
public class QuestionListAdapter extends BaseAdapter {

    LayoutInflater mInflater;
    ArrayList<Question> arrQuestion;

    public QuestionListAdapter(Context context, ArrayList<Question> arrQuestion) {
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arrQuestion = arrQuestion;
    }
    public QuestionListAdapter(Context context) {
        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrQuestion.size();
    }

    @Override
    public Object getItem(int position) {
        return arrQuestion.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            int res = 0;
            res = R.layout.listitem_question;
            convertView = mInflater.inflate(res, parent, false);

        }

        ImageView icon = (ImageView)convertView.findViewById(R.id.questionlist_img);
        TextView quesion = (TextView)convertView.findViewById(R.id.questionlist_question);
        TextView answer = (TextView)convertView.findViewById(R.id.questionlist_answer);
        TextView group  = (TextView)convertView.findViewById(R.id.questionlist_group);


        icon.setImageResource(R.mipmap.odnq_app_icon_bulb);

        quesion.setText(arrQuestion.get(position).getQuesion());
        group.setText(arrQuestion.get(position).getGroup());
        answer.setText(arrQuestion.get(position).getAnswer());

        return convertView;
    }
}
