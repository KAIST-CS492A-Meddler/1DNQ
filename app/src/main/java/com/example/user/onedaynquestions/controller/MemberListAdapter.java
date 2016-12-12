package com.example.user.onedaynquestions.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.onedaynquestions.R;
import com.example.user.onedaynquestions.model.MyCard;
import com.example.user.onedaynquestions.view.activity.LeaderboardActivity;

import java.util.ArrayList;

/**
 * Created by GEUN on 2016-12-13.
 */

public class MemberListAdapter extends BaseAdapter {

        LayoutInflater mInflater;
        ArrayList<LeaderboardActivity.UserInfo> arrMember;

        public MemberListAdapter(Context context, ArrayList<LeaderboardActivity.UserInfo> arrMember) {
            this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.arrMember = arrMember;
        }
        public MemberListAdapter(Context context) {
            this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return arrMember.size();
        }

        @Override
        public Object getItem(int position) {
            return arrMember.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                int res = 0;
                res = R.layout.user_row;
                convertView = mInflater.inflate(res, parent, false);

            }

            ImageView icon = (ImageView)convertView.findViewById(R.id.user_row_icon);
            TextView nick = (TextView)convertView.findViewById(R.id.user_nick);
            TextView id = (TextView)convertView.findViewById(R.id.user_id);
            TextView name  = (TextView)convertView.findViewById(R.id.user_name);
            TextView exp  = (TextView)convertView.findViewById(R.id.user_exp);


            if(arrMember.get(position).isMale) {
                icon.setImageResource(R.drawable.here_character_simple_boy);
            }else {
                icon.setImageResource(R.drawable.here_character_simple_girl);
            }
            nick.setText(arrMember.get(position).userNickName);
            name.setText(arrMember.get(position).userName);
            id.setText(arrMember.get(position).userId);
            exp.setText(""+arrMember.get(position).userExp);

            return convertView;
        }
    }
