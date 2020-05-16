package com.milushifa.miplayer.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.milushifa.miplayer.util.Flags;

public class UiDataReceiver extends BroadcastReceiver {
    private static UiUpdater mUiUpdater;
    private static MediaObserver mMediaObserverForMiPlayer;
    @Override
    public void onReceive(Context context, Intent intent) {
        if(mUiUpdater!=null && mUiUpdater.isActivityActive()){
            if(BroadcastAction.SEND_DURATION.equals(intent.getAction())){
                mUiUpdater.setDuration(intent.getIntExtra(BroadcastAction.DURATION, 0));
            }else if(BroadcastAction.SEND_CURRENT_POSITION.equals(intent.getAction())){
                mUiUpdater.setSeekProgress(intent.getIntExtra(BroadcastAction.CURRENT_POSITION, 0));
            }else if(BroadcastAction.SEND_PLAYING_STATUS.equals(intent.getAction())){
                mMediaObserverForMiPlayer.mediaChangePlayingStatus(intent.getBooleanExtra(BroadcastAction.PLAYING_STATUS, false));
            }else if(BroadcastAction.TRACK_CHANGE.equals(intent.getAction())){
                mUiUpdater.trackChange();
            }
        }
    }

    public static void setUiUpdater(UiUpdater uiUpdater){
        mUiUpdater = uiUpdater;
    }
    public static void setMediaObserver(MediaObserver mediaObserver){ mMediaObserverForMiPlayer = mediaObserver; }

}
