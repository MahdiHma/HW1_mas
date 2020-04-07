package com.example.hw1_mas.requests;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hw1_mas.WeatherActivity;
import com.example.hw1_mas.utilities.WeatherURLBuilder;
import com.google.gson.JsonObject;

import org.json.JSONObject;

public class WeatherRequestHandler {
    private static RequestQueue requestQueue;
    private static WeatherRequestHandler requestHandler;
    private Handler handler;
    private static final int FOUND = 101;
    private static final int ERROR_OCCUR = 102;


    private WeatherRequestHandler(Context context) {
        WeatherRequestHandler.requestHandler = this;
        WeatherRequestHandler.requestQueue = Volley.newRequestQueue(context);
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message inputMessage) {
                switch (inputMessage.what) {
                    case FOUND:
                        break;
                    case ERROR_OCCUR:
                        break;
                }
            }
        };
    }

    public static void addWeatherRequest(int latitude, int longitude, Context context) {
        if (WeatherRequestHandler.requestHandler == null) {
            WeatherRequestHandler.requestHandler = new WeatherRequestHandler(context);
        }
        String url = WeatherURLBuilder.weatherApiParse(latitude, longitude).toString();
        requestQueue.add(WeatherRequestHandler.buildRequest(url));
    }

    private static JsonObjectRequest buildRequest(String url) {
        final JSONObject weatherJson = new JSONObject();
        return new JsonObjectRequest(Request.Method.GET, url, weatherJson, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("Just_for_test", response.toString());
                Message message = new Message();
                message.what = WeatherActivity.FOUND;
                message.obj = WeatherRequestParser.fromJson(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Just_for_test", error.toString());
            }
        });
    }
}
