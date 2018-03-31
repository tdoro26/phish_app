package com.example.tom.myapplication;

import android.content.ComponentName;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.tom.myapplication.Phish.in.Objects.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NowPlayingActivity extends AppCompatActivity {

    private static final String TAG = "NowPlayingActivity";

    private MediaPlayer mediaPlayer;
    private Button playTrack;
    private ImageView mPlayPause;

    private MediaBrowserCompat mMediaBrowser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMediaBrowser = new MediaBrowserCompat(this, new ComponentName(this, AudioStreamService.class),
                mConnectionCallbacks, null);
    }

    @Override
    public void onStart() {
        super.onStart();
        mMediaBrowser.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        // (see "stay in sync with the MediaSession")
        if (MediaControllerCompat.getMediaController(NowPlayingActivity.this) != null) {
            MediaControllerCompat.getMediaController(NowPlayingActivity.this).unregisterCallback(controllerCallback);
        }
        mMediaBrowser.disconnect();
    }

    private final MediaBrowserCompat.ConnectionCallback mConnectionCallbacks = new MediaBrowserCompat.ConnectionCallback() {
        @Override
        public void onConnected() {
            // Get the token for the MediaSession
            MediaSessionCompat.Token token = mMediaBrowser.getSessionToken();
            MediaControllerCompat mediaController;

            try {
                // Create a MediaControllerCompat
                mediaController =
                        new MediaControllerCompat(NowPlayingActivity.this, /*Context*/ token);
                // Save the controller
                MediaControllerCompat.setMediaController(NowPlayingActivity.this, mediaController);

            } catch (android.os.RemoteException e) {
                // Do something
                Log.d(TAG, "Caught android.os.RemoteException");
            }

            // Finish building the UI
            buildTransportControls();
        }

        @Override
        public void onConnectionSuspended() {
            // The Service has crashed. Disable transport controls until it automatically reconnects
        }

        @Override
        public void onConnectionFailed() {
            // The Service has refused our connection
        }
    };

    void buildTransportControls() {
        // Grab the view for the play/pause button
        //mPlayPause = (ImageView) findViewById(R.id.ic_play_arrow_black_36dp);
        //mPlayPause = R.drawable.ic_play_arrow_black_36dp);
        // Attach listener to the button
        playTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Since this is a play/pause button, we need to test the current state
                // and choose the action accordingly
                int pbState = MediaControllerCompat.getMediaController(NowPlayingActivity.this).getPlaybackState().getState();
                if (pbState == PlaybackStateCompat.STATE_PLAYING) {
                    MediaControllerCompat.getMediaController(NowPlayingActivity.this).getTransportControls().pause();
                } else {
                    MediaControllerCompat.getMediaController(NowPlayingActivity.this).getTransportControls().play();
                }
            }
        });

        MediaControllerCompat mediaController = MediaControllerCompat.getMediaController(NowPlayingActivity.this);

        // Display the initial state
        MediaMetadataCompat metadata = mediaController.getMetadata();
        PlaybackStateCompat pbState = mediaController.getPlaybackState();

        // Register a Callback to stay in sync
        mediaController.registerCallback(controllerCallback);
    }

    MediaControllerCompat.Callback controllerCallback = new MediaControllerCompat.Callback() {
        @Override
        public void onPlaybackStateChanged(PlaybackStateCompat state) {
            super.onPlaybackStateChanged(state);
        }

        @Override
        public void onMetadataChanged(MediaMetadataCompat metadata) {
            super.onMetadataChanged(metadata);
        }
    };

    /**
     * OLD ON CREATE METHOD
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

//        ***MUSIC NO LONGER STREAMED FROM THIS ACTIVITY***
//        mediaPlayer = new MediaPlayer();
//        //String url = getIntent().getSerializableExtra("URL");
//        //final String url = "http://phish.in/audio/000/017/963/17963.mp3";
//
//        Runnable loadThread = new Runnable() {
//            @Override
//            public void run() {
//                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//                try {
//                    mediaPlayer.setDataSource(url);
//                    mediaPlayer.prepare(); // might take long! (for buffering, etc)
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                mediaPlayer.start();
//            }
//        };
//        new Thread(loadThread).start();
    }
    **/

}
