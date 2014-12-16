package com.example.whackamole;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.renderscript.Element;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by nils on 13/12/14.
 */
public class DisplayManager extends View {

    private static final float TOUCH_TOLERANCE = 50;
    private static final float EXPLOSION_GAP = 300;
    private static final int MAX_EXPLOSIONS = 5;
    private static final float CORRECTION_X = 500;
    private static final float CORRECTION_Y = 200;

    private BitmapDrawable background;
    private Bitmap  mBitmap;
    private Canvas  mCanvas;
    private Paint   mBitmapPaint;

    private float   mX, mY;
    private boolean         moved;
    private int explosionsLeft;

    private int width;
    private int height;

    private CopyOnWriteArrayList<Mole> moleManager;
    private CopyOnWriteArrayList<Explosion> explosionManager;
    private SoundManager soundManager;
    private BitmapManager bitmapManager;


    public DisplayManager(Context context, SoundManager soundManager, Bitmap background_photo, CopyOnWriteArrayList<Mole> moleManager, CopyOnWriteArrayList<Explosion> explosionManager, BitmapManager bitmapManager) {
        super(context);

        mBitmapPaint = new Paint(Paint.DITHER_FLAG);

        moved = false;


        this.moleManager = moleManager;
        this.soundManager = soundManager;
        this.explosionManager = explosionManager;
        this.bitmapManager = bitmapManager;

        this.background = new BitmapDrawable(getResources(), background_photo);

        setBackground(background);
    }

    @Override
    protected void onSizeChanged(int width, int height, int oldw, int oldh) {
        super.onSizeChanged(width, height, oldw, oldh);

        this.width = width;
        this.height = height;

        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mBitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888);
        mCanvas.setBitmap(mBitmap);

        if (!moleManager.isEmpty()) {
            Iterator<Mole> e = moleManager.iterator();
            while (e.hasNext()) {
                Mole mole = e.next();
                mCanvas.drawBitmap(mole.getBitmap(), mole.getPositionX(), mole.getPositionY(), mBitmapPaint);
            }
        }

        if (!explosionManager.isEmpty()) {
            Iterator<Explosion> e = explosionManager.iterator();
            while (e.hasNext()) {
                Log.d("Debug", "draw explosion");
                Explosion explosion = e.next();
                mCanvas.drawBitmap(explosion.getBitmap(), explosion.getPositionX(), explosion.getPositionY(), mBitmapPaint);
            }
        }

        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
    }

    private void touch_start(float x, float y) {
        Iterator<Mole> e = moleManager.iterator();
        while (e.hasNext()) {
            Mole mole = e.next();
           if (Math.sqrt(Math.pow(mole.getPositionX() - x, 2) + Math.pow(mole.getPositionY() - y, 2)) <= 200) {
               moleManager.remove(mole);
               soundManager.play_kick();
               break;
           }
        }

        moved = false;
        mX = x;
        mY = y;
    }
    private void touch_move(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (!moved) {
            if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
                explosionsLeft = MAX_EXPLOSIONS;
                soundManager.play_bomb();
                explosionManager.add(new Explosion(bitmapManager, x - CORRECTION_X, y - CORRECTION_Y));
                explosionsLeft--;
                moved = true;
                mX = x;
                mY = y;
            }
        }
        else if (dx >= EXPLOSION_GAP || dy >= EXPLOSION_GAP) {
            if (explosionsLeft > 0) {
                soundManager.play_bomb();
                explosionManager.add(new Explosion(bitmapManager, x - CORRECTION_X, y - CORRECTION_Y));
                mX = x;
                mY = y;
                explosionsLeft--;
            }
        }
    }
    private void touch_up(float x, float y) {
        // kill this so we don't double draw
        if(!moved)
        {

        }
        moved = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touch_start(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                touch_move(x, y);
                break;
            case MotionEvent.ACTION_UP:
                touch_up(x,y);
                break;
        }
        return true;
    }

}
