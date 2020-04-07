package com.example.hw1_mas.utilities;

import android.net.Uri;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

public class NetWorkUtil {
    private static final String MAP_BOX_URL = "https://api.mapbox.com/geocoding/v5/mapbox.places/";
    private static final String TOCKEN_PARAM = "access_token";
    private static final String FORMAT_JSON = ".json";
    private static final String MAP_BOX_TOKEN = "pk.eyJ1IjoibWFoZGlobWEiLCJhIjoiY2s4bjM5ZGd5MGkxYzNmbnNiZnFibGhwMyJ9.JCtipX_U1niNBdId-XpSgQ";
    private static final String TAG = "built url";

    public static URL mapBoxBuildUrl(String locationQuery) {

        String baseUrl = MAP_BOX_URL + locationQuery + FORMAT_JSON;

        Uri builtUri = Uri.parse(baseUrl).buildUpon()
                .appendQueryParameter(TOCKEN_PARAM, MAP_BOX_TOKEN)
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


