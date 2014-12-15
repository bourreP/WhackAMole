package com.example.whackamole;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

/**
 * Created by nils on 13/12/14.
 */
public class SoundManager {

    private SoundPool soundPool;
    private MediaPlayer mainThemePlayer;
    private int kickEffect;

    @SuppressWarnings("deprecation")
	public SoundManager(Context context) {
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        mainThemePlayer = MediaPlayer.create(context, R.raw.overworld);

        kickEffect = soundPool.load(context, R.raw.kick, 1);


    }

    public void play_kick() {
        soundPool.play(kickEffect, 1, 1, 1, 0, 1);
    }

    public void play_theme() {
        mainThemePlayer.start();
    }

    public void pause_theme() {
        mainThemePlayer.pause();
    }

    public void finalize()
    {
        soundPool.release();
        mainThemePlayer.release();
    }


    public void resume_theme() {
        mainThemePlayer.start();
    }
}
