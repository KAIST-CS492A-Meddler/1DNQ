package com.example.user.onedaynquestions.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.user.onedaynquestions.model.Question;

import java.util.ArrayList;

/**
 * Created by PCPC on 2016-12-04.
 */

public class TemporalStorage extends BroadcastReceiver{
    private static ArrayList<Question> receivedQuestions = new ArrayList<>();

    @Override
    public void onReceive(Context context, Intent intent) {

        String name = intent.getAction();

        if(name.equals(".service.PushReceiver")){
            Toast.makeText
                    (context, intent.getStringExtra("question") + " / " + intent.getStringExtra("answer") + " / "+intent.getStringExtra("group"), Toast.LENGTH_SHORT).show();
            TemporalStorage.addReceivedQuestion(intent);
        }
    }

    public static void addReceivedQuestion(Intent intent){
        receivedQuestions.add(new Question(intent.getStringExtra("question"), intent.getStringExtra("answer"), intent.getStringExtra("group")));
    }

    public static void clearReceivedQuestions(){
        receivedQuestions.clear();
    }

    public static boolean isEmpty(){
        return receivedQuestions.isEmpty();
    }
    public static Question consumeReceivedQuestions(){
        Question temp = receivedQuestions.remove(receivedQuestions.size() - 1);
        return temp;
    }
    public static ArrayList<Question> getReceivedQuestions(){
        return receivedQuestions;
    }

    public static int numReceivedQuestions(){
        return receivedQuestions.size();
    }

}
