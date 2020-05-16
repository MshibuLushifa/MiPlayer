package com.milushifa.miplayer.service.player;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.milushifa.miplayer.media.model.Track;
import com.milushifa.miplayer.util.Flags;

public class PlayerTracker{
    private Track currentTrack = new Track();

    private int mPositionOfCurrentTrack = 0;

    private boolean isPlaying = false;

    public void updateWithCurrentTrack(Track currentTrack){
        this.currentTrack = currentTrack;
    }
    public void updatePlayingStatus(boolean isPlaying){
        this.isPlaying = isPlaying;
    }

    public Uri getCurrentPlayableTrack(){
        return ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, currentTrack.id);
    }
    public int getDurationOfCurrentTrack(){ return currentTrack.duration; }
    public int getPositionOfCurrentTrack() { return mPositionOfCurrentTrack; }

    public boolean getPlayingStatus() {
        return isPlaying;
    }

    public String getCurrentTrackTitle() { return currentTrack.title; }
    public String getCurrentTrackDetails() { return currentTrack.artist + ", " + currentTrack.artist; }
    public Uri getCurrentTrackAlbumArtUri() { return ContentUris.withAppendedId(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, currentTrack.id); }







    private static PlayerTracker instance;

    public static PlayerTracker getInstance(){
        if(instance==null){
            instance = new PlayerTracker();
        }
        return instance;
    }
}
