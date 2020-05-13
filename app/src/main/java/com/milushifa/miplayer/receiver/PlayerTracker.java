package com.milushifa.miplayer.receiver;

public class PlayerTracker {
    private int mDurationOfCurrentTrack = -1;
    private int mPositionOfCurrentTrack = -1;

    public int getDurationOfCurrentTrack(){ return mDurationOfCurrentTrack; }
    public int getPositionOfCurrentTrack() { return mPositionOfCurrentTrack; }

    public void updateDurationOfTrack(int duration){
        mDurationOfCurrentTrack = duration;
    }
    public void updateCurrentPositionOfTrack(int position){
        mPositionOfCurrentTrack = position;
    }


    private static PlayerTracker instance;

    public static PlayerTracker getInstance(){
        if(instance==null){
            instance = new PlayerTracker();
        }
        return instance;
    }

}
