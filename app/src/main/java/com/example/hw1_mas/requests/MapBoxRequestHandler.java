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
import com.example.hw1_mas.models.City;
import com.example.hw1_mas.utilities.NetWorkUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

import static com.example.hw1_mas.MainActivity.REQUEST_ERROR;
import static com.example.hw1_mas.MainActivity.SEARCH_NOT_FOUND;
import static com.example.hw1_mas.MainActivity.SHOW_CITIES;
import static com.example.hw1_mas.MainActivity.UNSHOW_WAITING__BAR;

public class MapBoxRequestHandler {
    private Handler mHandler;
    private RequestQueue requestQueue;
    private static MapBoxRequestHandler instance;



    private MapBoxRequestHandler( Handler handler) {
        this.mHandler = handler;
        this.requestQueue = MainActivity.requestQueue;
    }


    public static void handleNewRequest(RequestQueue requestQueue,Handler handler,String  placeQuery) {
        URL url = NetWorkUtil.mapBoxBuildUrl(placeQuery);
        new MapBoxRequestHandler(handler).sendSearchRequest(url);
    }


    private void sendSearchRequest(URL url) {

         JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url.toString(), null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("On Response", response.toString());
                        ArrayList<City> resultCities = parseJsonResponse(response);
                        if (resultCities.size() == 0) {
                            Message message = new Message();
                            message.what = SEARCH_NOT_FOUND;
                            mHandler.sendMessage(message);
                        }
                        Message message = new Message();
                        message.what = SHOW_CITIES;
                        message.obj = resultCities;
                        mHandler.sendMessage(message);
                        Message unShowMesg = new Message();
                        unShowMesg.what = UNSHOW_WAITING__BAR;
                        mHandler.sendMessage(unShowMesg);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Message unShowMesg = new Message();
                        unShowMesg.what = UNSHOW_WAITING__BAR;
                        mHandler.sendMessage(unShowMesg);
                        Message message = new Message();
                        message.what = REQUEST_ERROR;
                        mHandler.sendMessage(message);

                    }
                });
        Log.i("MapBox","rquest created");
        requestQueue.add(jsonObjectRequest);
    }

    private ArrayList<City> parseJsonResponse(JSONObject response) {
        JsonArray features;
        Gson gson = new Gson();
        JsonObject gResponse = gson.fromJson(response.toString(), JsonObject.class);
        features = (JsonArray) gResponse.get("features");
        ArrayList<City> resultCities = new ArrayList<>();
        for (JsonElement feature : features) {
            resultCities.add(gson.fromJson(feature, City.class));
        }
        return resultCities;
    }
}
