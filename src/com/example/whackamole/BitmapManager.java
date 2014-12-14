package com.example.whackamole;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by nils on 14/12/14.
 */
public class BitmapManager {

    private Bitmap[] mole;

    public BitmapManager(Context context) {
        mole = new Bitmap[7];

        mole[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.mole_1);
        mole[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.mole_2);
        mole[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.mole_3);
        mole[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.mole_4);
        mole[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.mole_5);
        mole[5] = BitmapFactory.decodeResource(context.getResources(), R.drawable.mole_6);
        mole[6] = BitmapFactory.decodeResource(context.getResources(), R.drawable.mole_7);
    }

    public Bitmap[] getMole() {
        return mole;
    }
}
