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
}
