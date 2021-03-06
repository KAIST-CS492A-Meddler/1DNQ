package com.example.user.onedaynquestions.model;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.user.onedaynquestions.view.activity.CardSolvingActivity;

import java.util.Iterator;
import java.util.Set;

/**
 * Created by user on 2016-12-04.
 */

public class MyCard {
    private static final String TAG = "MyCard";

    public static final String ATTRIBUTE_CARD_ID = "card_id";
    public static final String ATTRIBUTE_DATE = "card_datetime";
    public static final String ATTRIBUTE_CARD_TYPE = "card_type";
    public static final String ATTRIBUTE_CARD_MAKER = "card_maker";
    public static final String ATTRIBUTE_CARD_GROUP = "card_group";
    public static final String ATTRIBUTE_CARD_QUESTION = "card_question";
    public static final String ATTRIBUTE_CARD_ANSWER = "card_answer";
    public static final String ATTRIBUTE_CARD_HINT = "card_hint";
    public static final String ATTRIBUTE_CARD_DIFFICULTY = "card_difficulty";

    String myCardId;
    String myCardDateTime;
    int myCardType;
    String myCardMaker;
    String myCardGroup;
    String myCardQuestion;
    String myCardAnswer;
    String myCardHint;
    int myCardWrong;
    int myCardDifficulty;
    int myCardQuality;
    int myCardStarred;

    private void initMyCard() {
        myCardId = "-1";
        myCardDateTime = "-1";
        myCardType = 0;
        myCardMaker = "-1";
        myCardGroup = "-1";
        myCardQuestion = "-1";
        myCardAnswer = "-1";
        myCardHint = "-1";
        myCardWrong = 0;
        myCardDifficulty = -1;
        myCardQuality = -1;
        myCardStarred = 0;
    }

    public Intent getCardSolvingIntent(Context current){
        Intent result = new Intent(current, CardSolvingActivity.class);
        result.putExtra(ATTRIBUTE_CARD_ID, myCardId);
        result.putExtra(ATTRIBUTE_DATE, myCardDateTime);
        result.putExtra(ATTRIBUTE_CARD_TYPE, myCardType);
        result.putExtra(ATTRIBUTE_CARD_MAKER, myCardMaker);
        result.putExtra(ATTRIBUTE_CARD_GROUP, myCardGroup);
        result.putExtra(ATTRIBUTE_CARD_QUESTION, myCardQuestion);
        result.putExtra(ATTRIBUTE_CARD_ANSWER, myCardAnswer);
        result.putExtra(ATTRIBUTE_CARD_HINT, myCardHint);
        result.putExtra(ATTRIBUTE_CARD_DIFFICULTY, ""+myCardDifficulty);

        return  result;
    }
    public MyCard() {
        initMyCard();
    }

    public MyCard(Intent intent){

//        Bundle bundle = intent.getExtras();
//        if (bundle != null) {
//            Set<String> keys = bundle.keySet();
//            Iterator<String> it = keys.iterator();
//            Log.e(TAG,"Dumping Intent start");
//            while (it.hasNext()) {
//                String key = it.next();
//                Log.e(TAG,"[" + key + "=" + bundle.get(key)+"]");
//            }
//            Log.e(TAG,"Dumping Intent end");
//        }

        myCardId = intent.getStringExtra(ATTRIBUTE_CARD_ID);
        myCardDateTime = intent.getStringExtra(ATTRIBUTE_DATE);
        try {
            myCardType = Integer.valueOf(intent.getStringExtra(ATTRIBUTE_CARD_TYPE));
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
        myCardMaker = intent.getStringExtra(ATTRIBUTE_CARD_MAKER);
        myCardGroup = intent.getStringExtra(ATTRIBUTE_CARD_GROUP);
        myCardQuestion = intent.getStringExtra(ATTRIBUTE_CARD_QUESTION);
        myCardAnswer = intent.getStringExtra(ATTRIBUTE_CARD_ANSWER);
        myCardHint = intent.getStringExtra(ATTRIBUTE_CARD_HINT);
        myCardWrong = 0;
        try {
            myCardDifficulty = Integer.valueOf(intent.getStringExtra(ATTRIBUTE_CARD_DIFFICULTY));
        }catch (Exception e){
            Log.e(TAG, e.getMessage());
        }
        myCardQuality = -1;
        myCardStarred = 0;
    }
    public MyCard(String myCardId, String myCardDateTime, int myCardType, String myCardMaker, String myCardGroup) {
        initMyCard();

        this.myCardId = myCardId;
        this.myCardDateTime = myCardDateTime;
        this.myCardType = myCardType;
        this.myCardMaker = myCardMaker;
        this.myCardGroup = myCardGroup;
    }

    public MyCard(String myCardId, String myCardDateTime, int myCardType, String myCardMaker, String myCardGroup, String myCardQuestion, String myCardAnswer) {
        initMyCard();

        this.myCardId = myCardId;
        this.myCardDateTime = myCardDateTime;
        this.myCardType = myCardType;
        this.myCardMaker = myCardMaker;
        this.myCardGroup = myCardGroup;
        this.myCardQuestion = myCardQuestion;
        this.myCardAnswer = myCardAnswer;
    }

    public MyCard(String myCardId, String myCardDateTime, int myCardType, String myCardMaker, String myCardGroup, String myCardQuestion, String myCardAnswer, int myCardWrong, int myCardDifficulty, int myCardQuality, int myCardStarred) {
        initMyCard();

        this.myCardId = myCardId;
        this.myCardDateTime = myCardDateTime;
        this.myCardType = myCardType;
        this.myCardMaker = myCardMaker;
        this.myCardGroup = myCardGroup;
        this.myCardQuestion = myCardQuestion;
        this.myCardAnswer = myCardAnswer;
        this.myCardWrong = myCardWrong;
        this.myCardDifficulty = myCardDifficulty;
        this.myCardQuality = myCardQuality;
        this.myCardStarred = myCardStarred;
    }




    public String getMyCardId() {
        return myCardId;
    }

    public void setMyCardId(String myCardId) {
        this.myCardId = myCardId;
    }

    public String getMyCardDateTime() {
        return myCardDateTime;
    }

    public void setMyCardDateTime(String myCardDateTime) {
        this.myCardDateTime = myCardDateTime;
    }

    public int getMyCardType() {
        return myCardType;
//
//        switch (myCardType){
//            case 0:
//                return "What is the meaning\n of this word?";
//            default:
//                return "What is the meaning\n of this word?";
//
//        }
    }

    public void setMyCardType(int myCardType) {
        this.myCardType = myCardType;
    }

    public String getMyCardMaker() {
        return myCardMaker;
    }

    public void setMyCardMaker(String myCardMaker) {
        this.myCardMaker = myCardMaker;
    }

    public String getMyCardGroup() {
        return myCardGroup;
    }

    public void setMyCardGroup(String myCardGroup) {
        this.myCardGroup = myCardGroup;
    }

    public String getMyCardQuestion() {
        return myCardQuestion;
    }

    public void setMyCardQuestion(String myCardQuestion) {
        this.myCardQuestion = myCardQuestion;
    }

    public String getMyCardAnswer() {
        return myCardAnswer;
    }

    public void setMyCardAnswer(String myCardAnswer) {
        this.myCardAnswer = myCardAnswer;
    }

    public int getMyCardWrong() {
        return myCardWrong;
    }

    public void setMyCardWrong(int myCardWrong) {
        this.myCardWrong = myCardWrong;
    }

    public int getMyCardDifficulty() {
        return myCardDifficulty;
    }

    public void setMyCardDifficulty(int myCardDifficulty) {
        this.myCardDifficulty = myCardDifficulty;
    }

    public int getMyCardQuality() {
        return myCardQuality;
    }

    public void setMyCardQuality(int myCardQuality) {
        this.myCardQuality = myCardQuality;
    }

    public int getMyCardStarred() {
        return myCardStarred;
    }

    public void setMyCardStarred(int myCardStarred) {
        this.myCardStarred = myCardStarred;
    }

    public String getMyCardHint() {
        return myCardHint;
    }

    public void setMyCardHint(String myCardHint) {
        this.myCardHint = myCardHint;
    }
}
