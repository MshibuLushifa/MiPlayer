package com.milushifa.miplayer.datasaver;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.milushifa.miplayer.media.model.LastPlayedTrack;
import com.milushifa.miplayer.media.model.Track;

import java.util.ArrayList;
import java.util.List;

public class CookieDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "last_record.db";
    private static final int VERSION = 1;
    private SQLiteDatabase db;
    public CookieDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_COOKIE_TABLE = "CREATE TABLE IF NOT EXISTS "
                + CookieDBContract.COOKIE_TABLE + " ( "
                + CookieDBContract.CookieColumns.TRACK_ID + " NUMBER PRIMARY KEY, "
                + CookieDBContract.CookieColumns.TRACK_TITLE + " TEXT, "
                + CookieDBContract.CookieColumns.ALBUM_ID + " TEXT, "
                + CookieDBContract.CookieColumns.ALBUM_NAME + " TEXT, "
                + CookieDBContract.CookieColumns.ARTIST_ID + " TEXT, "
                + CookieDBContract.CookieColumns.ARTIST_NAME + " TEXT, "
                + CookieDBContract.CookieColumns.DURATION + " NUMBER, "
                + CookieDBContract.CookieColumns.TRACK_NUMBER + " NUMBER"
                + " );";

        final String SQL_CREATE_LAST_RECORD_TABLE = "CREATE TABLE IF NOT EXISTS "
                + CookieDBContract.LAST_TRACK_TABLE + " ( "
                + CookieDBContract.LastTrackColumns.TRACK_ID + " NUMBER, "
                + CookieDBContract.LastTrackColumns.LAST_PLAYED_RECORD + " NUMBER "
                + " );";

        db.execSQL(SQL_CREATE_COOKIE_TABLE);
        db.execSQL(SQL_CREATE_LAST_RECORD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CookieDBContract.COOKIE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CookieDBContract.LAST_TRACK_TABLE);
    }

    public void newCookie(List<Track> tracks){
        String SQL_DELETE_STATEMENT = "DELETE FROM " + CookieDBContract.COOKIE_TABLE;
        db.execSQL(SQL_DELETE_STATEMENT);
        for(Track track:tracks){
            addTrack(track);
        }
    }
    private void addTrack(Track track){
        ContentValues cv = new ContentValues();
        cv.put(CookieDBContract.CookieColumns.TRACK_ID, track.id);
        cv.put(CookieDBContract.CookieColumns.TRACK_TITLE, track.title);
        cv.put(CookieDBContract.CookieColumns.ALBUM_ID, track.albumId);
        cv.put(CookieDBContract.CookieColumns.ALBUM_NAME, track.album);
        cv.put(CookieDBContract.CookieColumns.ARTIST_ID, track.artistId);
        cv.put(CookieDBContract.CookieColumns.ARTIST_NAME, track.artist);
        cv.put(CookieDBContract.CookieColumns.DURATION, track.duration);
        cv.put(CookieDBContract.CookieColumns.TRACK_NUMBER, track.trackNumber);
        this.db.insert(CookieDBContract.COOKIE_TABLE, null, cv);
    }

    public void lastTrackRecords(long track_id, int lastPlayedPosition){
        String SQL_DELETE_STATEMENT = "DELETE FROM " + CookieDBContract.LAST_TRACK_TABLE;
        db.execSQL(SQL_DELETE_STATEMENT);
        ContentValues cv = new ContentValues();
        cv.put(CookieDBContract.LastTrackColumns.TRACK_ID, track_id);
        cv.put(CookieDBContract.LastTrackColumns.LAST_PLAYED_RECORD, lastPlayedPosition);

        db.insert(CookieDBContract.LAST_TRACK_TABLE, null, cv);
    }

    public List<Track> getCookies(){
        List<Track> allTracks = new ArrayList<>();

        this.db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + CookieDBContract.COOKIE_TABLE, null);
        if(cursor!=null && cursor.moveToFirst()){
            do{
                allTracks.add(new Track(cursor.getLong(cursor.getColumnIndex(CookieDBContract.CookieColumns.TRACK_ID)),
                        cursor.getString(cursor.getColumnIndex(CookieDBContract.CookieColumns.TRACK_TITLE)),
                        cursor.getLong(cursor.getColumnIndex(CookieDBContract.CookieColumns.ALBUM_ID)),
                        cursor.getString(cursor.getColumnIndex(CookieDBContract.CookieColumns.ALBUM_NAME)),
                        cursor.getLong(cursor.getColumnIndex(CookieDBContract.CookieColumns.ARTIST_ID)),
                        cursor.getString(cursor.getColumnIndex(CookieDBContract.CookieColumns.ARTIST_NAME)),
                        cursor.getInt(cursor.getColumnIndex(CookieDBContract.CookieColumns.DURATION)),
                        cursor.getInt(cursor.getColumnIndex(CookieDBContract.CookieColumns.TRACK_NUMBER))));
            }while(cursor.moveToNext());
        }else{
            return null;
        }
        if(cursor!=null) cursor.close();
        return allTracks;
    }

    public LastPlayedTrack getLastTrack(){
        LastPlayedTrack lts;
        this.db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + CookieDBContract.LAST_TRACK_TABLE, null);
        if(cursor!=null && cursor.moveToFirst()){
            lts = new LastPlayedTrack(cursor.getLong(cursor.getColumnIndex(CookieDBContract.LastTrackColumns.TRACK_ID)),
                    cursor.getInt(cursor.getColumnIndex(CookieDBContract.LastTrackColumns.LAST_PLAYED_RECORD)));
        }else{
            return null;
        }
        if(cursor!=null) cursor.close();
        return lts;
    }
}
