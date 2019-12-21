/*
 * *
 *  * Created by Amit kremer ID 302863253 on 12/10/19 9:00 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 12/10/19 9:00 AM
 *
 */

package com.example.hw1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    private MySharedPref msp;
    private RadioButton settings_RBTN_fast;
    private RadioButton settings_RBTN_slow;
    private RadioButton settings_RBTN_buttons;
    private RadioButton settings_RBTN_Acceleromater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        msp = new MySharedPref(this);
        findViews();
        //Link the Radio buttons with click listener
        setClickListeners();


    }
    /**
     * Attach every view to id
     */
    private void findViews(){
        settings_RBTN_fast = findViewById(R.id.settings_RBTN_fast);
        settings_RBTN_slow = findViewById(R.id.settings_RBTN_slow);
        settings_RBTN_buttons = findViewById(R.id.settings_RBTN_buttons);
        settings_RBTN_Acceleromater = findViewById(R.id.settings_RBTN_Acceleromater);
    }
    /**
     * Attach every Radio button to listener
     */
    private void setClickListeners(){
        settings_RBTN_fast.setOnClickListener(onRadioButtonClicked);
        settings_RBTN_slow.setOnClickListener(onRadioButtonClicked);
        settings_RBTN_buttons.setOnClickListener(onRadioButtonClicked);
        settings_RBTN_Acceleromater.setOnClickListener(onRadioButtonClicked);
    }

    /**
     * Radio buttons listener for selecting game speed and car control method
     */
    View.OnClickListener onRadioButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Is the button now checked?
            boolean checked = ((RadioButton) v).isChecked();
            // Check which radio button was clicked
            switch (v.getId()) {
                case R.id.settings_RBTN_fast:
                    if (checked)
                        msp.putInt("speed", 250);
                    Toast.makeText(SettingsActivity.this, "Fast speed selected", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.settings_RBTN_slow:
                    if (checked)
                        msp.putInt("speed", 500);
                        Toast.makeText(SettingsActivity.this, "Slow speed selected", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.settings_RBTN_buttons:
                    if (checked)
                        msp.putString("control", "B");
                        Toast.makeText(SettingsActivity.this, "Control car with Buttons", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.settings_RBTN_Acceleromater:
                    if (checked)
                        msp.putString("control", "A");
                    Toast.makeText(SettingsActivity.this, "Control car with Rotation Sensor", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
}
