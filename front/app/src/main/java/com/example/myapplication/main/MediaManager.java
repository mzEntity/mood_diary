package com.example.myapplication.main;

import android.media.MediaPlayer;
import android.util.Log;

public class MediaManager {
    private static MediaPlayer mediaPlayer;

    private static boolean stopped;

    public MediaManager() {
    }

    public void setPlayer(MediaPlayer mediaPlayer){
        MediaManager.mediaPlayer = mediaPlayer;
    }

    public boolean hasMusicLoaded(){
        return mediaPlayer != null;
    }

    public void switchAudioState() {
        if(mediaPlayer == null) return;
        Log.i("MediaManager", "PlayAudio " + mediaPlayer.isPlaying());
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        } else {
            mediaPlayer.pause();
        }
    }

    public void stopAudio() {
        if(mediaPlayer == null) return;
        Log.i("MediaManager", "StopAudio " + mediaPlayer.isPlaying());
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.prepareAsync();
        }
    }

    public void clearSource(){
        mediaPlayer = null;
    }
}
