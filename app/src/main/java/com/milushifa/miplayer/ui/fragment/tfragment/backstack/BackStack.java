package com.milushifa.miplayer.ui.fragment.tfragment.backstack;

public class BackStack {
    private static String[] backStack  = new String[20];
    private static int stackTrace = -1;


    public static String popBackStack(){
        int currentTrace = --stackTrace;
        if(currentTrace>-1 && currentTrace<20){
            String fragmentType = backStack[currentTrace];
            return fragmentType;
        }
        return null;
    }

    public static void pushBackStack(String fragmentType){
       if(stackTrace>=-1 && stackTrace<20){
           backStack[++stackTrace] = fragmentType;
       }
    }
    public static void resetBackStack(){
        stackTrace = -1;
    }
}
