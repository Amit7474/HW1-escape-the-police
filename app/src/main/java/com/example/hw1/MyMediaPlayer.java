/*
 * *
 *  * Created by Amit kremer ID 302863253 on 12/12/19 1:53 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 12/12/19 1:53 PM
 *
 */

package com.example.hw1;

import android.content.Context;
import android.media.MediaPlayer;

public class MyMediaPlayer {

    private MediaPlayer mPlayer;
    private boolean replay = false;
    private Context context;
    private int[] sounds = {R.raw.vintageparty, R.raw.meme, R.raw.coin, R.raw.impact};

    public MyMediaPlayer(Context context, int song) {
        this.context = context;
        mPlayer = MediaPlayer.create(context, sounds[song]);
        if (song == 0) {
            replay = true;
        }
    }

    public void start() {
        mPlayer.start();
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (replay) {
                    start();
                } else {
                    mPlayer.release();
                }
            }
        });
    }

    public void pause() {
        if (mPlayer != null) {
            mPlayer.pause();
        }
    }

    public void releasePlayer() {
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;

        }
    }

    public void switchSounds(int song){
        if(mPlayer != null) {
            mPlayer.release();
            mPlayer = MediaPlayer.create(context, sounds[song]);
            mPlayer.start();
        }
    }
}
