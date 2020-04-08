package com.example.hw1_mas.utilities;

import android.net.Uri;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

public class NetWorkUtil {
    private static final String MAP_BOX_URL = "https://api.mapbox.com/geocoding/v5/mapbox.places/";
    private static final String MAP_BOX_TOCKEN_PARAM = "access_token";
    private static final String FORMAT_JSON = ".json";
    private static final String MAP_BOX_TOKEN = "pk.eyJ1IjoibWFoZGlobWEiLCJhIjoiY2s4bjM5ZGd5MGkxYzNmbnNiZnFibGhwMyJ9.JCtipX_U1niNBdId-XpSgQ";
    private static final String TAG = "built url";


    private static final String WEATHER_API_URL = "https://api.weatherapi.com/v1/forecast.json";
    private static final String WEATHER_API_TOKEN_PARAM = "key";
    private static final String PLACE_PARAM = "q";
    private static final String WEATHER_API_TOKEN = "278a92b995b649e180a132552200504";
    private static final String DAY_PARAM = "days";
    private static final String DAY_NUMBER = "7";


    public static URL mapBoxBuildUrl(String locationQuery) {

        String baseUrl = MAP_BOX_URL + locationQuery + FORMAT_JSON;

        Uri builtUri = Uri.parse(baseUrl).buildUpon()
                .appendQueryParameter(MAP_BOX_TOCKEN_PARAM, MAP_BOX_TOKEN)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }


    public static URL weatherBuileUrl(double latitude, double longitude) {
        String place = String.valueOf(latitude) + ',' + longitude;
        Uri builtUri = Uri.parse(WEATHER_API_URL).buildUpon()
                .appendQueryParameter(PLACE_PARAM, place)
                .appendQueryParameter(WEATHER_API_TOKEN_PARAM, WEATHER_API_TOKEN)
                .appendQueryParameter(DAY_PARAM, DAY_NUMBER)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }
}


