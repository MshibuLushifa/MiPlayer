package com.milushifa.miplayer.datasaver;

public class CookieDBContract {

    public static final String COOKIE_TABLE = "cookie_table";
    public static final String LAST_TRACK_TABLE = "last_track";


    public static class CookieColumns{
        public static final String TRACK_ID = "track_id";
        public static final String TRACK_TITLE = "track_title";
        public static final String ALBUM_ID = "album_id";
        public static final String ALBUM_NAME = "album_name";
        public static final String ARTIST_ID = "artist_id";
        public static final String ARTIST_NAME = "artist_name";
        public static final String DURATION = "duration";
        public static final String TRACK_NUMBER = "track_number";
    }

    public static class LastTrackColumns{
        public static final String TRACK_ID = "track_id";
        public static final String LAST_PLAYED_RECORD = "last_played_record";
    }
}
