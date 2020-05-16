package com.milushifa.miplayer.service.player;

import android.content.Context;
import android.content.Intent;

import com.milushifa.miplayer.receiver.BroadcastAction;

public class BroadcastMessageHandler {
    private Intent broadcastIntent;
    private Context mContext;

    public BroadcastMessageHandler(Context context){
        this.mContext = context;
    }

    public void durationBroadcast(int duration){
        broadcastIntent = new Intent(BroadcastAction.SEND_DURATION);
        broadcastIntent.putExtra(BroadcastAction.DURATION, duration);
        send(broadcastIntent);
    }

    public void currentPositionBroadcast(int position){
        broadcastIntent = new Intent(BroadcastAction.SEND_CURRENT_POSITION);
        broadcastIntent.putExtra(BroadcastAction.CURRENT_POSITION, position);
        send(broadcastIntent);
    }

    public void playingStatusBroadcast(boolean isPlaying){
        broadcastIntent = new Intent(BroadcastAction.SEND_PLAYING_STATUS);
        broadcastIntent.putExtra(BroadcastAction.PLAYING_STATUS, isPlaying);
        send(broadcastIntent);
    }

    public void trackChangeBroadcast(){
        broadcastIntent = new Intent(BroadcastAction.TRACK_CHANGE);
        send(broadcastIntent);
    }



    private void send(Intent intent){
        mContext.sendBroadcast(intent);
    }


}
