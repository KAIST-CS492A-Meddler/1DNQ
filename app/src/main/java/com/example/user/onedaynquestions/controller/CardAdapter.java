package com.example.user.onedaynquestions.controller;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.onedaynquestions.R;
import com.example.user.onedaynquestions.model.MyCard;

import java.util.ArrayList;

/**
 * Created by user on 2016-12-05.
 */

public class CardAdapter extends RecyclerView.Adapter<CardViewHolder>{

    ArrayList<MyCard> cardList;

    public CardAdapter(ArrayList<MyCard> cardList) {
        this.cardList = cardList;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_word, parent, false);
        return new CardViewHolder(v);
//        return null;
    }
    /** 정보 및 이벤트 처리는 이 메소드에서 구현 **/
    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        MyCard tmpCard = cardList.get(position);

        if (tmpCard.getMyCardStarred() == 1) {
            holder.tagview.setBackgroundColor(Color.parseColor("#ffc90e"));
        }

        if (tmpCard.getMyCardWrong() > 0) {
            holder.tagview.setBackgroundColor(Color.parseColor("#e7406f"));
        }

        holder.question.setText(tmpCard.getMyCardQuestion());
        holder.examiner.setText(tmpCard.getMyCardMaker());
        holder.group.setText(tmpCard.getMyCardGroup());
        holder.date.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return this.cardList.size();
    }


}
