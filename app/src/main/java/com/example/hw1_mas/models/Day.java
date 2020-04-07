package com.example.hw1_mas.models;

import java.util.Date;
import com.google.gson.annotations.SerializedName;
public class Day {
//    private Date date = new Date();
    private String date;
    @SerializedName("avgtemp_c")
    private int averageTemp ;
    private Condition condition;

    public String getDate() {
        return date;
    }

    public int getAverageTemp() {
        return averageTemp;
    }

    public Condition getCondition() {
        return condition;
    }
}
