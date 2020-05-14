package com.milushifa.miplayer.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.milushifa.miplayer.util.Flags;

public class UiDataReceiver extends BroadcastReceiver {
    private static UiUpdater mUiUpdater;
    private static MediaObserver mMediaObserverForMiPlayer, mMediaObserverForMiniPlayer;
    @Override
    public void onReceive(Context context, Intent intent) {
        if(mUiUpdater!=null && mUiUpdater.isActivityActive()){
            if(ConstantsBroadcast.SEND_DURATION.equals(intent.getAction())){
                mUiUpdater.setDuration(intent.getIntExtra(ConstantsBroadcast.DURATION, 0));
            }else if(ConstantsBroadcast.SEND_CURRENT_POSITION.equals(intent.getAction())){
                mUiUpdater.setSeekProgress(intent.getIntExtra(ConstantsBroadcast.CURRENT_POSITION, 0));
            }else if(ConstantsBroadcast.SEND_PLAYING_STATUS.equals(intent.getAction())){
                mMediaObserverForMiPlayer.mediaChangePlayingStatus(intent.getBooleanExtra(ConstantsBroadcast.PLAYING_STATUS, false));
            }else if(ConstantsBroadcast.TRACK_CHANGE.equals(intent.getAction())){
                mUiUpdater.trackChange();
            }
            Log.i(Flags.TAG, "onReceive: is Called!");
        }
    }

    public static void setUiUpdater(UiUpdater uiUpdater){
        mUiUpdater = uiUpdater;
    }
    public static void setMediaObserver(MediaObserver mediaObserver){ mMediaObserverForMiPlayer = mediaObserver; }
    public static void setMediaObserverForMiniPlayer(MediaObserver mediaObserver){ mMediaObserverForMiniPlayer = mediaObserver; }

}
