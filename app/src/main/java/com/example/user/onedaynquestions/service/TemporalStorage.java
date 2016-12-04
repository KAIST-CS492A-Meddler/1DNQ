package com.example.user.onedaynquestions.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.user.onedaynquestions.model.MyCard;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by PCPC on 2016-12-04.
 */

public class TemporalStorage extends BroadcastReceiver{
    private static ArrayList<MyCard> receivedQuestions = new ArrayList<>();
    private static int count = 0;

    @Override
    public void onReceive(Context context, Intent intent) {

        String name = intent.getAction();

        if(name.equals(".service.PushReceiver")){
            Toast.makeText
                    (context, "A new Question card has arrived!", Toast.LENGTH_SHORT).show();
            TemporalStorage.addReceivedQuestion(intent);
        }
    }

    public static void addReceivedQuestion(Intent intent){
        //MyCard(String myCardId, String myCardDateTime, int myCardType, String myCardMaker, String myCardGroup, String myCardQuestion, String myCardAnswer) {
        receivedQuestions.add(new MyCard(intent.getStringExtra("id") , Calendar.getInstance().getTime().toString(), intent.getIntExtra("type", 0), intent.getStringExtra("examiner"), intent.getStringExtra("group"), intent.getStringExtra("question"), intent.getStringExtra("answer")));
    }

    public static void clearReceivedQuestions(){
        receivedQuestions.clear();
    }

    public static boolean isEmpty(){
        return receivedQuestions.isEmpty();
    }
    public static MyCard consumeReceivedQuestions(){
        MyCard temp =null;
        if(!receivedQuestions.isEmpty()){
            temp = receivedQuestions.remove(0);
        }
        return temp;
    }
    public static MyCard getReceivedQuestions(int id){
        MyCard temp =null;
        if(receivedQuestions.size() > id){
            temp = receivedQuestions.get(id);
        }
        return temp;
    }
    public static MyCard getReceivedQuestion(){
        MyCard temp =null;
        if(receivedQuestions.size() > count){
            temp = receivedQuestions.get(count++);
        }else{
            count = 0;
        }
        return temp;
    }
    public static ArrayList<MyCard> getAllReceivedQuestions(){
        return receivedQuestions;
    }

    public static int numReceivedQuestions(){
        return receivedQuestions.size();
    }

}
