/*
 * *
 *  * Created by Amit kremer ID 302863253 on 12/4/19 11:25 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 12/3/19 1:24 PM
 *
 */

package com.example.hw1;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

public class MyFeedbacks {

    /**
     * Creates vibrations using the device vibrator
     * @param context
     * @param duration(How long the vibration will be)
     */
    public static void vibrate(Context context, int duration){
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
// Vibrate for X milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(duration);
        }

    }
}
