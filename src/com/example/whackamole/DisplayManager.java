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
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.Vector;

/**
 * Created by nils on 13/12/14.
 */
public class DisplayManager extends View {

    private static final float TOUCH_TOLERANCE = 4;

    private BitmapDrawable background;
    private Bitmap  mBitmap;
    private Canvas  mCanvas;
    private Path    mPath;
    private Paint   mBitmapPaint;
    private Paint   mPaint;

    private float   mX, mY;
    private boolean         moved;

    private int width;
    private int height;

    private Vector<Mole> moleManager;


    public DisplayManager(Context context, Bitmap background_photo, Vector<Mole> moleManager) {
        super(context);

        mPath = new Path();
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);

        moved = false;

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(0xFFFF0000);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        // mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(10);

        this.moleManager = moleManager;

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
        Log.d("Debug", "onDraw");
        mBitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888);
        mCanvas.setBitmap(mBitmap);

        for (Mole mole : moleManager) {
            mCanvas.drawBitmap(mole.getBitmap(),mole.getPositionX(),mole.getPositionY(),mBitmapPaint);
        }

        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);

        Log.d("Debug", "onDraw OK");
    }

    private void touch_start(float x, float y) {
        mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;

    }
    private void touch_move(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            moved = true;
        }
    }
    private void touch_up(float x, float y) {
        // kill this so we don't double draw
        if(!moved)
        {

        }
        mPath.reset();
        moved = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        Log.d("Debug", "X=" + x + " Y=" + y);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touch_start(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touch_move(x, y);
                //invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touch_up(x,y);
                //invalidate();
                break;
        }
        return true;
    }

}
