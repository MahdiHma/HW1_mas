package com.example.hw1_mas;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.hw1_mas.models.City;
import com.example.hw1_mas.requests.MapBoxRequestHandler;
import com.example.hw1_mas.requests.WeatherRequestHandler;
import com.example.hw1_mas.utilities.NetWorkUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final int SHOW_CITIES = 100;
    public static final int SHOW_WAITING_BAR = 101;
    public static final int UNSHOW_WAITING__BAR = 102;
    public static final int REQUEST_ERROR = 103;
    public static final int SEARCH_NOT_FOUND = 104;
    public static final int INTERNET_NOT_CONNECTED = 105;


    ProgressBar progressBar;
    EditText locationSearchBox;
    TextView searchErrorTv;
    Button searchBtn;
    LinearLayout llResults;

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SHOW_CITIES:
                    showCities((ArrayList<City>) msg.obj);
                    break;
                case SHOW_WAITING_BAR:
                    progressBar.setVisibility(View.VISIBLE);
                    break;
                case UNSHOW_WAITING__BAR:
                    progressBar.setVisibility(View.INVISIBLE);
                    break;
                case SEARCH_NOT_FOUND:
                    searchErrorTv.setText(R.string.city_not_found);
                    break;
                case REQUEST_ERROR:
                    searchErrorTv.setText(R.string.error_in_request);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + msg.what);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationSearchBox = findViewById(R.id.et_location_search);
        searchErrorTv = findViewById(R.id.tv_search_error);
        searchBtn = findViewById(R.id.btn_search);
        llResults = findViewById(R.id.ll_results);
        progressBar = findViewById(R.id.pb_results);
        if (!checkInternetConnectivity()) {
            showInternetNotConnectedError();
        }
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchQuery = locationSearchBox.getText().toString();
                searchErrorTv.setText("");
                progressBar.setVisibility(View.VISIBLE);
                MapBoxRequestHandler.handleNewRequest(getApplicationContext(), mHandler,searchQuery);
            }
        });


    }


    private void switchPage(City city) {
        Intent intent = new Intent(this, WeatherActivity.class);
        intent.putExtra("latitude", city.getCenter()[1]);
        intent.putExtra("longitude", city.getCenter()[0]);
        startActivity(intent);
    }

    private void showCities(ArrayList<City> cities) {
        llResults.removeAllViews();
        for (final City city : cities) {
            Button btn = new Button(this);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    switchPage(city);

                }
            });
            btn.setText(city.getPlace_name());
            llResults.addView(btn);
        }
    }

    private boolean checkInternetConnectivity() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }


    private void showInternetNotConnectedError() {
        Toast toast = Toast.makeText(getApplicationContext(), R.string.no_internet_connection, Toast.LENGTH_LONG);
        toast.show();
    }
}
