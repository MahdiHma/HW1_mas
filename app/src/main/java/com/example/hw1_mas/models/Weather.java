package com.example.hw1_mas.models;

import android.content.Context;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class Weather {
    //    private Date date = new Date();
    private String date;
    //    @SerializedName("avgtemp_c")
    private double averageTemp;
    private Condition condition;

    public String getDate() {
        return date;
    }

    public double getAverageTemp() {
        return averageTemp;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setAverageTemp(double averageTemp) {
        this.averageTemp = averageTemp;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }
}
