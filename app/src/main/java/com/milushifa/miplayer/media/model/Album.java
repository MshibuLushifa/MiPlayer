package com.milushifa.miplayer.media.model;

public class Album {
    public final long id;
    public final String album;
    public final long artistId;
    public final String artist;
    public final int numberOfSong;
    public final int year;

    public Album(long id, String albumName, long artistId, String artistName, int numberOfSong, int year) {
        this.id = id;
        this.album = albumName;
        this.artistId = artistId;
        this.artist = artistName;
        this.numberOfSong = numberOfSong;
        this.year = year;
    }

}