package com.example.hw1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button main_BTN_right, main_BTN_left;
    private GridLayout main_LAY_gridlayout;
    private LinearLayout main_LAY_linearLayout;
    private final int LIVES = 3;
    private final int INTERVAL_BETWEEN_NEW_POLICE = 3;
    private final int CAR_INITIAL_POSITION = 16;
    private int carPosition = CAR_INITIAL_POSITION;
    private int intervalCounter = 0;
    private int life = LIVES;
    private int score = 0;
    private ImageView[] car = new ImageView[24];
    private ImageView[] lives = new ImageView[LIVES];
    private Handler handler;
    private Runnable run;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main_LAY_linearLayout = findViewById(R.id.main_LAY_linearLayout);
        main_LAY_gridlayout = findViewById(R.id.main_LAY_gridview);
        main_BTN_left = findViewById(R.id.main_BTN_left);
        main_BTN_right = findViewById(R.id.main_BTN_right);
        main_BTN_left.setOnClickListener(changeCarPosition);
        main_BTN_right.setOnClickListener(changeCarPosition);

        init();
    }


    /**
     * Move the car left OR right
     */
    View.OnClickListener changeCarPosition = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.main_BTN_left:
                    if (carPosition > 15) {
                        //Checks if there is a police in the LEFT side of the car to determine collision
                        if (car[carPosition - 1].getDrawable() != null) {
                            if (isImageViewSameAsPolice(car[carPosition - 1])) {
                                collision(car[carPosition - 1]);
                            }
                        }
                        moveLeft();
                    }
                    break;
                case R.id.main_BTN_right:
                    if (carPosition < 17) {
                        //Checks if there is a police in the RIGHT side of the car to determine collision
                        if (car[carPosition + 1].getDrawable() != null) {
                            if (isImageViewSameAsPolice(car[carPosition + 1])) {
                                collision(car[carPosition + 1]);
                            }
                        }
                        moveRight();
                    }
                    break;
            }
        }
    };

    /**
     * Move the car 1 time to the left
     */
    public void moveLeft() {
        car[carPosition].setImageResource(0);
        carPosition--;
        car[carPosition].setImageResource(R.drawable.car);
    }

    /**
     * Move the car 1 time to the right
     */
    public void moveRight() {
        car[carPosition].setImageResource(0);
        carPosition++;
        car[carPosition].setImageResource(R.drawable.car);
    }

    /**
     * Init the game parameters
     */
    public void init() {
        for (int i = 0; i < car.length; i++) {
            car[i] = (ImageView) main_LAY_gridlayout.getChildAt(i);
            if (i == CAR_INITIAL_POSITION) {
                car[i].setImageResource(R.drawable.car);
            }
        }
        for (int i = 0; i < lives.length; i++) {
            lives[i] = (ImageView) main_LAY_linearLayout.getChildAt(i);
            lives[i].setImageResource(R.drawable.heart);
        }
        score = 0;
        intervalCounter = 0;
        life = LIVES;
        main_BTN_right.setAlpha(1);
        main_BTN_left.setAlpha(1);
        main_BTN_right.setClickable(true);
        main_BTN_left.setClickable(true);

//        startTheGame();
        loop();
    }

    /**
     * Start an Handler that runs the game
     */
    public void loop() {
        handler = new Handler();
        run = new Runnable() {
            @Override
            public void run() {
                loop();
                startTheGame();
            }
        };
        handler.postDelayed(run, 500);
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
            //If there is an non-empty ImageView(police), we Compare that ImageView with the ImageView in row ahead to determinate
            //collision
            if ((car[i - 3].getDrawable() != null) && isImageViewSameAsPolice(car[i - 3])) {
                if (car[i].getDrawable() == null) {
                    car[i].setImageResource(R.drawable.police);
                    car[i - 3].setImageResource(0);
                    //Collision
                } else {
                    collision(car[i - 3]);
                }
            }
        }
        //Draw new police on the screen
        if (intervalCounter == INTERVAL_BETWEEN_NEW_POLICE - 1) {
            lanchPolice();
        }
        intervalCounter++;
        score++;
    }

    /**
     * Called when the player has no life and move to the Game Over activity
     */
    public void gameOver() {
        Intent intent = new Intent(getApplicationContext(), GameOverActivity.class);
        intent.putExtra("score", score + "");
        startActivity(intent);
        handler.removeCallbacks(run);
        finish();
    }

    /**
     * @param police
     * @return Boolean value that says if a certain ImageView is a police. Comparison using bitmaps
     */
    public boolean isImageViewSameAsPolice(ImageView police) {
        //Create bitmap for the police image we want to move forward
        Bitmap policeBitmap = ((BitmapDrawable) police.getDrawable()).getBitmap();
        //Create bitmap for the police resource image
        Drawable policeResource = getResources().getDrawable(R.drawable.police);
        Bitmap policeResourceBitmap = ((BitmapDrawable) policeResource).getBitmap();

        //Compare them
        return policeBitmap.sameAs(policeResourceBitmap);
    }

    /**
     * @param police Remove the police from the screen, vibrates the device and notify with toast, decrease 1 life and checks if the game is over
     */
    public void collision(ImageView police) {
        MyFeedbacks.vibrate(getApplicationContext(), 250);
        Toast.makeText(this, "Be more careful next time!", Toast.LENGTH_SHORT).show();
        life--;
        if (life == 0) {
            gameOver();
        }
        //Removes the heart from the screen with animation
        MyAnimations.remove(lives[life]);
        police.setImageResource(0);
    }

    /**
     * Generate random number (0-2) that represent column and place new police on the top of that column
     */
    public void lanchPolice() {
        int newLocation = (int) (Math.random() * 2.9);
        car[newLocation].setImageResource(R.drawable.police);
        intervalCounter = -1;
    }
}