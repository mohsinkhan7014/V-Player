package com.example.v_player;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

public class videoPlayerActivity extends AppCompatActivity {

    VideoView videoView;
    int position=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        videoView=findViewById(R.id.videoView);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        position=getIntent().getIntExtra("position",-1);
        getSupportActionBar().hide();

        playVideo();


    }

    private void playVideo() {
        MediaController mediaController=new MediaController(videoPlayerActivity.this);
        mediaController.setAnchorView(videoView);
        mediaController.setPrevNextListeners(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.setVideoPath(String.valueOf(MainActivity.fileArrayList.get(position+=1)));
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.setVideoPath(String.valueOf(MainActivity.fileArrayList.get(position-=1)));

            }
        });
        videoView.setMediaController(mediaController);
        videoView.setVideoPath(String.valueOf(MainActivity.fileArrayList.get(position)));
        videoView.requestFocus();
        videoView.setOnPreparedListener(x->{
            videoView.start();

        });

        videoView.setOnCompletionListener(x->{
            videoView.setVideoPath(String.valueOf(MainActivity.fileArrayList.get(position+=1)));
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        videoView.stopPlayback();
    }
}