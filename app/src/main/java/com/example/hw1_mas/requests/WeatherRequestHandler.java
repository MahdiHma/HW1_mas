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
import com.example.hw1_mas.WeatherActivity;
import com.example.hw1_mas.models.Day;
import com.example.hw1_mas.models.Weather;
import com.example.hw1_mas.utilities.WeatherURLBuilder;

import org.json.JSONObject;

import java.util.ArrayList;

public class WeatherRequestHandler {
    private static RequestQueue requestQueue;
    private static WeatherRequestHandler requestHandler;
    private static String noApiError = "cannot find the place";
    private Handler handler;

    private WeatherRequestHandler(Context context, Handler handler) {
        this.handler = handler;
        WeatherRequestHandler.requestHandler = this;
        WeatherRequestHandler.requestQueue = Volley.newRequestQueue(context);
    }

    public static void addWeatherRequest(double latitude, double longitude, Context context, Handler handler) {
        WeatherRequestHandler.requestHandler = new WeatherRequestHandler(context, handler);
        String url = WeatherURLBuilder.weatherApiParse(latitude, longitude).toString();
        requestQueue.add(WeatherRequestHandler.buildRequest(url, context));
    }

    private static JsonObjectRequest buildRequest(String url, final Context context) {
        final JSONObject weatherJson = new JSONObject();
        return new JsonObjectRequest(Request.Method.GET, url, weatherJson, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Message message = new Message();
                message.what = WeatherActivity.UNSHOW_WAITING__BAR;
                requestHandler.handler.sendMessage(message);
                message = new Message();
                message.what = WeatherActivity.FOUND;
                message.obj = WeatherRequestParser.fromJson(response);
                ArrayList<Weather> r = WeatherRequestParser.fromJson(response);
                WeatherRequestParser.saveJson(r, context);

                requestHandler.handler.sendMessage(message);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Message message = new Message();
                message.what = WeatherActivity.UNSHOW_WAITING__BAR;
                requestHandler.handler.sendMessage(message);
                message = new Message();
                message.what = WeatherActivity.ERROR_OCCUR;
                message.obj = WeatherRequestHandler.noApiError;
                requestHandler.handler.sendMessage(message);

            }
        });
    }
}
