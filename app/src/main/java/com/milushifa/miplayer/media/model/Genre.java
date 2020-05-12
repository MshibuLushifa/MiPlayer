package com.milushifa.miplayer.media.model;

public class Genre {
    public final long id;
    public final String genre;

    public Genre(long id, String genre) {
        this.id = id;
        this.genre = genre;
    }

    public Genre() {
        id = -1;
        genre = "";
    }
}
