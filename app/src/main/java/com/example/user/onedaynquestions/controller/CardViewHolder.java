package com.example.user.onedaynquestions.controller;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.user.onedaynquestions.R;

/**
 * Created by PCPC on 2016-12-10.
 */
public class CardViewHolder extends RecyclerView.ViewHolder{
    public TextView question, group, examiner, date;
    public View tagview;
    public CardViewHolder(View cardView){
        super(cardView);
        tagview = (View)cardView.findViewById(R.id.card_tagcolor);
        question = (TextView)cardView.findViewById(R.id.card_question);
        group = (TextView)cardView.findViewById(R.id.card_group);
        examiner = (TextView)cardView.findViewById(R.id.card_examiner);
        date = (TextView)cardView.findViewById(R.id.card_date);
    }
}
