package com.example.user.onedaynquestions.model;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by PCPC on 2016-12-05.
 */

public class Question {

    String quesion;

    String answer;
    //MAC address
    String group;

    Date questionRegisterDate;
    Date questionLastUseDate;



    public Question() {
        this.quesion = "NO QUESTION";
        this.group = "NO GROUP";
        this.questionRegisterDate = Calendar.getInstance(Locale.KOREA).getTime();
        this.answer = "NO ANSWER";
    }

    public Question(String answer) {
        this.quesion = "NO QUESTION";
        this.group = "NO GROUP";
        this.questionRegisterDate =Calendar.getInstance(Locale.KOREA).getTime();
        this.answer = answer;
    }

    public Question(String quesion, String answer) {
        this.group = "NO GROUP";
        this.questionRegisterDate =Calendar.getInstance(Locale.KOREA).getTime();
        this.quesion = quesion;
        this.answer = answer;
    }

    public Question(String quesion, String answer, String questionRegisterDate) {
        this.group = "NO GROUP";
        this.quesion = quesion;
        this.answer = answer;
        this.questionRegisterDate =Calendar.getInstance(Locale.KOREA).getTime();
    }

    public Question(String quesion, String answer, String group, String questionRegisterDate) {
        this.quesion = quesion;
        this.answer = answer;
        this.group = group;
        this.questionRegisterDate =Calendar.getInstance(Locale.KOREA).getTime();
    }

    public String getQuesion() {
        return quesion;
    }

    public void setQuesion(String quesion) {
        this.quesion = quesion;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getQuestionRegisterDate() {
        return questionRegisterDate.toString();
    }

    public void setQuestionRegisterDate(Date questionRegisterDate) {
        this.questionRegisterDate = questionRegisterDate;
    }

    @Override
    public boolean equals(Object obj){
        return this.quesion.equals(((Question)obj).quesion);
    }
}