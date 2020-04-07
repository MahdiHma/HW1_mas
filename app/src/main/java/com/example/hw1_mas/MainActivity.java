package com.example.hw1_mas;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hw1_mas.models.City;
import com.example.hw1_mas.utilities.NetWorkUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.net.URL;

//import com.mapbox.api.geocoding.v5.MapboxGeocoding;

public class MainActivity extends AppCompatActivity {
    EditText locationSearchBox;
    TextView searchResultTv;
    Button searchBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationSearchBox = findViewById(R.id.et_location_search);
        searchResultTv = findViewById(R.id.tv_search_result);
        searchBtn = findViewById(R.id.btn_search);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchQuery = locationSearchBox.getText().toString();
                URL url = NetWorkUtil.mapBoxBuildUrl(searchQuery);
                sendSearchRequest(url);
            }
        });

    }

    private void sendSearchRequest(URL url) {
        RequestQueue queue = Volley.newRequestQueue(this);
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url.toString(), null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        JsonArray features;
                        Gson gson = new Gson();
                        JsonObject gResponse = gson.fromJson(response.toString(), JsonObject.class);
                        features = (JsonArray) gResponse.get("features");
                        City city = gson.fromJson(features,City.class);
                        searchResultTv.setText("Response: " + city.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });
        queue.add(jsonObjectRequest);
    }
}
