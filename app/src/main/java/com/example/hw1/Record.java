/*
 * *
 *  * Created by Amit kremer ID 302863253 on 12/12/19 6:40 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 12/12/19 6:40 PM
 *
 */

package com.example.hw1;

public class Record implements Comparable<Record> {

    private String name;
    private int score;
    private double longitude;
    private double latitude;

    public Record(String name, int score, double lat, double lon) {
        this.name = name;
        this.score = score;
        this.longitude = lon;
        this.latitude = lat;
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
        return this.getName() + "           (Score: "+ this.getScore()+")";
    }

    @Override
    public int compareTo(Record o) {
        return o.score - this.score;
    }


}


