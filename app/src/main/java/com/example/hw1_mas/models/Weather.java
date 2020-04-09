package com.example.hw1_mas.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.nio.charset.Charset;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.example.hw1_mas.MainActivity;
import com.example.hw1_mas.R;
import com.example.hw1_mas.WeatherActivity;
import com.google.gson.annotations.SerializedName;

public class Weather {
    private String date;
    private Day day;
    private String locationName;

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getDate() {
        return date;
    }

    public Day getDay() {
        return day;
    }

    public void setDate() {
        String pattern = "EEE, MMM d HH:mm";
        if (date != null) {
            pattern = "EEE, MMM d";
            String pattern1 = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(pattern1);
            Date date1 = sdf.parse(date, new ParsePosition(0));
            sdf = new SimpleDateFormat(pattern);
            date = sdf.format(date1);
            return;
        }

        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        date = simpleDateFormat.format(now);


    }


    public void setDay(Day day) {
        this.day = day;

    }


    public LinearLayout getWeatherLayout(Context context) {
        float locationFontSize = 16f;
        float temperatureFontSize = 12f;
        float stateFontSize = 15f;
        int dayColor = Color.rgb(0, 100, 240);
        int tempColor = Color.rgb(20, 20, 150);
        int stateColor = Color.rgb(0, 200, 50);
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(0, 30, 0, 30);
        linearLayout.addView(getTextView(context, date, locationFontSize, dayColor));
        LinearLayout weatherIconAndStatus = new LinearLayout(context);
        weatherIconAndStatus.addView(getIconView(context));
        weatherIconAndStatus.addView(getTextView(context, day.getAverageTemp(), temperatureFontSize, tempColor));
        weatherIconAndStatus.setGravity(Gravity.CENTER);
        linearLayout.addView(weatherIconAndStatus);
        linearLayout.addView(getTextView(context, getDay().getCondition().getState(), stateFontSize, stateColor));
        return linearLayout;
    }

    private ImageView getIconView(Context context) {
        ImageView iconImageView = new ImageView(context);
        this.getDay().getCondition().getIconView(context, iconImageView);
        iconImageView.setScaleX(1.8f);
        iconImageView.setScaleY(1.8f);
        iconImageView.setPadding(0, 10, 20, 10);
        return iconImageView;
    }

    private TextView getTextView(Context context, String text, float fontSize, int color) {
        TextView textView = new TextView(context);
        textView.setText(text);
        textView.setTextColor(color);
        textView.setTextSize(fontSize);
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(0, 2, 0, 2);
        return textView;
    }


}
