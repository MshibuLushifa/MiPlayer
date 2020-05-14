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
import com.milushifa.miplayer.receiver.ConstantsBroadcast;
import com.milushifa.miplayer.service.player.ControllerConstants;
import com.milushifa.miplayer.service.player.Player;
import com.milushifa.miplayer.ui.MainActivity;
import com.milushifa.miplayer.util.Flags;

public class MiPlayerService extends Service {

    private static final int PLAY_STATUS_TRUE = 1;
    private static final int PLAY_STATUS_FALSE = 0;

    private Player mPlayer;

    private Handler mHandler;
    private Runnable mRunnable;

    @Override
    public void onCreate() {
        super.onCreate();
        mPlayer = new Player(this);

        mHandler = new Handler();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        switch(action){
            case ControllerConstants.PLAY_TRACK:
                mPlayer.playTrack();
                sendBroadcastToUi(ConstantsBroadcast.SEND_DURATION, mPlayer.getDuration());
                sendBroadcastToUi(ConstantsBroadcast.SEND_PLAYING_STATUS, PLAY_STATUS_TRUE);
                traceTrackProgress();
                break;
            case ControllerConstants.CONTROL_TRACK:
                if(mPlayer.isPlaying()){
                    mPlayer.pauseTrack();
                    sendBroadcastToUi(ConstantsBroadcast.SEND_PLAYING_STATUS, PLAY_STATUS_FALSE);
                }else{
                    mPlayer.startTrack();
                    sendBroadcastToUi(ConstantsBroadcast.SEND_PLAYING_STATUS, PLAY_STATUS_TRUE);
                    traceTrackProgress();
                }
                break;
            case ControllerConstants.NEXT_TRACK:
                mPlayer.nextTrack();
                sendBroadcastToUi(ConstantsBroadcast.SEND_DURATION, mPlayer.getDuration());
                sendBroadcastToUi(ConstantsBroadcast.SEND_PLAYING_STATUS, PLAY_STATUS_TRUE);
                sendBroadcastToUi(ConstantsBroadcast.TRACK_CHANGE, 0);
                traceTrackProgress();
                break;
            case ControllerConstants.PREV_TRACK:
                mPlayer.previousTrack();
                sendBroadcastToUi(ConstantsBroadcast.SEND_DURATION, mPlayer.getDuration());
                sendBroadcastToUi(ConstantsBroadcast.SEND_PLAYING_STATUS, PLAY_STATUS_TRUE);
                sendBroadcastToUi(ConstantsBroadcast.TRACK_CHANGE, 0);
                traceTrackProgress();
                break;
            case ControllerConstants.STOP_SERVICE:
                mPlayer.stopTrack();
                sendBroadcastToUi(ConstantsBroadcast.SEND_PLAYING_STATUS, PLAY_STATUS_FALSE);
                break;
            case ControllerConstants.SET_TRACK_POSITION:
                mPlayer.setSeekTo(intent.getIntExtra("PROGRESS", 0));
                traceTrackProgress();
                break;
        }
        createForegroundService(mPlayer.getCurrentTrackTitle());
        return START_NOT_STICKY;
    }


    private void sendBroadcastToUi(String action, int data){
        Intent intent = new Intent();
        if(ConstantsBroadcast.SEND_DURATION.equals(action)){

            intent.setAction(ConstantsBroadcast.SEND_DURATION);
            intent.putExtra(ConstantsBroadcast.DURATION, data);

//            mPlayerTracker.updateDurationOfTrack(data);

        }else if(ConstantsBroadcast.SEND_CURRENT_POSITION.equals(action)){

            intent.setAction(ConstantsBroadcast.SEND_CURRENT_POSITION);
            intent.putExtra(ConstantsBroadcast.CURRENT_POSITION, data);

//            mPlayerTracker.updateCurrentPositionOfTrack(data);

        }else if(ConstantsBroadcast.SEND_PLAYING_STATUS.equals(action)){
            intent.setAction(ConstantsBroadcast.SEND_PLAYING_STATUS);
            if (PLAY_STATUS_TRUE==data) {
                intent.putExtra(ConstantsBroadcast.PLAYING_STATUS, true);
            }else if(PLAY_STATUS_FALSE==data){
                intent.putExtra(ConstantsBroadcast.PLAYING_STATUS, false);
            }

        }else if(ConstantsBroadcast.TRACK_CHANGE.equals(action)){
            intent.setAction(ConstantsBroadcast.TRACK_CHANGE);
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
//                        mPlayerTracker.updateCurrentPositionOfTrack(currentPosition);
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
