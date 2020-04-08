package com.example.hw1_mas.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Message;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.Date;

import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.example.hw1_mas.MainActivity;
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

    public ConstraintLayout getWeatherLayout(Context context) {
        ConstraintLayout linearLayout = new ConstraintLayout(context);
        float locationFontSize = 16f;
        linearLayout.setPadding(10, 10, 10, 10);
//        linearLayout(getTextView(context, date != null ? date : "", locationFontSize));
        linearLayout.addView(getIconView(context));
        float temperatureFontSize = 10f;
        linearLayout.addView(getTextView(context, String.valueOf(day.getAverageTemp()), temperatureFontSize));
        return linearLayout;
    }

    private ImageView getIconView(Context context) {
        ImageView iconImageView = new ImageView(context);
        this.getDay().getCondition().getIconView(context, iconImageView);
        iconImageView.setScaleX(3);
        iconImageView.setScaleY(3);
        return iconImageView;
    }

    private TextView getTextView(Context context, String text, float fontSize) {
        TextView textView = new TextView(context);
        textView.setText(text);
        textView.setTextColor(Color.DKGRAY);
        textView.setTextSize(fontSize);
        textView.setPadding(10, 10, 10, 10);
        return textView;
    }

//
//    private void getImageRequest(final ImageView imageView) {
//        return MainActivity.requestQueue.add(new ImageRequest(getIconURL(), new Response.Listener<Bitmap>() {
//            @Override
//            public void onResponse(Bitmap response) {
//                Message message = new Message();
//                message.what = WeatherActivity.SET_ICON;
//                ArrayList<Object> arrayList = new ArrayList<>();
//                arrayList.add(imageView);
//                arrayList.add(response);
//                message.obj = arrayList;
//            }
//        }, 0, 0, null, null));
//    }

}
