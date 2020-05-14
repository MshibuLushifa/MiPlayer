package com.milushifa.miplayer.service.player;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

import com.milushifa.miplayer.receiver.PlayerTracker;

public class Player implements IPlayer, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {
    private Context context;
    private MediaPlayer mediaPlayer;
    private MPPlayable mpPlayable;

    private PlayerTracker mPlayerTracker;

    public Player(Context context){
        this.context = context;
        mediaPlayer = new MediaPlayer();

        mediaPlayer.setOnCompletionListener(this);

        mpPlayable = MPPlayable.getInstance();

        mPlayerTracker = PlayerTracker.getInstance();
    }

    @Override public void playTrack(){
        releaseMiPlayer();
        Uri uri = mpPlayable.getCurrentPlayableTrack();
        mediaPlayer = MediaPlayer.create(context, uri);
        mediaPlayer.start();
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnErrorListener(this);

        mPlayerTracker.updateDurationOfTrack(getDuration());
        mPlayerTracker.updatePlayingStatus(true);
        mPlayerTracker.updateCurrentTrackTitle(mpPlayable.getCurrentTrackTitle());
        mPlayerTracker.updateCurrentTrackDetails(mpPlayable.getCurrentTrackDetails());
        mPlayerTracker.updateCurrentTrackAlbumArtUri(mpPlayable.getTCurrentTrackUri());
    }

    @Override public void pauseTrack() {
        mediaPlayer.pause();
        mPlayerTracker.updatePlayingStatus(false);
    }

    @Override
    public void startTrack() {
        mediaPlayer.start();
        mPlayerTracker.updatePlayingStatus(true);
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
        mPlayerTracker.updatePlayingStatus(false);
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

    public String getCurrentTrackTitle(){ return mpPlayable.getCurrentTrackTitle(); }

    public int getDuration(){
        return mpPlayable.getDurationOfCurrentTrack();
    }

    public int getCurrentPosition(){mPlayerTracker.updateCurrentPositionOfTrack(mediaPlayer.getCurrentPosition()); return mediaPlayer.getCurrentPosition(); }

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
