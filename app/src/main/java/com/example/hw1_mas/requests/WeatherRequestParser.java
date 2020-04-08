package com.example.hw1_mas.requests;

import android.content.Context;
import android.util.JsonReader;
import android.util.Log;

import com.example.hw1_mas.models.Condition;
import com.example.hw1_mas.models.Day;
import com.example.hw1_mas.models.Weather;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class WeatherRequestParser {
    private static WeatherRequestParser requestParser;
    private static String fileLocation = "lastUpdate";
    private static File file;

    private WeatherRequestParser() {

    }

    public static void saveJson(ArrayList<Weather> weather, Context context) {
        Gson toJson = new GsonBuilder().setPrettyPrinting().create();

        String dayToJsonString = toJson.toJson(weather);
        try {
            file = new File(context.getFilesDir().getPath(), fileLocation);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(WeatherRequestParser.file);
            fileWriter.write(dayToJsonString);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<Weather> loadJson(Context context) throws IOException, NullPointerException {
        ArrayList<Weather> weathers = new ArrayList<>();
        FileReader scanner = new FileReader(file);
        Gson json = new GsonBuilder().setPrettyPrinting().create();
        JsonArray weatherJson = json.fromJson(scanner, JsonArray.class);
        for (JsonElement day : weatherJson) {
            Weather weatherN = json.fromJson(day, Weather.class);
            weathers.add(weatherN);
        }
        scanner.close();
        return weathers;
    }

    public static ArrayList<Weather> fromJson(JSONObject weatherResponse) {
        Gson jsonReader = new Gson();
        JsonObject weatherObject = jsonReader.fromJson(weatherResponse.toString(), JsonObject.class);
        Weather current = jsonReader.fromJson(weatherObject.get("current"), Weather.class);
        Day currentDay = new Day();
        currentDay.setCondition(jsonReader.fromJson(weatherObject.get("current").getAsJsonObject().get("condition"), Condition.class));
        current.setDay(currentDay);
        Log.i("WeatherObject", weatherObject.toString());
        ArrayList<Weather> weathers = new ArrayList<>();
        weathers.add(current);
        JsonObject forecast = (JsonObject) weatherObject.get("forecast");
        JsonArray forecastDay = (JsonArray) forecast.get("forecastday");
        for (JsonElement day : forecastDay) {
            Weather weatherN = jsonReader.fromJson(day, Weather.class);
            weathers.add(weatherN);
        }
        Log.i("test", weathers.toString());
        return weathers;

    }
}
