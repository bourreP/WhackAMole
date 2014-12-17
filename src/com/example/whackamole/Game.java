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

	private Activity activity;
	private SoundManager soundManager;
	private BitmapManager bitmapManager;
	private DisplayManager displayManager;
	private CopyOnWriteArrayList<Mole> moleManager;
    private CopyOnWriteArrayList<Explosion> explosionManager;
	private Point size;
	private Random random;
    private int counter;
    private int difficulty;
    private Timer timer;
    private Runnable taskDispayManager;

	public Game(Activity activity, Context context, Bitmap photo) {
		super();

		this.activity = activity;
		soundManager = new SoundManager(context);
		bitmapManager = new BitmapManager(context);
		moleManager = new CopyOnWriteArrayList<Mole>();
        explosionManager = new CopyOnWriteArrayList<Explosion>();
		displayManager = new DisplayManager(context, soundManager, photo, moleManager, explosionManager, bitmapManager);

		random = new Random();
		size = new Point();
        difficulty = 0;
        counter = 0;

		activity.setContentView(displayManager);
		activity.getWindowManager().getDefaultDisplay().getSize(size);
		timer = new Timer();
        timer.scheduleAtFixedRate(this, 0, 100);
        taskDispayManager = new Runnable() {
            @Override
            public void run() {
                displayManager.invalidate();
            }
        };

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
                // Else game over
            }
            for (int i = 0; i <= difficulty/5; i++) {
                moleManager.add(new Mole(bitmapManager, random.nextInt(size.x - 50), random.nextInt(size.y - 50)));
            }
            counter = 0;
            difficulty++;
        }

        counter ++;
        
        activity.runOnUiThread(taskDispayManager);
     }
}
