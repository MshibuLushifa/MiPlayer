package com.milushifa.miplayer.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.milushifa.miplayer.R;
import com.milushifa.miplayer.receiver.ConstantsBroadcast;
import com.milushifa.miplayer.receiver.PlayerTracker;
import com.milushifa.miplayer.service.player.ControllerConstants;
import com.milushifa.miplayer.service.player.Player;
import com.milushifa.miplayer.ui.MainActivity;
import com.milushifa.miplayer.util.Flags;

public class MiPlayerService extends Service {

    private Player mPlayer;

    private PlayerTracker mPlayerTracker;

    private Handler mHandler;
    private Runnable mRunnable;

    @Override
    public void onCreate() {
        super.onCreate();
        mPlayer = new Player(this);
        mPlayerTracker = PlayerTracker.getInstance();

        mHandler = new Handler();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        switch(action){
            case ControllerConstants.PLAY_TRACK:
                mPlayer.playTrack();
                sendBroadcastToUi(ConstantsBroadcast.SEND_DURATION, mPlayer.getDuration());
                mPlayerTracker.updateDurationOfTrack(mPlayer.getDuration());
                traceTrackProgress();
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
                sendBroadcastToUi(ConstantsBroadcast.SEND_DURATION, mPlayer.getDuration());
                traceTrackProgress();
                break;
            case ControllerConstants.PREV_TRACK:
                mPlayer.previousTrack();
                sendBroadcastToUi(ConstantsBroadcast.SEND_DURATION, mPlayer.getDuration());
                traceTrackProgress();
                break;
            case ControllerConstants.STOP_SERVICE:
                mPlayer.stopTrack();
                break;
            case ControllerConstants.SET_TRACK_POSITION:
                mPlayer.setSeekTo(intent.getIntExtra("PROGRESS", 0));
                traceTrackProgress();
                break;
        }
        createForegroundService(mPlayer.getCurrentTrack());
        return START_NOT_STICKY;
    }


    private void sendBroadcastToUi(String action, int data){
        Intent intent = new Intent();
        if(ConstantsBroadcast.SEND_DURATION.equals(action)){

            intent.setAction(ConstantsBroadcast.SEND_DURATION);
            intent.putExtra(ConstantsBroadcast.DURATION, data);
            mPlayerTracker.updateDurationOfTrack(data);

        }else if(ConstantsBroadcast.SEND_CURRENT_POSITION.equals(action)){

            intent.setAction(ConstantsBroadcast.SEND_CURRENT_POSITION);
            intent.putExtra(ConstantsBroadcast.CURRENT_POSITION, data);
            mPlayerTracker.updateCurrentPositionOfTrack(data);

        }
        sendBroadcast(intent);
    }



    public void traceTrackProgress(){
        try{
            if(mPlayer.isPlaying()){
                mRunnable = new Runnable() {
                    @Override
                    public void run() {
                        int currentPosition = mPlayer.getCurrentPosition();
                        sendBroadcastToUi(ConstantsBroadcast.SEND_CURRENT_POSITION, currentPosition);
                        mPlayerTracker.updateCurrentPositionOfTrack(currentPosition);
                        traceTrackProgress();
                    }
                };
                mHandler.postDelayed(mRunnable, 1000);
            }
        }catch (IllegalStateException ex){

        }
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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
