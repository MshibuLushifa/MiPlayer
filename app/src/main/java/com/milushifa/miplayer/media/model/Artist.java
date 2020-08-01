package com.milushifa.miplayer.media.model;

public class Artist {
    public final long id;
    public final String artist;
    public final int albumCount;
    public final int songCount;

    public Artist(long id, String artist, int albumCount, int songCount) {
        this.id = id;
        this.artist = artist;
        this.albumCount = albumCount;
        this.songCount = songCount;
    }
}
