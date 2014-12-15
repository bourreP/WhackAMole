package com.example.whackamole;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by nils on 14/12/14.
 */
public class Mole extends TimerTask{

    private Activity activity;
    private BitmapManager bitmapManager;
    private DisplayManager displayManager;
    private int positionX;
    private int positionY;
    private int state;

    public Mole(Activity activity, BitmapManager bitmapManager, DisplayManager displayManager, int positionX, int positionY) {
        super();

        this.activity = activity;
        this.bitmapManager = bitmapManager;
        this.displayManager = displayManager;
        this.positionX = positionX;
        this.positionY = positionY;
        this.state = 0;

        new Timer().scheduleAtFixedRate(this, 1000, 1000);
    }

    @Override
    public void run() {
        Log.d("Debug", "Mole task run");
        if (state < bitmapManager.getMole().length -1) {
            Log.d("Debug", "Mole state ++");
            state++;
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    displayManager.invalidate();
                }
            });

        }
        Log.d("Debug", "Mole task run OK");
        /* Else game over */
    }

    public Bitmap getBitmap() {
        return bitmapManager.getMole()[state];
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }
}
