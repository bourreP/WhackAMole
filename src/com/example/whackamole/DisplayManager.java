package com.example.whackamole;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.View;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by nils on 13/12/14.
 */
public class DisplayManager extends View {

	private static final float TOUCH_TOLERANCE = 75;
	private static final float EXPLOSION_GAP = 200;
	private static final double EXPLOSION_RADIUS = 500;
    private static final int BOMB_OFFSET_RIGHT = 400;
    private static final int MOLE_OFFSET_RIGHT = 100;


    private BitmapDrawable background;
	private Bitmap  mBitmap;
	private Canvas  mCanvas;
	private Paint   mBitmapPaint;
	private Paint   mTextPaint;

    private Point resolution;
	private float   mX, mY;
	private boolean         moved;
	private int explosionsLeft;
	private int score;
    private int moleOut;
    private int moleMax;


	private int width;
	private int height;

    private Activity activity;
	private CopyOnWriteArrayList<Mole> moleManager;
	private CopyOnWriteArrayList<Explosion> explosionManager;
	private SoundManager soundManager;
	private BitmapManager bitmapManager;


	public DisplayManager(Activity activity, Context context, Point resolution, SoundManager soundManager, Bitmap background_photo, CopyOnWriteArrayList<Mole> moleManager, CopyOnWriteArrayList<Explosion> explosionManager, BitmapManager bitmapManager) {
		super(context);

		mBitmapPaint = new Paint(Paint.DITHER_FLAG);
		mTextPaint = new Paint();
		mTextPaint.setColor(Color.WHITE);
		mTextPaint.setLinearText(true);
		mTextPaint.setTextSize(50);
		mTextPaint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        mTextPaint.setTextAlign(Paint.Align.RIGHT);

		moved = false;

        this.activity = activity;
		this.moleManager = moleManager;
		this.soundManager = soundManager;
		this.explosionManager = explosionManager;
		this.bitmapManager = bitmapManager;
        this.explosionsLeft = 5;
		this.score = 0;
        this.resolution = resolution;
        this.moleOut = 0;
        this.moleMax = 1;

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
                Bitmap bitmap = mole.getBitmap();
                mCanvas.drawBitmap(bitmap, mole.getPositionX() - bitmap.getWidth() / 2, mole.getPositionY() - bitmap.getHeight() / 2, mBitmapPaint);
            }
        }

        if (!explosionManager.isEmpty()) {
            Iterator<Explosion> e = explosionManager.iterator();
            while (e.hasNext()) {
                Explosion explosion = e.next();
                Bitmap bitmap = explosion.getBitmap();
                mCanvas.drawBitmap(bitmap, explosion.getPositionX() - bitmap.getWidth() / 2, explosion.getPositionY() - bitmap.getHeight() / 2, mBitmapPaint);
            }
        }

        mTextPaint.setTextAlign(Paint.Align.LEFT);
        mTextPaint.setTextSize(50);
        mCanvas.drawText("Score : " + Integer.toString(score), 100, 100, mTextPaint);


        mTextPaint.setTextAlign(Paint.Align.RIGHT);
        mTextPaint.setTextSize(50);

        Bitmap bombIcon = bitmapManager.getBombIcon();
        mCanvas.drawText(this.explosionsLeft + "x", resolution.x - BOMB_OFFSET_RIGHT - 100, 100, mTextPaint);
        mCanvas.drawBitmap(bombIcon, resolution.x - BOMB_OFFSET_RIGHT - bombIcon.getWidth() / 2, 75 - bombIcon.getHeight() / 2, mBitmapPaint);

        Bitmap moleIcon = bitmapManager.getMoleIcon();
        mCanvas.drawText(this.moleOut + "/" + this.moleMax, resolution.x - MOLE_OFFSET_RIGHT - 100, 100, mTextPaint);
        mCanvas.drawBitmap(moleIcon, resolution.x - MOLE_OFFSET_RIGHT - moleIcon.getWidth() / 2, 75 - moleIcon.getHeight() / 2, mBitmapPaint);

        if (moleOut >= moleMax) // Game Over
        {
            mTextPaint.setTextAlign(Paint.Align.CENTER);
            mTextPaint.setTextSize(200);
            mCanvas.drawText("Game Over !", resolution.x/2, resolution.y/2, mTextPaint);
        }


		canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
	}

	private void touch_start(float x, float y) {
		moved = false;
		mX = x;
		mY = y;
	}
	private void touch_move(float x, float y) {
		float dx = Math.abs(x - mX);
		float dy = Math.abs(y - mY);
		if (!moved) {
			if ((dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) && explosionsLeft > 0) {
                soundManager.play_bomb();
                explosionManager.add(new Explosion(bitmapManager, x, y));
                explosionsLeft--;
                Iterator<Mole> e = moleManager.iterator();
                while (e.hasNext()) {
                    Mole mole = e.next();
                    if (Math.sqrt(Math.pow(mole.getPositionX() - x, 2) + Math.pow(mole.getPositionY() - y, 2)) <= EXPLOSION_RADIUS) {
                        moleManager.remove(mole);
                        score++;
                    }
                }
                moved = true;
                mX = x;
                mY = y;
			}
		}
		else if ((dx >= EXPLOSION_GAP || dy >= EXPLOSION_GAP) && explosionsLeft > 0) {
			if (explosionsLeft > 0) {
				soundManager.play_bomb();
				explosionManager.add(new Explosion(bitmapManager, x, y));
				Iterator<Mole> e = moleManager.iterator();
				while (e.hasNext()) {
					Mole mole = e.next();
					if (Math.sqrt(Math.pow(mole.getPositionX() - x, 2) + Math.pow(mole.getPositionY() - y, 2)) <= EXPLOSION_RADIUS) {
						moleManager.remove(mole);
						score++;
					}
				}
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
            Iterator<Mole> e = moleManager.iterator();
            while (e.hasNext()) {
                Mole mole = e.next();
                if (Math.sqrt(Math.pow(mole.getPositionX() - x, 2) + Math.pow(mole.getPositionY() - y, 2)) <= 100) {
                    moleManager.remove(mole);
                    score++;
                }
            }
            soundManager.play_kick();
		}
		moved = false;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
        if (moleOut < moleMax) {
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
                    touch_up(x, y);
                    break;
            }
        }
        else if (!soundManager.isGameOverPlaying()){
            activity.finish();
        }
		return true;
	}

    public void loadBomb() {
        explosionsLeft++;
    }

    public void setMoleOut(int moleOut) {
        this.moleOut = moleOut;
    }

    public void setMoleMax(int moleMax) {
        this.moleMax = moleMax;
    }
}
