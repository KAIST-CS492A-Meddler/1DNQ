package com.example.user.onedaynquestions.controller;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.onedaynquestions.R;
import com.example.user.onedaynquestions.model.MyCard;
import com.example.user.onedaynquestions.view.testactivity.WidgetTestActivity;

import java.util.ArrayList;

/**
 * Created by user on 2016-12-05.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    ArrayList<MyCard> cardList;

    public CardAdapter(ArrayList<MyCard> cardList) {
        this.cardList = cardList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_word, parent, false);
        return new ViewHolder(v);
//        return null;
    }

    /** 정보 및 이벤트 처리는 이 메소드에서 구현 **/
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MyCard tmpCard = cardList.get(position);

        holder.card_question.setText(tmpCard.getMyCardQuestion());
        holder.card_date.setText(tmpCard.getMyCardDateTime());
        holder.card_group.setText(tmpCard.getMyCardGroup());
    }

    @Override
    public int getItemCount() {
        return this.cardList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView card_icon;
        TextView card_question;
        TextView card_date;
        TextView card_group;
        CardView cv;

        public ViewHolder(View v) {
            super(v);

            card_icon = (ImageView) v.findViewById(R.id.cardicon);
            card_question = (TextView) v.findViewById(R.id.card_question);
            card_date = (TextView) v.findViewById(R.id.card_date);
            card_group = (TextView) v.findViewById(R.id.card_group);
            cv = (CardView) v.findViewById(R.id.cardview);


        }
    }

    //    private String[] mDataset;

//    public CardAdapter(String[] myDataset) {
//        this.mDataset = myDataset;
//    }
//
//    @Override
//    public CardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_word, parent, false);
//        ViewHolder vh = new ViewHolder(v);
//
////            return null;
//    }
//
//    @Override
//    public void onBindViewHolder(CardAdapter.ViewHolder holder, int position) {
//        holder.mTextView.setText(mDataset[position]);
//    }
//
//    @Override
//    public int getItemCount() {
//        return mDataset.length;
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder{
//
//        public TextView mTextView;
//        public ViewHolder(TextView v) {
//            super(v);
//            mTextView = v;
////                super(itemView);
//        }
//    }
}
