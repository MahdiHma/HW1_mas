package com.example.hw1_mas;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hw1_mas.models.City;
import com.example.hw1_mas.utilities.NetWorkUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.net.URL;
import java.text.BreakIterator;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText locationSearchBox;
    TextView searchResultTv;
    Button searchBtn;
    LinearLayout llResults;
    Handler mHandler;
    ProgressBar progressBar;
    private static final int SHOW_CITIES = 100;
    private static final int SHOW_WAITING_BAR = 101;
    private static final int UNSHOW_WAITING__BAR =102;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationSearchBox = findViewById(R.id.et_location_search);
        searchResultTv = findViewById(R.id.tv_search_result);
        searchBtn = findViewById(R.id.btn_search);
        llResults = findViewById(R.id.ll_results);
        progressBar = findViewById(R.id.pb_results);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchQuery = locationSearchBox.getText().toString();
                final URL url = NetWorkUtil.mapBoxBuildUrl(searchQuery);
                Thread handleRequest = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message showMessage = new Message();
                        showMessage.what = SHOW_WAITING_BAR;
                        mHandler.sendMessage(showMessage);
                        sendSearchRequest(url);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Message unShowMesg = new Message();
                        unShowMesg.what = UNSHOW_WAITING__BAR;
                        mHandler.sendMessage(unShowMesg);
                    }
                });
                handleRequest.start();
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
                        ArrayList<City> resultCities = new ArrayList<>();
                        for (JsonElement feature : features) {
                            resultCities.add(gson.fromJson(feature,City.class));
                        }
                        Message message = new Message();
                        message.what = SHOW_CITIES;
                        message.obj = resultCities;
                        mHandler.sendMessage(message);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                    }
                });
        queue.add(jsonObjectRequest);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mHandler = new Handler(){
            @SuppressLint("HandlerLeak")
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case SHOW_CITIES:
                        showCities((ArrayList<City>) msg.obj);
                        break;
                    case SHOW_WAITING_BAR:
                        progressBar.setVisibility(View.VISIBLE);
                        Log.i("handler", "show");
                        break;
                    case UNSHOW_WAITING__BAR:
                        progressBar.setVisibility(View.INVISIBLE);
                        Log.i("handler", "unshow");
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + msg.what);
                }
            }
        };
    }

    private void showCities(ArrayList<City> cities) {
        for (City city : cities) {
            Button btn = new Button(this);
            btn.setText(city.getPlace_name());
            llResults.addView(btn);
        }

        ;
    }
}
