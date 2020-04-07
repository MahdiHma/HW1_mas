package com.example.hw1_mas.requests;

import com.example.hw1_mas.models.City;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

public class WeatherRequestParser {
    private static WeatherRequestParser requestParser;

    private WeatherRequestParser(){}

    public static String saveJson() {
        Gson fromJson = new Gson();
    }

    public static String fromJson(JsonObject weatherResponse) {
        Gson fromJson = new Gson();
//        fromJson.fromJson(weatherResponse, );

    }
}
