package com.milushifa.miplayer.service.player;

import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;

import com.milushifa.miplayer.media.model.Track;

import java.util.ArrayList;
import java.util.List;

public class MPPlayable {
    private List<Track> mTrackList;
    private int mCurrentPosition;

    private static MPPlayable instance;

    private MPPlayable(){
        mTrackList = new ArrayList<>();
    }

    public static MPPlayable getInstance(){
        if(instance==null){
            instance = new MPPlayable();
        }
        return instance;
    }


    public void updateCurrentPlayable(List<Track> playableList, int currentPlayable){
        this.mTrackList = playableList;
        this.mCurrentPosition = currentPlayable;
    }

    public Uri getCurrentPlayableTrack(){
        return ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                mTrackList.get(mCurrentPosition).id);
    }

    public String getCurrentTrack(){
        return mTrackList.get(mCurrentPosition).title;
    }

    public void nextPlayableTrack(){
        if(mCurrentPosition!=mTrackList.size()-1){
            mCurrentPosition++;
        }else{
            mCurrentPosition = 0;
        }
    }
    public void prevPlayableTrack(){
        if(mCurrentPosition!=0){
            mCurrentPosition--;
        }else{
            mCurrentPosition = mTrackList.size()-1;
        }
    }

    public int getDurationOfCurrentTrack() {
        return mTrackList.get(mCurrentPosition).duration;
    }
}
