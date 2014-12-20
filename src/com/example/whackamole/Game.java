package com.example.whackamole;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by nils on 13/12/14.
 */
public class Game extends TimerTask {


    private static final int MARGE = 150;
    private Activity activity;
	private SoundManager soundManager;
	private BitmapManager bitmapManager;
	private DisplayManager displayManager;
	private CopyOnWriteArrayList<Mole> moleManager;
    private CopyOnWriteArrayList<Explosion> explosionManager;
	private Point resolution;
	private Random random;
    private int counter;
    private int difficulty;
    private int bombLoader;
    private Timer timer;
    private Runnable taskDispayManager;

	public Game(Activity activity, Context context, Bitmap photo) {
		super();

        random = new Random();
        resolution = new Point();
        difficulty = 5;
        counter = 0;
        bombLoader = 0;

		this.activity = activity;
		soundManager = new SoundManager(context);
		bitmapManager = new BitmapManager(context);
		moleManager = new CopyOnWriteArrayList<Mole>();
        explosionManager = new CopyOnWriteArrayList<Explosion>();
		displayManager = new DisplayManager(activity, context, resolution, soundManager, photo, moleManager, explosionManager, bitmapManager);

		activity.setContentView(displayManager);
        activity.getWindowManager().getDefaultDisplay().getSize(resolution);

		timer = new Timer();
        timer.scheduleAtFixedRate(this, 0, 100);
        taskDispayManager = new Runnable() {
            @Override
            public void run() {
                displayManager.invalidate();
            }
        };

	}

    public void gameOver() {
        soundManager.play_gameOver();
        timer.cancel();
    }

	public void start() {
		soundManager.play_theme();
	}

	public void pause() {
		soundManager.pause_theme();
	}

	public void resume() {
		soundManager.resume_theme();
	}

    public void stop() {
        timer.cancel();
    }

	@Override
	public void run() {
        int moleOut = 0;

        for (Explosion explosion : explosionManager) {
            if (explosion.hasNextState())
                explosion.nextState();
            else
                explosionManager.remove(explosion);
        }

        if (counter == 10) {
            for (Mole mole : moleManager) {
                if (mole.hasNextState())
                    mole.nextState();
                else
                    moleOut++;
            }
            for (int i = 0; i < difficulty/5; i++) {
                moleManager.add(new Mole(bitmapManager, random.nextInt(resolution.x - 2*MARGE) + MARGE, random.nextInt(resolution.y - 2*MARGE) + MARGE));
            }

            if (bombLoader == 5) {
                displayManager.loadBomb();
                bombLoader = 0;
            }

            displayManager.setMoleOut(moleOut);
            displayManager.setMoleMax(difficulty/5);

            if (moleOut >= difficulty/5) {
                gameOver();
            }

            counter = 0;
            difficulty++;
            bombLoader++;
        }

        counter ++;


        
        activity.runOnUiThread(taskDispayManager);
     }
}
