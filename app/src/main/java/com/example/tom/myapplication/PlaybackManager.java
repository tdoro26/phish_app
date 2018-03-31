//package com.example.tom.myapplication;
//
//import android.content.Context;
//import android.content.IntentFilter;
//import android.media.AudioManager;
//import android.service.media.MediaBrowserService;
//import android.support.v4.media.session.MediaSessionCompat;
//
///**
// * Created by tom on 3/28/18.
// */
//
//public class PlaybackManager {
//    private IntentFilter intentFilter = new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
//
//    private AudioManager.OnAudioFocusChangeListener afChangeListener;
//    private BecomingNoisyReceiver myNoisyAudioStreamReceiver = new BecomingNoisyReceiver();
//    private MediaStyleNotification myPLayerNotification;
//    private MediaSessionCompat mediaSession;
//    private MediaBrowserService service;
//    private SomeKindOfPlayer player;
//
//    MediaSessionCompat.Callback callback = new MediaSessionCompat.Callback() {
//        @Override
//        public void onPlay() {
//            // super.onPlay();
//            AudioManager am = mContext.getSystemService(Context.AUDIO_SERVICE);
//            // Request audio focus for playback, this registers the afChangeListener
//            int result = am.requestAudioFocus(afChangeListener,
//                    // Use the music stream.
//                    AudioManager.STREAM_MUSIC,
//                    // Request permanent focus.
//                    AudioManager.AUDIOFOCUS_GAIN);
//
//            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
//                // Start the service
//                service.start();
//                // Set the session active (and update the metadata and state)
//                mediaSession.setActive(true);
//                // Start the player (custom call)
//                player.start();
//                // Register BECOME_NOISY BroadcastReceiver
//                registerReceiver(myNoisyAudioStreamReceiver, intentFilter);
//                // Put the service in the foreground, post notification
//                service.startForeground(myPLayerNotification);
//            }
//        }
//
//        @Override
//        public void onPause() {
//            // super.onPause();
//            AudioManager am = mContext.getSystemService(Context.AUDIO_SERVICE);
//            // Update the metadata and state
//            // pause the player (custom call)
//            player.pause();
//            // unregister BECOME_NOISY BroadcastReceiver
//            unregisterReceiver(myNoisyAudioStreamReceiver, intentFilter);
//            // Take the service out of the foreground, retain the notification
//            service.startForeground(false);
//        }
//
//        @Override
//        public void onStop() {
//            // super.onStop();
//            AudioManager am = mContext.getSystemService(Context.AUDIO_SERVICE);
//            // Abandon audio focus
//            am.abandonAudioFocus(afChangeListener);
//            unregisterReceiver(myNoisyAudioStreamReceiver);
//            // Start the service
//            service.stop();
//            // Set the session inactive (and update metadata and state)
//            mediaSession.setActive(false);
//            // Stop the player (custom call)
//            player.stop();
//            // Take the service out of the foreground
//            service.stopForeground(false);
//        }
//    };
//
//}
