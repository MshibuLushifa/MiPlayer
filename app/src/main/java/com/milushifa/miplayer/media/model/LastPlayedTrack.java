package com.milushifa.miplayer.media.model;

public class LastPlayedTrack {
    public final long track_id;
    public final int positionInList;

    public LastPlayedTrack(){
        track_id = -1;
        positionInList = 0;
    }

    public LastPlayedTrack(long track_id, int positionInList) {
        this.track_id = track_id;
        this.positionInList = positionInList;
    }
}
