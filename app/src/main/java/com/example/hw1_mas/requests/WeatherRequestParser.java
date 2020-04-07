package com.example.hw1_mas.requests;

import android.util.Log;

import com.example.hw1_mas.models.Weather;
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

    public static void saveJson(ArrayList<Weather> weather) {
        Gson toJson = new GsonBuilder().setPrettyPrinting().create();
        String dayToJsonString = toJson.toJson(weather);
        try {
            FileWriter fileWriter = new FileWriter(WeatherRequestParser.fileLocation);
            fileWriter.write(dayToJsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<Weather> fromJson(JSONObject weatherResponse) {
        Gson jsonReader = new Gson();
        JsonObject weatherObject = jsonReader.fromJson(weatherResponse.toString(), JsonObject.class);
        Weather current = jsonReader.fromJson(weatherObject.get("current"), Weather.class);
        ArrayList<Weather> weathers = new ArrayList<>();
        weathers.add(current);
        JsonObject forecast = (JsonObject) weatherObject.get("forecast");
        JsonArray forecastDay = (JsonArray) forecast.get("forecastday");
        Log.i("test", forecastDay.toString());
        for (JsonElement day : forecastDay) {
            Weather weatherN = jsonReader.fromJson(day, Weather.class);
            Log.i("TESsT", day.getAsJsonObject().toString());
            Log.i("TEST", String.valueOf(weatherN.getAverageTemp()));
            weathers.add(weatherN);

        }
        return weathers;

    }
}
