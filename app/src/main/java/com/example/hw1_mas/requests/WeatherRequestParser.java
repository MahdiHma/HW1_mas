package com.example.hw1_mas.requests;

import com.example.hw1_mas.models.City;
import com.example.hw1_mas.models.Day;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class WeatherRequestParser {
    private static WeatherRequestParser requestParser;
    private static String fileLocation = "";

    private WeatherRequestParser() {
    }

    public static void saveJson(ArrayList<Day> day) {
        Gson toJson = new GsonBuilder().setPrettyPrinting().create();
        String dayToJsonString = toJson.toJson(day);
        try {
            FileWriter fileWriter = new FileWriter(WeatherRequestParser.fileLocation);
            fileWriter.write(dayToJsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<Day> fromJson(JSONObject weatherResponse) {
        Gson jsonReader = new Gson();
        JsonObject weatherObject = jsonReader.fromJson(weatherResponse.toString(), JsonObject.class);
        Day current = jsonReader.fromJson(weatherObject.get("current"), Day.class);
        ArrayList<Day> days = new ArrayList<>();
        days.add(current);
        JsonArray forecasts = (JsonArray) weatherObject.get("forecast");
        for (JsonElement day : forecasts) {
            days.add(jsonReader.fromJson(day, Day.class));
        }
        return days;
//        fromJson.fromJson(weatherResponse, );

    }
}
