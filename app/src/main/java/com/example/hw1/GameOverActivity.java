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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Iterator;
import java.util.TreeSet;

public class GameOverActivity extends AppCompatActivity {

    private Button gameOver_BTN_playAgain, gameOver_BTN_save, gameOver_BTN_scoreList;
    private TextView gameOver_TXT_score;
    private MyMediaPlayer player;
    private Location playerLocation = null;
    private String playerName;
    private int playerScore;
    private TreeSet<Record> scoreSet = new TreeSet<Record>();
//    private ArrayList<Record> scoreSet = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);


        gameOver_BTN_playAgain = findViewById(R.id.gameOver_BTN_playAgain);
        gameOver_TXT_score = findViewById(R.id.gameOver_TXT_score);
        gameOver_BTN_scoreList = findViewById(R.id.gameOver_BTN_scoreList);
        gameOver_BTN_save = findViewById(R.id.gameOver_BTN_save);

        //Play the 'Game over' sound
        player = new MyMediaPlayer(this, 1);
        player.start();

        //Get the player's score from the game activity by the intent
        Intent intent = getIntent();
        playerScore = getScoreFromIntemt(intent);
        gameOver_TXT_score.setText("Your score is " + playerScore + "pts");


        gameOver_BTN_playAgain.setOnClickListener(playAgain);
        gameOver_BTN_save.setOnClickListener(saveScore);
        gameOver_BTN_scoreList.setOnClickListener(printScore);

    }


    View.OnClickListener printScore = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Iterator<Record> iterator = scoreSet.descendingIterator();
            while (iterator.hasNext()) {
                Log.i("score", iterator.next().toString() + "");
            }
            Log.i("dsds", "" + scoreSet.size());
//            for (int i = 0; i < scoreSet.size(); i++) {
//                Log.i("kcdsa", scoreSet.get(i).toString());
//            }
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
                scoreSet.add(new Record(playerName, playerScore, playerLocation));
            }
        });
        builder.show();
    }
}
