package com.milushifa.miplayer.service.player;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import com.milushifa.miplayer.util.Flags;

public class Player implements IPlayer, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {
    private Context context;
    private MediaPlayer mediaPlayer;

    private MPPlayable mpPlayable;

    public Player(Context context){
        this.context = context;
        mediaPlayer = new MediaPlayer();

        mpPlayable = MPPlayable.getInstance();
    }

    @Override public void playTrack(){
        releaseMiPlayer();
        Uri uri = mpPlayable.getCurrentPlayableTrack();
        mediaPlayer = MediaPlayer.create(context, uri);
        mediaPlayer.start();

    }

    private void releaseMiPlayer(){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    @Override public void pauseTrack() {

    }

    @Override public void nextTrack() {

    }

    @Override public void previousTrack() {

    }

    @Override public void onCompletion(MediaPlayer mp) {

    }

    @Override public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }
}
