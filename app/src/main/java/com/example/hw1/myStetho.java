/*
 * *
 *  * Created by Amit kremer ID 302863253 on 12/10/19 10:08 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 12/4/19 6:50 PM
 *
 */

package com.example.hw1;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class myStetho extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
