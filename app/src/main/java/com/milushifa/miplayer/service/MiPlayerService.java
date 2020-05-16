package com.milushifa.miplayer.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.milushifa.miplayer.R;
import com.milushifa.miplayer.service.player.ControllerConstants;
import com.milushifa.miplayer.service.player.Player;
import com.milushifa.miplayer.ui.MainActivity;
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
            case ControllerConstants.CONTROL_TRACK:
                if(mPlayer.isPlaying()){
                    mPlayer.pauseTrack();
                }else{
                    mPlayer.startTrack();
                }
                break;
            case ControllerConstants.NEXT_TRACK:
                mPlayer.nextTrack();
                break;
            case ControllerConstants.PREV_TRACK:
                mPlayer.previousTrack();
                break;
            case ControllerConstants.STOP_SERVICE:

                break;
            case ControllerConstants.SET_TRACK_POSITION:
                mPlayer.setSeekTo(intent.getIntExtra("PROGRESS", 0));
                break;
        }
        createForegroundService(mPlayer.getCurrentTrackTitle());
        return START_NOT_STICKY;
    }




    private void createForegroundService(String track) {
        // Content Intent
        Intent pendingIntent = new Intent(this, MainActivity.class);
        pendingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 1, pendingIntent, 0);

//        Notification notification = new NotificationCompat.MediaStyle(this, Flags.CHANNEL_ID)
        Notification notification = new NotificationCompat.Builder(this, Flags.CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("MiPlayer")
                .setContentText("Track: " + track)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(contentIntent)
                .build();

        startForeground(123, notification);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
