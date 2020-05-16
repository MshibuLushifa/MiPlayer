package com.milushifa.miplayer.media.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.milushifa.miplayer.util.Flags;

public class Track {
    public final long id;
    public final String title;
    public final long albumId;
    public final String album;
    public final long artistId;
    public final String artist;
    public final int duration;
    public final int trackNumber;

    public Track(long id, String title, long albumId, String albumName, long artistId, String artistName, int duration, int trackNumber) {
        this.id = id;
        this.title = title;
        this.albumId = albumId;
        this.album = albumName;
        this.artistId = artistId;
        this.artist = artistName;
        this.duration = duration;
        this.trackNumber = trackNumber;
    }

    public Track() {
        id = -1;
        title = "";
        albumId = -1;
        album = "";
        artistId = -1;
        artist = "";
        duration = -1;
        trackNumber = -1;
    }

    public Track(long id, String title, long albumId, String albumName, long artistId, String artistName, int duration){
        this.id = id;
        this.title = title;
        this.albumId = albumId;
        this.album = albumName;
        this.artistId = artistId;
        this.artist = artistName;
        this.duration = duration;
        this.trackNumber = -1;
    }

    @NonNull
    @Override
    public String toString() {
        return "data-> id: " + id + ", title: " + title + ", albumId: " + albumId;
    }
}
