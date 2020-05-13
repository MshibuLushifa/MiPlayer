package com.milushifa.miplayer.receiver;

public interface UiUpdater{
    boolean isActivityActive();
    void setDuration(int duration);
    void setSeekProgress(int progress);
}
