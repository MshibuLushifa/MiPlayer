package com.milushifa.miplayer.ui.fragment.tfragment.backstack;

import android.util.Log;

import com.milushifa.miplayer.util.Flags;

public class BackStack {
    private static String[] backStack  = new String[20];
    private static int stackTrace = -1;


    public static String popBackStack(){
        int currentTrace = --stackTrace;
        if(currentTrace>-1 && currentTrace<20){
            String fragmentType = backStack[currentTrace];
            Log.i(Flags.TAG, "popBackStack: popped: " + fragmentType + " stackTrace: " + stackTrace);
            return fragmentType;
        }
        return null;
    }

    public static void pushBackStack(String fragmentType){
       if(stackTrace>=-1 && stackTrace<20){
           backStack[++stackTrace] = fragmentType;
           Log.i(Flags.TAG, "pushBackStack: pushed: " + fragmentType + " stackTrace: " + stackTrace);
       }
    }
    public static void resetBackStack(){
        stackTrace = -1;
    }
}
