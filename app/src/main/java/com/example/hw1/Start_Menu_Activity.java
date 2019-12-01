package com.example.hw1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Start_Menu_Activity extends AppCompatActivity {

    private Button menu_BTN_start;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start__manu_);

        menu_BTN_start = findViewById(R.id.menu_BTN_start);
        menu_BTN_start.setOnClickListener(moveToMainActivity);

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
}
