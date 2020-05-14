package com.milushifa.miplayer.receiver;

import android.net.Uri;

public class PlayerTracker{

    private int mDurationOfCurrentTrack = -1;
    private int mPositionOfCurrentTrack = -1;

    private boolean isPlaying = false;

    private String currentTrackTitle;
    private String currentTrackDetails;
    private Uri currentTrackAlbumArtUri;

    public int getDurationOfCurrentTrack(){ return mDurationOfCurrentTrack; }
    public int getPositionOfCurrentTrack() { return mPositionOfCurrentTrack; }

    public boolean getPlayingStatus() { return isPlaying; }

    public String getCurrentTrackTitle() { return currentTrackTitle; }
    public String getCurrentTrackDetails() { return currentTrackDetails; }
    public Uri getCurrentTrackAlbumArtUri() { return currentTrackAlbumArtUri; }

    public void updateDurationOfTrack(int duration){
        mDurationOfCurrentTrack = duration;
    }
    public void updateCurrentPositionOfTrack(int position){
        mPositionOfCurrentTrack = position;
    }

    public void updatePlayingStatus(boolean isPlaying){ this.isPlaying = isPlaying; }

    public void updateCurrentTrackTitle(String currentTrackTitle){ this.currentTrackTitle = currentTrackTitle; }
    public void updateCurrentTrackDetails(String currentTrackDetails){ this.currentTrackDetails = currentTrackDetails; }
    public void updateCurrentTrackAlbumArtUri(Uri currentTrackAlbumArtUri){ this.currentTrackAlbumArtUri = currentTrackAlbumArtUri; }

    private static PlayerTracker instance;

    public static PlayerTracker getInstance(){
        if(instance==null){
            instance = new PlayerTracker();
        }
        return instance;
    }
}
