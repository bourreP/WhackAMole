package com.example.whackamole;

import android.graphics.Bitmap;

/**
 * Created by nils on 16/12/14.
 */
public class Explosion {

    private BitmapManager bitmapManager;
    private float positionX;
    private float positionY;
    private int state;

    public Explosion(BitmapManager bitmapManager, float positionX, float positionY) {
        this.bitmapManager = bitmapManager;
        this.state = 0;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public Bitmap getBitmap() {
        return bitmapManager.getExplosion()[state];
    }

    public float getPositionX() {
        return positionX;
    }

    public float getPositionY() {
        return positionY;
    }


    public Boolean hasNextState() {
        if (state < bitmapManager.getExplosion().length -1)
            return true;
        else
            return false;
    }

    public void nextState() {
        if (state < bitmapManager.getExplosion().length -1) {
            state++;
        }
    }
}
