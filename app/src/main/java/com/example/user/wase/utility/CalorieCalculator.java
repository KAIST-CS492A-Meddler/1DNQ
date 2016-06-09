package com.example.user.wase.utility;

import com.example.user.wase.model.Equipment;
import com.example.user.wase.model.MyHereAgent;
import com.example.user.wase.model.MyInformation;

/**
 * Created by sarahsong on 2016-06-06.
 */
public class CalorieCalculator {
    private double calorie;

    public CalorieCalculator() {
        calorie = 0;
    }

    public void setCalorie(double calorie) {
        this.calorie = calorie;
    }

    public double getCalorie(MyHereAgent agent, MyInformation info,int num, int time) {
        switch (agent.getMyeqType()) {
            case MyHereAgent.TYPE_DUMBEL:
                //http://www.lgcare.com/m/news/beauty/view.jsp?seq=329&type=B&title=
                //calorie = 0.17 * time;
                calorie = 0.117 * time;
                break;
            case MyHereAgent.TYPE_PUSH_UP:
                //http://blog.naver.com/iunkim/220556783031
                int weight = info.getUserWeight();
                if(weight<50){
                    calorie = 0.1 * time;
                } else if(weight>=50&&weight<70){
                    calorie = 0.12 * time;
                } else if(weight>=70){
                    calorie = 0.17 * time;
                }
                break;
            case MyHereAgent.TYPE_HOOLA_HOOP:
                //http://blog.naver.com/8597hks/220724012977
                calorie = 0.07 * time;
                break;
        }
        return calorie;
    }
}
