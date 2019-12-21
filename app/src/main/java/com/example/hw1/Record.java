/*
 * *
 *  * Created by Amit kremer ID 302863253 on 12/12/19 6:40 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 12/12/19 6:40 PM
 *
 */

package com.example.hw1;

import android.location.Location;

public class Record implements Comparable<Record> {

    private String name;
    private int score;
    private double longitude;
    private double latitude;

    public Record(String name, int score, Location location) {
        this.name = name;
        this.score = score;
        this.longitude = location.getLongitude();
        this.latitude = location.getLatitude();
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    @Override
    public String toString() {
        return "Record{" +
                "name='" + this.getName() + '\'' +
                ", score=" + this.getScore() +
                ", longitude=" + this.getLongitude() +
                ", latitude=" + this.getLatitude() +
                '}';
    }

    @Override
    public int compareTo(Record o) {
        return this.score - o.score;
    }


}

