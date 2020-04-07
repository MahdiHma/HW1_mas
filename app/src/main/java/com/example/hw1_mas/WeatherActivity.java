package com.example.hw1_mas;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;

import com.example.hw1_mas.requests.WeatherRequestHandler;

public class WeatherActivity extends AppCompatActivity {
    public static final int SHOW_CITIES = 100;
    public static final int SHOW_WAITING_BAR = 101;
    public static final int UNSHOW_WAITING__BAR = 102;
    public static final int REQUEST_ERROR = 103;
    public static final int FOUND = 104;

    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message inputMessage) {

            switch (inputMessage.what) {
                case SHOW_WAITING_BAR:
                    progressBar.setVisibility(View.INVISIBLE);
                    break;
                case UNSHOW_WAITING__BAR:
                    progressBar.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };

    public Message sendRequest() {
        Message message = null;
        new Thread() {
            @Override
            public void run() {
                WeatherRequestHandler.addWeatherRequest(latitude, longitude, getApplicationContext());

            }
        }.start();
        return message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Message message = new Message();
        message.what = SHOW_WAITING_BAR;
        handler.sendMessage(message);
        Intent intent = getIntent();
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.WeatherProgressBar);
        int latitude = intent.getIntExtra("latitude", 0);
        int longitude = intent.getIntExtra("longitude", 0);

    }


}
