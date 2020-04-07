package com.example.hw1_mas.utilities;

import android.net.Uri;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

public class WeatherURLBuilder {
    private static final String WeatherApiUrl = "https://api.weatherapi.com/v1/forecast.json";
    private static final String TOKEN_PARAM = "key";
    private static final String placeQuery = "q";
    private static final String WeatherAPIToken = "278a92b995b649e180a132552200504";
    private static final String TAG = "built url";

    public static URL mapBoxBuildUrl(int longitude, int latitude) {
        String place = String.valueOf(longitude) + ',' + latitude;
        Uri builtUri = Uri.parse(WeatherApiUrl).buildUpon()
                .appendQueryParameter(placeQuery, place)
                .appendQueryParameter(TOKEN_PARAM, WeatherAPIToken)
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
