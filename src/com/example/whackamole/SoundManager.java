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
    private MediaPlayer gameOverPlayer;
    private int kickEffect;
    private int bombEffect;

    @SuppressWarnings("deprecation")
	public SoundManager(Context context) {
        soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);
        mainThemePlayer = MediaPlayer.create(context, R.raw.overworld);
        gameOverPlayer = MediaPlayer.create(context, R.raw.gameover);

        kickEffect = soundPool.load(context, R.raw.kick, 1);
        bombEffect = soundPool.load(context, R.raw.bomb, 1);
    }

    public void play_kick() {
        soundPool.play(kickEffect, 1, 1, 1, 0, 1);
    }

    public void play_bomb()  {
        soundPool.play(bombEffect, 1, 1, 1, 0, 1);
    }

    public void play_theme() {
        mainThemePlayer.start();
        mainThemePlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mainThemePlayer.start();
            }
        });
    }

    public void play_gameOver() {
        mainThemePlayer.pause();
        gameOverPlayer.start();
    }

    public boolean isGameOverPlaying() {
        return gameOverPlayer.isPlaying();
    }

    public void pause_theme() {
        mainThemePlayer.pause();
    }

    public void finalize()
    {
        soundPool.release();
        mainThemePlayer.release();
        gameOverPlayer.release();
    }


    public void resume_theme() {
        mainThemePlayer.start();
    }
}
