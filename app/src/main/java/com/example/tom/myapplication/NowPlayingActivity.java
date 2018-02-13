package com.example.tom.myapplication;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.tom.myapplication.Phish.in.Objects.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NowPlayingActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private Button playTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream_audio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        final String url = bundle.getString("URL");

        Intent i = new Intent(getApplicationContext(), AudioStreamService.class);  // getApplicationContext(), NowPlayingActivity.class);
        //Intent i = new Intent(AudioStreamService.ACTION_PLAY);
        i.putExtra("URL", url);
        i.setAction(AudioStreamService.ACTION_PLAY);
        //i.setPackage("com.example.tom.myapplication.AudioStreamService");
        getApplicationContext().startService(i);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //String url = "http://phishtracks.com/shows/2017-07-22/the-squirming-coil"; // your URL here
        //  Following URL works!
        //String url = "https://www.ssaurel.com/tmp/mymusic.mp3";
        /*
        ***MUSIC NO LONGER STREAMED FROM THIS ACTIVITY***
        mediaPlayer = new MediaPlayer();
        //String url = getIntent().getSerializableExtra("URL");
        //final String url = "http://phish.in/audio/000/017/963/17963.mp3";

        Runnable loadThread = new Runnable() {
            @Override
            public void run() {
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    mediaPlayer.setDataSource(url);
                    mediaPlayer.prepare(); // might take long! (for buffering, etc)
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaPlayer.start();
            }
        };
        new Thread(loadThread).start();
        */




    }

}
