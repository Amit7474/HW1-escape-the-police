/*
 * *
 *  * Created by Amit kremer ID 302863253 on 12/4/19 11:25 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 12/4/19 11:25 AM
 *
 */
package com.example.hw1;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private Button main_BTN_right, main_BTN_left;
    private GridLayout main_LAY_gridlayout;
    private TextView main_TXT_great, main_TXT_bonus;
    private LinearLayout main_LAY_linearLayout;
    private final int LIVES = 3;
    private final int CAR_INITIAL_POSITION = 16;
    private int intervalBetweenNewPolice;
    private int carPosition;
    private int level;
    private int life;
    private int score;
    private int intervalDuration;
    private MySharedPref msp;
    private int[] obstacles = {R.drawable.car, R.drawable.police, R.drawable.coin};
    private ImageView[] car = new ImageView[24];
    private ImageView[] lives = new ImageView[LIVES];
    private Handler handler = new Handler();
    private Runnable run;
    private MyRotationSensor mrs;
    private MyMediaPlayer[] mPlayers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        main_BTN_left.setOnClickListener(changeCarPosition);
        main_BTN_right.setOnClickListener(changeCarPosition);
        setMediaPlayers();
        init();
    }

    /**
     * Attach every view to id
     */
    private void findViews() {
        main_LAY_linearLayout = findViewById(R.id.main_LAY_linearLayout);
        main_LAY_gridlayout = findViewById(R.id.main_LAY_gridview);
        main_BTN_left = findViewById(R.id.main_BTN_left);
        main_BTN_right = findViewById(R.id.main_BTN_right);
        main_TXT_great = findViewById(R.id.main_TXT_great);
        main_TXT_bonus = findViewById(R.id.main_TXT_bonus);
    }

    /**
     * Move the car left OR right
     */
    View.OnClickListener changeCarPosition = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.main_BTN_left:
                    cbInterface.moveLeft();
                    break;
                case R.id.main_BTN_right:
                    cbInterface.moveRight();
                    break;
            }
        }
    };

    /**
     * Set the media players
     */
    public void setMediaPlayers() {
        mPlayers = new MyMediaPlayer[]{new MyMediaPlayer(this, 0), new MyMediaPlayer(this, 1)};
        mPlayers[0].start();
    }


    /**
     * Callback interface for moving the players car with buttns OR Rotation Sensor
     */
    CallBackInterface cbInterface = new CallBackInterface() {
        @Override
        public void moveRight() {
            //TODO: create coin scenario and coin check
            if (carPosition < CAR_INITIAL_POSITION + 1) {
                //Checks if there is a police in the RIGHT side of the car to determine collision
                if (car[carPosition + 1].getDrawable() != null) {
                    if (typeOfObjectAhead(car[carPosition + 1], "police")) {
                        collision(car[carPosition + 1]);
                    } else if (typeOfObjectAhead(car[carPosition + 1], "coin")) {
                        bonus(car[carPosition + 1]);
                    }
                }
                moveCarRight();
            }
        }

        @Override
        public void moveLeft() {
            if (carPosition > CAR_INITIAL_POSITION - 1) {
                //Checks if there is a police in the LEFT side of the car to determine collision
                if (car[carPosition - 1].getDrawable() != null) {
                    if (typeOfObjectAhead(car[carPosition - 1], "police")) {
                        collision(car[carPosition - 1]);
                    } else if (typeOfObjectAhead(car[carPosition - 1], "coin")) {
                        bonus(car[carPosition - 1]);
                    }
                }
                moveCarLeft();
            }
        }
    };

    /**
     * Move the car 1 time to the left
     */
    public void moveCarLeft() {
        car[carPosition].setImageResource(0);
        carPosition--;
        car[carPosition].setImageResource(R.drawable.car);
    }

    /**
     * Move the car 1 time to the right
     */
    public void moveCarRight() {
        car[carPosition].setImageResource(0);
        carPosition++;
        car[carPosition].setImageResource(R.drawable.car);
    }

    /**
     * initialize the game parameters
     */
    public void init() {
        //initializes the main Grid layout
        for (int i = 0; i < car.length; i++) {
            car[i] = (ImageView) main_LAY_gridlayout.getChildAt(i);
            if (i == CAR_INITIAL_POSITION) {
                car[i].setImageResource(R.drawable.car);
            }
        }
        //initializes the hearts
        for (int i = 0; i < lives.length; i++) {
            lives[i] = (ImageView) main_LAY_linearLayout.getChildAt(i);
            lives[i].setImageResource(R.drawable.heart);
        }
        msp = new MySharedPref(this);
        score = 1;
        level = 1;
        intervalBetweenNewPolice = 4;
        life = LIVES;
        carPosition = CAR_INITIAL_POSITION;
        intervalDuration = msp.getInt("speed", 500);
        setControlOptions(msp.getString("control", "B"));


        //start the game
        loop();
    }

    /**
     * Set the player's car to be controlled by the buttons OR the Rotation sensor
     *
     * @param options
     */
    public void setControlOptions(String options) {
        if (options.equals("A")) {
            //Rotation sensor settings
            ButtonsInit(false);
            mrs = new MyRotationSensor(MainActivity.this, cbInterface);
            mrs.setSensor();
        } else {
            //Buttons setting
            ButtonsInit(true);
        }


    }

    /**
     * Set the 'Left' and 'Right' buttons according to the player's control settings
     *
     * @param state
     */
    public void ButtonsInit(boolean state) {
        main_BTN_right.setClickable(state);
        main_BTN_left.setClickable(state);
        if (state) {
            main_BTN_right.setAlpha(1);
            main_BTN_left.setAlpha(1);

        } else {
            main_BTN_right.setAlpha(0);
            main_BTN_left.setAlpha(0);
        }

    }

    /**
     * Start an Handler that runs the game
     */
    public void loop() {
        run = new Runnable() {
            @Override
            public void run() {
                loop();
                startTheGame();
            }
        };

        handler.postDelayed(run, intervalDuration);
    }


    private void advanceImagview(ImageView _car, int resID) {
        int index = Arrays.asList(car).indexOf(_car);
        car[index + 3].setImageResource(resID);
        car[index].setImageResource(0);
    }

    /**
     * This is all the logic of the game
     */
    public void startTheGame() {
        for (int i = car.length - 1; i > 2; i--) {
            //Remove all the polices that already pass the car
            if (i > 20) {
                car[i].setImageResource(0);
            }
            //If there is an non-empty ImageView(police/coin), we Compare that ImageView with the ImageView in row ahead to determinate collision OR bonus
            if ((car[i - 3].getDrawable() != null)) {
                if (car[i].getDrawable() == null) {
                    //move the Police OR the coin 1 row forward
                    if (typeOfObjectAhead(car[i - 3], "police"))
                        advanceImagview(car[i - 3], obstacles[1]);

                    else if (typeOfObjectAhead(car[i - 3], "coin")) {
                        advanceImagview(car[i - 3], obstacles[2]);
                    }
                } else if (car[i].getDrawable() != null) {
                    //Collision OR bonus
                    if (typeOfObjectAhead(car[i - 3], "police")) {
                        collision(car[i - 3]);
                    } else if (typeOfObjectAhead(car[i - 3], "coin")) {
                        bonus(car[i - 3]);
                    }
                }
            }
        }
        //Every X seconds (depend on the game speed) 'Great' feedback will appear with animation
        if (score % 30 == 0) {
            showFeedback(main_TXT_great);
            if (intervalBetweenNewPolice > 3)
                reduceIntervalBetweenNewPolice();
            if (level < 2)
                level++;
        }
        if (score % 10 == 0)
            launchObstacle(2);
        //Draw new police on the screen
        if (score % intervalBetweenNewPolice == 0) {
            for (int i = 0; i <= level - 1; i++) {
                launchObstacle(1);
            }
        }

        score++;
    }

    /**
     * Called when the player has no life and move to the Game Over activity
     */
    public void gameOver() {
        for (MyMediaPlayer mPlayer : mPlayers) {
            mPlayer.releasePlayer();
        }
        Intent intent = new Intent(getApplicationContext(), GameOverActivity.class);
        intent.putExtra("score", score);
        startActivity(intent);
        handler.removeCallbacks(run);
        finish();
    }

    /**
     * @param obstacle
     * @param nameOfObstacle
     * @return boolean if the obstacle is same as coin OR police
     */
    public boolean typeOfObjectAhead(ImageView obstacle, String nameOfObstacle) {
        //Create bitmap for the object image we want to move forward
        Bitmap objectBitmap = ((BitmapDrawable) obstacle.getDrawable()).getBitmap();

        if (nameOfObstacle.equalsIgnoreCase("police")) {
            //Create bitmap for the police resource image
            Drawable policeResource = getResources().getDrawable(R.drawable.police);
            Bitmap policeResourceBitmap = ((BitmapDrawable) policeResource).getBitmap();

            return objectBitmap.sameAs(policeResourceBitmap);

        } else if (nameOfObstacle.equalsIgnoreCase("coin")) {
            //Create bitmap for the coin resource image
            Drawable coinResource = getResources().getDrawable(R.drawable.coin);
            Bitmap coinResourceBitmap = ((BitmapDrawable) coinResource).getBitmap();

            return objectBitmap.sameAs(coinResourceBitmap);
        } else return false;
    }

    /**
     * @param police Remove the police from the screen, vibrates the device and notify with toast, decrease 1 life and checks if the game is over
     */
    public void collision(ImageView police) {
        mPlayers[1].switchSounds(3);
        MyFeedbacks.vibrate(getApplicationContext(), 250);
        Toast.makeText(this, "Be more careful next time!", Toast.LENGTH_SHORT).show();
        life--;
        if (life == 0) {
            gameOver();
        }
        police.setImageResource(0);

        //Removes the heart from the screen with animation
        Animation removeHeart = AnimationUtils.loadAnimation(this, R.anim.remove_from_screen);
        removeHeart.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                lives[life].setImageResource(0);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        lives[life].startAnimation(removeHeart);
    }

    /**
     * Show "+10" text, adds 10 pts to the player's score and play coin sound
     */
    private void bonus(ImageView coin) {
        mPlayers[1].switchSounds(2);
        score += 10;
        showFeedback(main_TXT_bonus);
        coin.setImageResource(0);
    }

    /**
     * Generate random number (0-2) that represent column and place new police OR coin on the top of that column
     */
    public void launchObstacle(int obstacle) {
        int newLocation = (int) (Math.random() * 2.9);
        car[newLocation].setImageResource(obstacles[obstacle]);
    }

    /**
     * Increase the speed of the police launcher
     */
    public void showFeedback(final TextView feedback) {
        //Show text with scale-up animation
        Animation scaleFasterText = AnimationUtils.loadAnimation(this, R.anim.show_text_on_screen);
        scaleFasterText.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                feedback.animate().alpha(1);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                feedback.animate().alpha(0);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        feedback.startAnimation(scaleFasterText);
    }

    /**
     * make the polices to appear more frequent
     */
    public void reduceIntervalBetweenNewPolice() {
        intervalBetweenNewPolice -= 1;
    }

    /**
     * When the player leaves the activity it stops
     */
    @Override
    protected void onStop() {
        super.onStop();
        if (mrs != null)
            mrs.stopSensor();
        mPlayers[0].pause();
        handler.removeCallbacks(run);
    }

    /**
     * When the player returns to the activity it starts from the place he left it
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        if (mrs != null)
            mrs.setSensor();
        mPlayers[0].start();
        handler.postDelayed(run, intervalDuration);
    }
}

