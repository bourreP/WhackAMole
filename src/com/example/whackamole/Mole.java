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
public class Mole{

    private BitmapManager bitmapManager;
    private int positionX;
    private int positionY;
    private int state;

    public Mole(BitmapManager bitmapManager, int positionX, int positionY) {
        super();

        this.bitmapManager = bitmapManager;
        this.positionX = positionX;
        this.positionY = positionY;
        this.state = 0;

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


    public boolean hasNextState() {
        if (state < bitmapManager.getMole().length -1)
            return true;
        else
            return false;
    }

    public void nextState() {
        if (state < bitmapManager.getMole().length -1) {
            state++;
        }
    }
}
