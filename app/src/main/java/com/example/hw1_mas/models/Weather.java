package com.example.hw1_mas.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Message;
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

import java.util.ArrayList;
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

    public void setDay(Day day) {
        this.day = day;
    }

    public LinearLayout getWeatherLayout(Context context) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        float locationFontSize = 16f;
        linearLayout.setPadding(10, 10, 10, 10);
        linearLayout.setDividerPadding(10);

        linearLayout.addView(getTextView(context, date != null ? date : "", locationFontSize));
        linearLayout.addView(getIconView(context));
        float temperatureFontSize = 10f;
        linearLayout.addView(getTextView(context, String.valueOf(day.getAverageTemp()), temperatureFontSize));
        return linearLayout;
    }

    private ImageView getIconView(Context context) {
        ImageView iconImageView = new ImageView(context);
        this.getDay().getCondition().getIconView(context, iconImageView);
        iconImageView.setScaleX(1.5f);
        iconImageView.setScaleY(1.5f);
        return iconImageView;
    }

    private TextView getTextView(Context context, String text, float fontSize) {
        TextView textView = new TextView(context);
        textView.setText(text);
        textView.setTextColor(Color.DKGRAY);
        textView.setTextSize(fontSize);
        return textView;
    }


}
