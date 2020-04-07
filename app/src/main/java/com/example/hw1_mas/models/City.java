package com.example.hw1_mas.models;

import java.util.Arrays;

public class City {
    String place_name;
    float[] center;

    public String getPlace_name() {
        return place_name;
    }

    public float[] getCenter() {
        return center;
    }

    public City(String place_name, float[] center) {
        this.place_name = place_name;
        this.center = center;
    }

    @Override
    public String toString() {
        return "City{" +
                "place name='" + place_name + '\'' +
                ", center =" + Arrays.toString(center) +
                '}';
    }
}
