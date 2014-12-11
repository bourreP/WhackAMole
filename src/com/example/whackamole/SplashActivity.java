package com.example.whackamole;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.VideoView;

/**
 * Created by nils on 11/12/14.
 */
public class SplashActivity extends Activity {

    private VideoView videoView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);

        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.splash);

        videoView = (VideoView) findViewById(R.id.videoView);
        videoView.setVideoURI(uri);
        videoView.start();
    }

    public void onVideoTouch(View view) {
        if (videoView != null && videoView.isPlaying() == false) {
            Intent intent = new Intent(this, CameraActivity.class);
            startActivity(intent);
        }
    }
}