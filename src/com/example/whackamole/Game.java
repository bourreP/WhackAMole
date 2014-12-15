package com.example.whackamole;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
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
	private Point size;
	DisplayMetrics metrics;
	private Random r;
	private boolean stateGame;

	public Game(Activity activity, Context context, Bitmap photo) {
		super();

		this.activity = activity;
		soundManager = new SoundManager(context);
		bitmapManager = new BitmapManager(context);
		moleManager = new CopyOnWriteArrayList<Mole>();
		displayManager = new DisplayManager(context, photo, moleManager);

		r = new Random();
		size = new Point();
		stateGame = true;

		activity.setContentView(displayManager);
		activity.getWindowManager().getDefaultDisplay().getSize(size);
		new Timer().scheduleAtFixedRate(this, 1000, 1000);

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

	@Override
	public void run() {
		Log.d("Debug", "Game task run");
		moleManager.add(new Mole(activity, bitmapManager, displayManager, r.nextInt(size.x - 50), r.nextInt(size.y - 50)));
		Log.d("Debug", "Game task run OK");
	}
}
