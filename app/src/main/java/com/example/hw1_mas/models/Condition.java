package com.example.hw1_mas.models;

import com.google.gson.annotations.SerializedName;

public class Condition {
    private String text;
    private String icon;
    private int code;

    public String getState() {
        return text;
    }

    public String getIcon() {
        return icon;
    }

    public int getCode() {
        return code;
    }
}
