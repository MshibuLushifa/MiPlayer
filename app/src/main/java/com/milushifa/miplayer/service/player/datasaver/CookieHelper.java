package com.milushifa.miplayer.service.player.datasaver;

import android.content.Context;

public class CookieHelper {
    private static CookieDBHelper mCookieDBHelper;

    public static CookieDBHelper getHelper(Context context){
        if(mCookieDBHelper == null){
            mCookieDBHelper = new CookieDBHelper(context);
        }
        return mCookieDBHelper;
    }
}
