package com.example.hw1;

import android.view.View;
import android.view.animation.BounceInterpolator;

public class MyAnimations {

    /**
     * Get a view and removes it from the screen
     * @param view
     */
    public static void remove(View view){
        view.animate()
                .scaleX(0)
                .scaleY(0)
                .setDuration(1000)
                .setInterpolator(new BounceInterpolator())
                .start();
    }
}
