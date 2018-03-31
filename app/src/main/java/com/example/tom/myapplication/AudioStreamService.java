package com.example.tom.myapplication;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AudioStreamService extends MediaBrowserServiceCompat {
    private static final String MY_MEDIA_ROOT_ID = "media_root_id";
    private static final String MY_EMPTY_MEDIA_ROOT_ID = "empty_root_id";
    private static final int NOTIFICATION_ID = 412;

    private static final String LOG_TAG = "Creating MediaSessionCompat";
    private static final String TAG = "AudioStreamService";

    private MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;

    @Override
    public void onCreate() {
        super.onCreate();

        // Create a MediaSessionCompat
        mMediaSession = new MediaSessionCompat(this/*context*/, LOG_TAG);

        // Enable callbacks from MediaButtons and TransportControls
        mMediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player
        mStateBuilder = new PlaybackStateCompat.Builder().setActions(
                PlaybackStateCompat.ACTION_PLAY |
                PlaybackStateCompat.ACTION_PLAY_PAUSE);
        mMediaSession.setPlaybackState(mStateBuilder.build());

        // MySessionCallback() has methods that handle callbacks from a media controller
        mMediaSession.setCallback(new MySessionCallback());

        // Set the session's token so that client activities can communicate with it
        setSessionToken(mMediaSession.getSessionToken());
    }

    @Override
    public BrowserRoot onGetRoot(String clientPackageName, int clientUid, Bundle rootHints) {

        if (allowBrowsing(clientPackageName, clientUid)) {
            // Returns a root ID that clients can use with onLoadChildren() to retrieve
            // the content hierarchy.
            return new BrowserRoot(MY_MEDIA_ROOT_ID, null);
        } else {
            // Clients can connect, but this BrowserRoot is an empty hierachy
            // so onLoadChildren returns nothing. This disables the ability to browse for content.
            Log.i(TAG, "OnGetRoot: Browsing NOT ALLOWED for unknown caller. "
                    + "Returning empty browser root so all apps can use MediaController."
                    + clientPackageName);
            return new BrowserRoot(MY_EMPTY_MEDIA_ROOT_ID, null);
        }
    }

    @Override
    public void onLoadChildren(final String parentMediaId, final Result<List<MediaBrowserCompat.MediaItem>> result) {

        // Browsing not allowed
        if (TextUtils.equals(MY_EMPTY_MEDIA_ROOT_ID, parentMediaId)) {
            result.sendResult(null);
            return;
        }

        // Assume for example that the music catalog is already loaded/cached.

        List<MediaBrowserCompat.MediaItem> mediaItems = new ArrayList<>();

        // Check if this is the root menu:
        if (MY_MEDIA_ROOT_ID.equals(parentMediaId)) {
            // Build the MediaItem objects for the top level,
            // and put them in the mediaItems list...
        } else {
            // Examine the passed parentMediaId to see which submenu we're at,
            // and put the children of that menu in the mediaItems list...
        }
        result.sendResult(mediaItems);

    }

    private Boolean allowBrowsing(String clientPackageName, int clientUid) {
        // TODO: Add logic to only allow certain packages
        if (true) {
            return true;
        } else
            return false;
    }


    private class MySessionCallback extends MediaSessionCompat.Callback {
        // MAY NEED TO DEFINE ONPAUSE()

        @Override
        public void onPlay() {
            MediaControllerCompat controller = mMediaSession.getController();
            MediaMetadataCompat mediaMetadata = controller.getMetadata();
            MediaDescriptionCompat description = mediaMetadata.getDescription();

            Context context = getApplicationContext();

            NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext()); // /*getContext()*/);

            builder
                    // Add the metadata for the currently playing track
                    .setContentTitle(description.getTitle())
                    .setContentText((description.getSubtitle()))
                    .setSubText(description.getDescription())
                    .setLargeIcon(description.getIconBitmap())

                    // Enable launching the player by clicking the notification
                    .setContentIntent(controller.getSessionActivity())

                    // Stop the service when the notification is swiped away
                    .setDeleteIntent(MediaButtonReceiver.buildMediaButtonPendingIntent(getBaseContext()/*this*/, PlaybackStateCompat.ACTION_STOP))

                    // Make the transport controls visible on the lockscreen
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)

                    // Add an app icon and set its accent color
                    .setSmallIcon(R.drawable.ic_notification)
                    .setColor(ContextCompat.getColor(context /*this*/, R.color.colorPrimaryDark))

                    // Add a pause button
                    .addAction(new NotificationCompat.Action(
                            R.drawable.ic_pause_black_36dp, getString(R.string.pause),
                            MediaButtonReceiver.buildMediaButtonPendingIntent(context /*this*/, PlaybackStateCompat.ACTION_PLAY_PAUSE)))

                    // Take advantage of MediaStyle features
                    .setStyle(new NotificationCompat.MediaStyle()
                            .setMediaSession(mMediaSession.getSessionToken())
                            .setShowActionsInCompactView(0)

                    // Add a cancel button
                            .setShowCancelButton(true)
                            .setCancelButtonIntent(MediaButtonReceiver.buildMediaButtonPendingIntent(context /*this*/, PlaybackStateCompat.ACTION_STOP)));

            // Display the notification and place the service in the foreground
            startForeground(NOTIFICATION_ID, builder.build());
        }

    }
}

/*
public class AudioStreamService extends Service implements MediaPlayer.OnPreparedListener {
    static final String ACTION_PLAY = "com.example.tom.myapplication.action.PLAY";
    MediaPlayer mediaPlayer = null;

    public AudioStreamService() {
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("IN AUDIO STREAM SERVICE");
        System.out.println(intent.getAction());
        Bundle bundle = intent.getExtras();
        final String url = bundle.getString("URL");

        if  (intent.getAction().equals(ACTION_PLAY)) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mediaPlayer.setDataSource(url);
                mediaPlayer.prepareAsync(); // might take long! (for buffering, etc)
            } catch (IOException e) {
                e.printStackTrace();
            }
            // mediaPlayer.start();
        }
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void onPrepared(MediaPlayer player) {
        player.start();
    }
}*/
