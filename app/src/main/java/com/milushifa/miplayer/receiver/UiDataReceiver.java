package com.milushifa.miplayer.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class UiDataReceiver extends BroadcastReceiver {
    private static UiUpdater mUiUpdater;
    @Override
    public void onReceive(Context context, Intent intent) {
        if(mUiUpdater!=null && mUiUpdater.isActivityActive()){
            if(ConstantsBroadcast.SEND_DURATION.equals(intent.getAction())){
                mUiUpdater.setDuration(intent.getIntExtra(ConstantsBroadcast.DURATION, 0));
            }else if(ConstantsBroadcast.SEND_CURRENT_POSITION.equals(intent.getAction())){
                mUiUpdater.setSeekProgress(intent.getIntExtra(ConstantsBroadcast.CURRENT_POSITION, 0));
            }
        }
    }

    public static void setUiUpdater(UiUpdater uiUpdater){
        mUiUpdater = uiUpdater;
    }
}
