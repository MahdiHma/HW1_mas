package com.example.hw1_mas.requests;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hw1_mas.utilities.WeatherURLBuilder;
import com.google.gson.JsonObject;

import org.json.JSONObject;

public class WeatherRequestHandler {
    private static RequestQueue requestQueue;
    private static WeatherRequestHandler requestHandler;

    private WeatherRequestHandler(Context context) {
        WeatherRequestHandler.requestHandler = this;
        WeatherRequestHandler.requestQueue = Volley.newRequestQueue(context);
    }

    public static void addWeatherRequest(String url, Context context) {
        if (WeatherRequestHandler.requestHandler == null) {
            WeatherRequestHandler.requestHandler = new WeatherRequestHandler(context);
        }
//        url = WeatherURLBuilder.mapBoxBuildUrl(0,0).toString();
        requestQueue.add(WeatherRequestHandler.buildRequest(url));
    }

    private static JsonObjectRequest buildRequest(String url) {
        final JSONObject weatherJson = new JSONObject();
        return new JsonObjectRequest(Request.Method.GET, url, weatherJson, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("Just_for_test", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Just_for_test", error.toString());
            }
        });
    }
}
