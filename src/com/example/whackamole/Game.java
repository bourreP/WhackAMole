package com.example.whackamole;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import java.util.TimerTask;
import java.util.Vector;

/**
 * Created by nils on 13/12/14.
 */
public class Game extends TimerTask {

    private Activity activity;
    private SoundManager soundManager;
    private BitmapManager bitmapManager;
    private DisplayManager displayManager;
    private Vector<Mole> moleManager;

    public Game(Activity activity, Context context, Bitmap photo) {
        super();

        this.activity = activity;
        soundManager = new SoundManager(context);
        bitmapManager = new BitmapManager(context);
        moleManager = new Vector<Mole>();
        displayManager = new DisplayManager(context, photo, moleManager);

        new DisplayManager(context, photo, moleManager);
        activity.setContentView(displayManager);
    }

    public void start() {
        soundManager.play_theme();

        moleManager.add(new Mole(activity, bitmapManager, displayManager, 200, 200));
    }

    public void pause() {
        soundManager.pause_theme();
    }

    public void resume() {
        soundManager.resume_theme();
    }

    @Override
    public void run() {

    }
}
