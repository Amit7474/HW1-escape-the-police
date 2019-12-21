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

import static android.content.Context.MODE_PRIVATE;

public class MySharedPref {

    SharedPreferences prefs;

    public MySharedPref(Context context) {
        prefs = context.getSharedPreferences("myPref", MODE_PRIVATE);
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

    public void removeKey(String key) {
        prefs.edit().remove(key);

    }

}
