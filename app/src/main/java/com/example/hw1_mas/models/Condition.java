package com.example.hw1_mas.models;

import com.google.gson.annotations.SerializedName;

public class Condition {
    @SerializedName("text")
    private String state;
    private String icon;
    private int code;

    public String getState() {
        return state;
    }

    public String getIcon() {
        return icon;
    }

    public int getCode() {
        return code;
    }
}
