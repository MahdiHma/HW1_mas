package com.example.hw1_mas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hw1_mas.utilities.NetWorkUtil;
import com.example.hw1_mas.utilities.NetWorkUtil.*;

import java.net.URL;
import java.text.BreakIterator;

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
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url.toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        searchResultTv.setText("Response is: "+ response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                searchResultTv.setText("That didn't work!");
            }
        });
        queue.add(stringRequest);
    }
}
