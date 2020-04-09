package com.example.hw1_mas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hw1_mas.models.Weather;
import com.example.hw1_mas.requests.WeatherRequestHandler;
import com.example.hw1_mas.requests.WeatherRequestParser;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.hw1_mas.MainActivity.SHOW_WAITING_BAR;
import static com.example.hw1_mas.MainActivity.UNSHOW_WAITING__BAR;

public class WeatherActivity extends AppCompatActivity {
    //todo merge these consts with mainActivity consts
    public static final int ERROR_OCCUR = 103;
    public static final int NOT_CACHED = 107;
    public static final int FOUND = 104;

    public static final int SET_ICON = 105;
    private String notCachedError = "you did not have cached data and internet connection. please try again later";
    private ProgressBar progressBar;
    private ScrollView scrollView;
    private LinearLayout linearLayout;

    public Handler getHandler() {
        return handler;
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message inputMessage) {
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
//                case NOT_CACHED:
//                    raiseError(inputMessage);
//                    break;
                case ERROR_OCCUR:
                    raiseError(inputMessage);
                    break;

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        progressBar = (ProgressBar) findViewById(R.id.WeatherProgressBar);
        progressBar.setVisibility(View.VISIBLE);
        scrollView = findViewById(R.id.scrollView2);
        linearLayout = findViewById(R.id.linear_layout);
        getWeather();
    }

    public void getWeather() {
        Intent intent = getIntent();

        boolean isConnected = intent.getBooleanExtra("connected", false);
        if (isConnected) {
            float latitude = intent.getFloatExtra("latitude", 0);
            float longitude = intent.getFloatExtra("longitude", 0);
            WeatherRequestHandler.addWeatherRequest(latitude, longitude, handler, getApplicationContext());
        } else {
            try {
                Message message = new Message();
                message.what = FOUND;
                message.obj = WeatherRequestParser.loadJson(this);
                handler.sendMessage(message);
//                showForecast(WeatherRequestParser.loadJson(this));
            } catch (Exception e) {
                Message message = new Message();
                e.printStackTrace();
//                Log.i("woooooww", );
                message.what = ERROR_OCCUR;
                message.obj = notCachedError;
                handler.sendMessage(message);
            }
        }
    }


    private void raiseError(Message inputMessage) {
        TextView textView = new TextView(this);
        String error = (String) inputMessage.obj;
        textView.setText(error);
        textView.setTextColor(Color.parseColor("red"));
        linearLayout.removeAllViews();
        linearLayout.addView(textView);
    }

    private void showForecast(Object days) {
        ArrayList<Weather> daysArray = (ArrayList<Weather>) days;
        linearLayout.removeAllViews();
        for (Weather weather : daysArray) {
            TextView textView = new TextView(this);
            String state = String.valueOf(weather.getDay().getCondition().getState());
            textView.setText(state);
            linearLayout.addView(weather.getWeatherLayout(this));
        }
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}
