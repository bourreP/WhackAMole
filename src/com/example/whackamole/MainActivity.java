package com.example.whackamole;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;


public class MainActivity extends Activity {
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

	private Uri fileUri;
	private View mView;
    private Game game;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        mView = new View(this);
        setContentView(mView);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File imageFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = new File(imageFolder, "CapturedImage.jpg" );
        fileUri = Uri.fromFile(image);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        // start the image capture Intent
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

	}

    @Override
    protected void onPause() {
        super.onPause();

        if (game != null)
            game.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (game != null)
            game.resume();
    }

    @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				Options options = new BitmapFactory.Options();
				options.inScaled = false;
				options.inDither = false;
				options.inPreferredConfig = Bitmap.Config.ARGB_8888;
				Bitmap photo = BitmapFactory.decodeFile(fileUri.getPath(), options);

                game = new Game(this, getApplicationContext(), photo);

                game.start();

            } else if (resultCode == RESULT_CANCELED) {
				finish();
			} else {
				// Image capture failed, advise user
			}

		}
	}
}
