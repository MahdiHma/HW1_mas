package com.example.hw1_mas.requests;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hw1_mas.MainActivity;
import com.example.hw1_mas.WeatherActivity;
import com.example.hw1_mas.models.Day;
import com.example.hw1_mas.models.Weather;
import com.example.hw1_mas.utilities.NetWorkUtil;

import org.json.JSONObject;

import static com.example.hw1_mas.MainActivity.REQUEST_ERROR;
import static com.example.hw1_mas.MainActivity.SEARCH_NOT_FOUND;
import static com.example.hw1_mas.MainActivity.SHOW_CITIES;
import static com.example.hw1_mas.MainActivity.UNSHOW_WAITING__BAR;

import java.io.IOException;
import java.util.ArrayList;

public class WeatherRequestHandler {
    private static RequestQueue requestQueue;
    private static WeatherRequestHandler requestHandler;
    //todo move string to res
    private static String noApiError = "cannot find the place";
    private Handler handler;

    private WeatherRequestHandler(Handler handler) {
        this.handler = handler;
        WeatherRequestHandler.requestHandler = this;
        WeatherRequestHandler.requestQueue = MainActivity.requestQueue;
    }

    public static void addWeatherRequest(double latitude, double longitude, Handler handler, Context context) {
        WeatherRequestHandler.requestHandler = new WeatherRequestHandler(handler);
        String url = NetWorkUtil.weatherBuileUrl(latitude, longitude).toString();
        requestQueue.add(WeatherRequestHandler.buildRequest(url, context));
    }

    private static JsonObjectRequest buildRequest(String url, final Context context) {
        final JSONObject weatherJson = new JSONObject();
        return new JsonObjectRequest(Request.Method.GET, url, weatherJson, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Message message = new Message();
                message.what = UNSHOW_WAITING__BAR;
                requestHandler.handler.sendMessage(message);
                message = new Message();
                message.what = WeatherActivity.FOUND;
                message.obj = WeatherRequestParser.fromJson(response);
                ArrayList<Weather> r = WeatherRequestParser.fromJson(response);
                WeatherRequestParser.saveJson(r, context);
                try {
                    WeatherRequestParser.loadJson(context);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                requestHandler.handler.sendMessage(message);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Message message = new Message();
                message.what = UNSHOW_WAITING__BAR;
                requestHandler.handler.sendMessage(message);
                message = new Message();
                message.what = WeatherActivity.ERROR_OCCUR;
                message.obj = WeatherRequestHandler.noApiError;
                requestHandler.handler.sendMessage(message);

            }
        });
    }
}
