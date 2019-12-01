package com.example.hw1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    private Button gameOver_BTN_playAgain;
    private TextView gameOver_TXT_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        gameOver_BTN_playAgain = findViewById(R.id.gameOver_BTN_playAgain);
        gameOver_TXT_score = findViewById(R.id.gameOver_TXT_score);

        //Get the player's score from the game activity by the intent
        Intent intent = getIntent();
        gameOver_TXT_score.setText("Your score is " + intent.getExtras().getString("score") + "pts");
        gameOver_BTN_playAgain.setOnClickListener(playAgain);
    }

    /**
     * Play again listener. Close this activity and start the game activity
     */
    View.OnClickListener playAgain = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    };
}
