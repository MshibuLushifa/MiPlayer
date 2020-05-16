package com.milushifa.miplayer.service.player;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Handler;

import com.milushifa.miplayer.service.player.datasaver.CookieDBHelper;
import com.milushifa.miplayer.service.player.datasaver.CookieHelper;

public class Player implements IPlayer, MediaPlayer.OnCompletionListener {
    public static final String LAST_TRACK_POSITION = "last_track_position";
    public static final String TRACK_POSITION = "track_position";
    private Context context;
    private MediaPlayer mediaPlayer;
    private MPPlayable mpPlayable;

    private PlayerTracker mPlayerTracker;

    private BroadcastMessageHandler mBroadcastMessageHandler;

    private Handler mHandler;
    private Runnable mRunnable;

    public Player(Context context){
        this.context = context;
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(this);
        mpPlayable = MPPlayable.getInstance();
        mPlayerTracker = PlayerTracker.getInstance();
        mBroadcastMessageHandler = new BroadcastMessageHandler(context);
        mHandler = new Handler();
    }

    @Override public void playTrack(){
        releaseMiPlayer();
        mediaPlayer = MediaPlayer.create(context, mPlayerTracker.getCurrentPlayableTrack());
        mediaPlayer.start();
        mPlayerTracker.updatePlayingStatus(true);
        mBroadcastMessageHandler.durationBroadcast(mPlayerTracker.getDurationOfCurrentTrack());
        mBroadcastMessageHandler.playingStatusBroadcast(true);
        mBroadcastMessageHandler.trackChangeBroadcast();
        traceTrackProgress();

        storeAsPreference(context, mpPlayable.getCurrentPosition());
    }

    private void storeAsPreference(Context context, int track_position){
        SharedPreferences sharedPreferences = context.getSharedPreferences(LAST_TRACK_POSITION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(TRACK_POSITION, track_position);
        editor.apply();
    }

    @Override public void pauseTrack() {
        mediaPlayer.pause();
        mBroadcastMessageHandler.playingStatusBroadcast(false);
        mPlayerTracker.updatePlayingStatus(false);
    }
    @Override
    public void startTrack() {
        mediaPlayer.start();
        mBroadcastMessageHandler.playingStatusBroadcast(true);
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
    }

    @Override public void onCompletion(MediaPlayer mp) {
        nextTrack();
    }

    public void setSeekTo(int duration){
        mediaPlayer.seekTo(duration);
    }


    public String getCurrentTrackTitle(){
        return mPlayerTracker.getCurrentTrackTitle();
    }
    public boolean isPlaying(){
        return mediaPlayer.isPlaying();
    }



    private void traceTrackProgress(){
        if(mediaPlayer.isPlaying()){
            mRunnable = new Runnable() {
                @Override
                public void run() {
                    mBroadcastMessageHandler.currentPositionBroadcast(mediaPlayer.getCurrentPosition());
                    traceTrackProgress();
                }
            };
            mHandler.postDelayed(mRunnable, 1000);
        }
    }
    private void releaseMiPlayer(){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

}
