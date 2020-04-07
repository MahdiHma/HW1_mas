package com.example.hw1_mas.models;

import com.google.gson.annotations.SerializedName;

public class Day {
    @SerializedName("avgtemp_c")
    private double averageTemp;
    private Condition condition;

    public double getAverageTemp() {
        return averageTemp;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }
}
