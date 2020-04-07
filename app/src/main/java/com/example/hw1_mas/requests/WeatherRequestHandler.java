package com.example.hw1_mas.requests;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONObject;

public class WeatherRequestHandler {
    private static RequestQueue requestQueue;
    private static WeatherRequestHandler requestHandler;

    public WeatherRequestHandler(Context context) {
        WeatherRequestHandler.requestHandler = this;
        WeatherRequestHandler.requestQueue = Volley.newRequestQueue(context);
    }

    public static void addWeatherRequest(String url, Context context) {
        if (WeatherRequestHandler.requestHandler == null) {
            WeatherRequestHandler.requestHandler = new WeatherRequestHandler(context);
        }
        requestQueue.add(WeatherRequestHandler.buildRequest(url));
    }

    private static JsonObjectRequest buildRequest(String url) {
        final JSONObject weatherJson = new JSONObject();
        JsonObjectRequest weatherJsonRequest = new JsonObjectRequest(Request.Method.GET, url, weatherJson, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("salam");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        return weatherJsonRequest;
    }
}
