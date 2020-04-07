package com.example.hw1_mas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.hw1_mas.models.Weather;
import com.example.hw1_mas.requests.WeatherRequestHandler;

import java.util.ArrayList;

public class WeatherActivity extends AppCompatActivity {
    public static final int SHOW_CITIES = 100;
    public static final int SHOW_WAITING_BAR = 101;
    public static final int UNSHOW_WAITING__BAR = 102;
    public static final int ERROR_OCCUR = 103;
    public static final int FOUND = 104;
    private ProgressBar progressBar;
    private LinearLayout linearLayout;


    public Handler getHandler() {
        return handler;
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message inputMessage) {
            Log.i("HandleMessage", String.valueOf(inputMessage.what));
            super.handleMessage(inputMessage);
            switch (inputMessage.what) {
                case SHOW_WAITING_BAR:
                    progressBar.setVisibility(View.VISIBLE);
                    break;
                case UNSHOW_WAITING__BAR:
                    progressBar.setVisibility(View.INVISIBLE);
                    break;
                case FOUND:
                    showForecast(inputMessage.obj);
                    break;
            }
        }
    };

    private void showForecast(Object days) {
        ArrayList<Weather> daysArray = (ArrayList<Weather>) days;
        for (Weather weather : daysArray) {
            TextView textView = new TextView(this);
            String state = weather.getCondition().getState();
            textView.setText(state);
            linearLayout.addView(textView);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Message message = new Message();
        message.what = SHOW_WAITING_BAR;
        handler.sendMessage(message);
        Intent intent = getIntent();
        progressBar = (ProgressBar) findViewById(R.id.WeatherProgressBar);
        linearLayout = findViewById(R.id.linearLayout);
        int latitude = intent.getIntExtra("latitude", 0);
        int longitude = intent.getIntExtra("longitude", 0);
        WeatherRequestHandler.addWeatherRequest(latitude, longitude, this, handler);

    }


}
