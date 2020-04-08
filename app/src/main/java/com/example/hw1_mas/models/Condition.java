package com.example.hw1_mas.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.hw1_mas.MainActivity;
import com.example.hw1_mas.WeatherActivity;
import com.google.gson.annotations.SerializedName;

import java.io.InputStream;

public class Condition {
    @SerializedName("text")
    private String state;
    private String icon;
    private int code;

    public String getState() {
        return state;
    }

    public String getIconURL() {
        return "https:" + icon;
    }

    public void getIconView(Context context, ImageView iconView) {

        new DownloadImageTask(iconView)
                .execute(getIconURL());

    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    public int getCode() {
        return code;
    }
}
