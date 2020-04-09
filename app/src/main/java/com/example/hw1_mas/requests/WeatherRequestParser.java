package com.example.hw1_mas.requests;

import android.content.Context;
import android.os.Environment;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.EventListener;
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
            file = getFile(context);

            Log.i("ssssssssssss", "ssstttttttttttttttt");
            FileOutputStream fileWriter = context.openFileOutput(fileLocation, Context.MODE_PRIVATE);
            fileWriter.write(dayToJsonString.getBytes());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<Weather> loadJson(Context context) throws IOException {
        ArrayList<Weather> weathers = new ArrayList<>();
        FileInputStream fis = context.openFileInput(fileLocation);
        InputStreamReader
                inputStreamReader = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT
                ? new InputStreamReader(fis, StandardCharsets.UTF_8)
                : new InputStreamReader(fis, Charset.forName("UTF-8"));
        String contents;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line).append('\n');
                line = reader.readLine();
            }
        } catch (IOException e) {
            // Error occurred when opening raw file for reading.
        } finally {
            contents = stringBuilder.toString();
            Log.i("woooooooooooww", contents);
        }

        FileReader scanner = new FileReader(getFile(context));
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
        currentDay.setAverageTemp(jsonReader.fromJson(weatherObject.get("current").getAsJsonObject().get("temp_c"), Double.class));
        current.setDate();
        Log.i("WeatherObject", weatherObject.toString());
        ArrayList<Weather> weathers = new ArrayList<>();
        weathers.add(current);
        JsonObject forecast = (JsonObject) weatherObject.get("forecast");
        JsonArray forecastDay = (JsonArray) forecast.get("forecastday");
        for (JsonElement day : forecastDay) {
            Weather weatherN = jsonReader.fromJson(day, Weather.class);
            weatherN.setDate();
            weathers.add(weatherN);
        }
        Log.i("test", weathers.toString());
        return weathers;

    }

    private static File getFile(Context context) {
        if (file == null || !file.exists()) {
            file = new File(context.getFilesDir(), fileLocation);
        }
        return file;
    }
}
