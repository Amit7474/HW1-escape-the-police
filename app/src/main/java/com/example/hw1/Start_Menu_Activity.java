/*
 * *
 *  * Created by Amit kremer ID 302863253 on 12/4/19 11:26 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 11/28/19 9:31 AM
 *
 */

package com.example.hw1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Start_Menu_Activity extends AppCompatActivity {

    private Button menu_BTN_start, menu_BTN_scoreList;
    private Button menu_BTN_settings;
    private MyLocationSensor mls;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start__manu_);

        menu_BTN_scoreList = findViewById(R.id.menu_BTN_scoreList);
        menu_BTN_start = findViewById(R.id.menu_BTN_start);
        menu_BTN_settings = findViewById(R.id.menu_BTN_settings);

        menu_BTN_scoreList.setOnClickListener(moveToMapActivity);
        menu_BTN_start.setOnClickListener(moveToMainActivity);
        menu_BTN_settings.setOnClickListener(moveToSetteingsAcivity);

        //Start collect location data
        mls = new MyLocationSensor(this);
    }

    /**
     * Listener of the Start button. Creates intent and move to the game activity
     */
    View.OnClickListener moveToMainActivity = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);

        }
    };

    /**
     * Listener of the Start button. Creates intent and move to the game activity
     */
    View.OnClickListener moveToMapActivity = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
            startActivity(intent);

        }
    };

    /**
     * Listener of the Settings button. Creates intent and move to the Settings activity
     */
    View.OnClickListener moveToSetteingsAcivity = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
        }
    };


}
