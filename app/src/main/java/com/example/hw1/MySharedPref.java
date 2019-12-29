/*
 * *
 *  * Created by Amit kremer ID 302863253 on 12/10/19 9:43 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 12/4/19 6:36 PM
 *
 */

package com.example.hw1;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class MySharedPref {

    SharedPreferences prefs;

    public MySharedPref(Context context) {
        prefs = context.getSharedPreferences("myPref", MODE_PRIVATE);
    }

    public void putFloat(String key, Float val) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat(key, val);
        editor.apply();
    }

    public float getFloat(String key, Float defaultValue) {
        return prefs.getFloat(key, defaultValue);
    }

    public void putString(String key, String val) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, val);
        editor.apply();
    }

    public String getString(String key, String defaultValue) {
        return prefs.getString(key, defaultValue);
    }

    public void putInt(String key, int val) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, val);
        editor.apply();
    }

    public int getInt(String key, int defaultValue) {
        return prefs.getInt(key, defaultValue);
    }

    public void putArrayList(ArrayList<Record> list) {
        Type recordType = new TypeToken<ArrayList<Record>>() {
        }.getType();
        this.putString("scoreList", new Gson().toJson(list, recordType));
    }

    public ArrayList<Record> getArrayList(String list, String defaultValue) {
        String scoreSetJson = this.getString(list, defaultValue);
        if (!scoreSetJson.isEmpty()) {
            if (!scoreSetJson.equalsIgnoreCase("na")) {
                Type recordType = new TypeToken<ArrayList<Record>>() {
                }.getType();
                return new Gson().fromJson(scoreSetJson, recordType);
            }
        }
        return new ArrayList<>();
    }


    public void removeKey(String key) {
        prefs.edit().remove(key);

    }

}
