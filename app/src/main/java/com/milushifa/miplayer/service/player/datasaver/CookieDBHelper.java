package com.milushifa.miplayer.service.player.datasaver;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.Nullable;

import com.milushifa.miplayer.media.model.Track;
import com.milushifa.miplayer.util.Flags;

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

        db.execSQL(SQL_CREATE_COOKIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CookieDBContract.COOKIE_TABLE);
    }

    public List<Track> getCookies(){
        List<Track> allTracks = new ArrayList<>();

        this.db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + CookieDBContract.COOKIE_TABLE, null);
        if(cursor!=null && cursor.moveToFirst()){
            do{
                Track track = new Track(cursor.getLong(cursor.getColumnIndex(CookieDBContract.CookieColumns.TRACK_ID)),
                        cursor.getString(cursor.getColumnIndex(CookieDBContract.CookieColumns.TRACK_TITLE)),
                        cursor.getLong(cursor.getColumnIndex(CookieDBContract.CookieColumns.ALBUM_ID)),
                        cursor.getString(cursor.getColumnIndex(CookieDBContract.CookieColumns.ALBUM_NAME)),
                        cursor.getLong(cursor.getColumnIndex(CookieDBContract.CookieColumns.ARTIST_ID)),
                        cursor.getString(cursor.getColumnIndex(CookieDBContract.CookieColumns.ARTIST_NAME)),
                        cursor.getInt(cursor.getColumnIndex(CookieDBContract.CookieColumns.DURATION)),
                        cursor.getInt(cursor.getColumnIndex(CookieDBContract.CookieColumns.TRACK_NUMBER)));
                allTracks.add(track);
            }while(cursor.moveToNext());
        }else{
            return null;
        }
        if(cursor!=null) cursor.close();
        return allTracks;
    }



    public class StoreData extends AsyncTask<List<Track>, Void, Void> {

        @Override
        protected Void doInBackground(List<Track>... tracks) {
            String SQL_DELETE_STATEMENT = "DELETE FROM " + CookieDBContract.COOKIE_TABLE;
            db.execSQL(SQL_DELETE_STATEMENT);
            for(Track track:tracks[0]){
                addTrack(track);
            }
            return null;
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
            db.insert(CookieDBContract.COOKIE_TABLE, null, cv);
        }
    }
    public void storeThisAsCookie(List<Track> tracks){
        new StoreData().execute(tracks);
    }



}
