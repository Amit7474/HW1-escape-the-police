/*
 * *
 *  * Created by Amit kremer ID 302863253 on 12/4/19 11:25 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 11/28/19 9:30 AM
 *
 */

package com.example.hw1;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.TreeSet;

public class GameOverActivity extends AppCompatActivity {

    private Button gameOver_BTN_playAgain, gameOver_BTN_save, gameOver_BTN_scoreList;
    private TextView gameOver_TXT_score;
    private MyMediaPlayer player;
    private String playerName;
    private int playerScore;
    private double latitude, longitude;
    private MySharedPref msp;
    private ArrayList<Record> scoreList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        scoreList = new ArrayList<>();
        findViews();

        //Get the player's score from the game activity by the intent
        Intent intent = getIntent();
        playerScore = getScoreFromIntemt(intent);
        gameOver_TXT_score.setText("Your score is " + playerScore + "pts");

        //Check if the player's score is high enough for save
        if (isOKtoSave()) {
            changeButtonConfig(gameOver_BTN_save, true);
            if (scoreList.size() == 10)
                scoreList.remove(scoreList.get(scoreList.size() - 1));
        } else {
            changeButtonConfig(gameOver_BTN_save, false);
            Toast.makeText(this, "Your score CANNOT enter the record list!", Toast.LENGTH_SHORT).show();
        }

        //Play the 'Game over' sound
        player = new MyMediaPlayer(this, 1);
        player.start();

        //Click listeners
        gameOver_BTN_playAgain.setOnClickListener(playAgain);
        gameOver_BTN_save.setOnClickListener(saveScore);
        gameOver_BTN_scoreList.setOnClickListener(printScore);

    }

    /**
     * Gets all the player's details and determines if he should be saved
     *
     * @return boolean value
     */
    private boolean isOKtoSave() {
        getPlayerLocationFromShared();
        getSoreListFromShared();
        if (scoreList.isEmpty() || scoreList.size() < 10) {
            return true;
        } else {
            Record lastRecord = getTheLastRecord();
            return playerScore - lastRecord.getScore() > 0;
        }
    }

    /**
     * Gets the score list(if available) from SharedPreferences
     */
    private void getSoreListFromShared() {
        scoreList = msp.getArrayList("scoreList", "na");
    }

    private Record getTheLastRecord() {
        return scoreList.get(scoreList.size() - 1);

    }

    /**
     * Gets the player's location from SharedPreferences
     */
    private void getPlayerLocationFromShared() {
        msp = new MySharedPref(this);
        latitude = (double) msp.getFloat("playerLatitude", 0f);
        longitude = (double) msp.getFloat("playerLongitude", 0f);
    }

    /**
     * Gets a button and boolean value and sets the button to "click mode" OR "disable mode"
     *
     * @param btn
     * @param shouldBeEnable
     */
    private void changeButtonConfig(Button btn, boolean shouldBeEnable) {
        if (shouldBeEnable) {
            btn.setAlpha(1);
            btn.setClickable(true);
        } else {
            btn.setAlpha(0.5f);
            btn.setClickable(false);
        }
    }

    /**
     * Attach every view to id
     */
    private void findViews() {
        gameOver_BTN_playAgain = findViewById(R.id.gameOver_BTN_playAgain);
        gameOver_TXT_score = findViewById(R.id.gameOver_TXT_score);
        gameOver_BTN_scoreList = findViewById(R.id.gameOver_BTN_scoreList);
        gameOver_BTN_save = findViewById(R.id.gameOver_BTN_save);
    }


    View.OnClickListener printScore = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            for (int i = 0; i < scoreList.size(); i++) {
                Log.i("Score", scoreList.get(i) + "");
            }

            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
            startActivity(intent);
        }
    };
    /**
     * Save the player's score on Sharded preferences
     */
    View.OnClickListener saveScore = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getPlayerName();
        }
    };

    /**
     * @param intent
     * @return The player's score
     */
    public int getScoreFromIntemt(Intent intent) {
        return intent.getIntExtra("score", 0);
    }


    /**
     * Play again listener. Close this activity and goes to menu activity
     */
    View.OnClickListener playAgain = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), Start_Menu_Activity.class);
            startActivity(intent);
            finish();
        }
    };

    /**
     * Open input alert dialog, gets the player's name, creates a new record and adds it to TreeSet
     */
    public void getPlayerName() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter your name");
        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                playerName = input.getText().toString();
                if (playerName.isEmpty()) {
                    //Notify the player that he cannot save score with no name
                    Toast.makeText(GameOverActivity.this, "Name is undefined", Toast.LENGTH_SHORT).show();
                } else {
                    scoreList.add(new Record(playerName, playerScore, latitude, longitude));
                    changeButtonConfig(gameOver_BTN_save, false);
                    changeButtonConfig(gameOver_BTN_scoreList, true);
                    sortAndSaveScoreList();
                }
            }
        });
        builder.show();
    }

    /**
     * Sorts the score List and saves it in SharedPreferences
     */
    private void sortAndSaveScoreList() {
        Collections.sort(scoreList);
        //solve the problem with Json and raw type on record class
        msp.putArrayList(scoreList);
    }
}