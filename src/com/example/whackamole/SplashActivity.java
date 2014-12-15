package com.example.whackamole;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoView.setOnPreparedListener(new
            MediaPlayer.OnPreparedListener()  {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    Log.d("Debug", "Video ready");
                    videoView.start();
                }
            });
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoView.pause();
    }



    public void onVideoTouch(View view) {
        if (videoView != null && videoView.isPlaying() == false) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}