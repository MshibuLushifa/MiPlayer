package com.milushifa.miplayer.service;

import android.content.Context;
import android.content.Intent;

public class ServiceProvider {

    private static Intent MiServiceIntent;

    public static Intent getMiServiceIntent(Context context, String action){
        if(MiServiceIntent==null){
            MiServiceIntent = new Intent(context, MiPlayerService.class);
        }
        MiServiceIntent.setAction(action);
        return MiServiceIntent;
    }

}
