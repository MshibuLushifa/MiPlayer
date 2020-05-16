package com.milushifa.miplayer.service.player;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.MediaStore;

import com.milushifa.miplayer.media.model.Track;

import java.util.ArrayList;
import java.util.List;

public class MPPlayable {
    private List<Track> mTrackList;
    private int mCurrentPosition;

    private PlayerTracker mPlayerTracker;

    public int getCurrentPosition(){
        return mCurrentPosition;
    }


    public void updateCurrentPlayable(List<Track> playableList, int currentPlayable){
        this.mTrackList = playableList;
        this.mCurrentPosition = currentPlayable;
        mPlayerTracker = PlayerTracker.getInstance();
        playerTrackerUpdate();
    }

    public void nextPlayableTrack(){
        if(mCurrentPosition!=mTrackList.size()-1){
            mCurrentPosition++;
        }else{
            mCurrentPosition = 0;
        }
        playerTrackerUpdate();
    }
    public void prevPlayableTrack(){
        if(mCurrentPosition!=0){
            mCurrentPosition--;
        }else{
            mCurrentPosition = mTrackList.size()-1;
        }
        playerTrackerUpdate();
    }

    private void playerTrackerUpdate(){
        mPlayerTracker.updateWithCurrentTrack(mTrackList.get(mCurrentPosition));
    }
















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
}
