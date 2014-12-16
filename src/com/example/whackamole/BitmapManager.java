package com.example.whackamole;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by nils on 14/12/14.
 */
public class BitmapManager {

    private Bitmap[] mole;
    private Bitmap[] explosion;

    public BitmapManager(Context context) {
        mole = new Bitmap[7];
        explosion = new Bitmap[10];

        mole[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.mole_1);
        mole[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.mole_2);
        mole[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.mole_3);
        mole[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.mole_4);
        mole[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.mole_5);
        mole[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.mole_6);
        mole[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable.mole_7);

        explosion[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion_1);
        explosion[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion_2);
        explosion[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion_3);
        explosion[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion_4);
        explosion[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion_5);
        explosion[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion_6);
        explosion[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion_7);
        explosion[7] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion_8);
        explosion[8] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion_9);
        explosion[9] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion_10);
    }

    public Bitmap[] getMole() {
        return mole;
    }

    public Bitmap[] getExplosion() {
        return explosion;
    }
}
