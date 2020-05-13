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

        mediaPlayer.setOnCompletionListener(this);

        mpPlayable = MPPlayable.getInstance();
    }

    @Override public void playTrack(){
        releaseMiPlayer();
        Uri uri = mpPlayable.getCurrentPlayableTrack();
        mediaPlayer = MediaPlayer.create(context, uri);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnErrorListener(this);
    }

    @Override public void pauseTrack() {
        mediaPlayer.pause();
    }

    @Override
    public void startTrack() {
        mediaPlayer.start();
    }

    @Override public void nextTrack() {
        mpPlayable.nextPlayableTrack();
        playTrack();
    }

    @Override public void previousTrack() {
        mpPlayable.prevPlayableTrack();
        playTrack();
    }

    @Override public void stopTrack() {
        releaseMiPlayer();
    }

    public boolean isPlaying(){
        return mediaPlayer.isPlaying();
    }

    @Override public void onCompletion(MediaPlayer mp) {
        nextTrack();
    }

    @Override public boolean onError(MediaPlayer mp, int what, int extra) {
        mediaPlayer.reset();
        return false;
    }

    public String getCurrentTrack(){
        return mpPlayable.getCurrentTrack();
    }

    public int getDuration(){
        return mpPlayable.getDurationOfCurrentTrack();
    }

    public int getCurrentPosition(){ return mediaPlayer.getCurrentPosition(); }

    public void setSeekTo(int position){
        mediaPlayer.seekTo(position);
    }

    private void releaseMiPlayer(){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
}
