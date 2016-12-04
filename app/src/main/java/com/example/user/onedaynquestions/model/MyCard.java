package com.example.user.onedaynquestions.model;

/**
 * Created by user on 2016-12-04.
 */

public class MyCard {

    String myCardId;
    String myCardDateTime;
    int myCardType;
    String myCardMaker;
    String myCardGroup;
    String myCardQuestion;
    String myCardAnswer;
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
        myCardWrong = 0;
        myCardDifficulty = -1;
        myCardQuality = -1;
        myCardStarred = 0;
    }

    public MyCard() {
        initMyCard();
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


    public String getMyCardId(){
        return myCardId;
    }
    public String getMyCardDateTime(){
        return myCardDateTime;
    }
    public String getMyCardType(){
        switch (myCardType){
            case 0:
                return "What is the meaning\n of this word?";
            default:
                return "What is the meaning\n of this word?";

        }
    }
    public String getMyCardMaker(){
        return myCardMaker;
    }
    public String getMyCardGroup(){
        return myCardGroup;
    }
    public String getMyCardQuestion(){
        return myCardQuestion;
    }
    public String getMyCardAnswer(){
        return myCardAnswer;
    }
    public int getMyCardWrong(){
        return myCardWrong;
    }
    public int getMyCardDifficulty(){
        return myCardDifficulty;
    }
    public int getMyCardQuality(){
        return myCardQuality;
    }
    public int getMyCardStarred(){
        return myCardStarred;
    }
}
