package com.example.hw1_mas.models;

import android.content.Context;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class Weather {
    private String date;
    private Day day;

    public String getDate() {
        return date;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }
}
