package com.milushifa.miplayer.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.milushifa.miplayer.service.player.ControllerConstants;
import com.milushifa.miplayer.service.player.Player;
import com.milushifa.miplayer.util.Flags;

public class MiPlayerService extends Service {

    private Player mPlayer;

    @Override
    public void onCreate() {
        super.onCreate();
        mPlayer = new Player(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        switch(action){
            case ControllerConstants.PLAY_TRACK:
                mPlayer.playTrack();
                break;
        }
        return START_NOT_STICKY;
    }












    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
