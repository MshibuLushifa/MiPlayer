package com.milushifa.miplayer.media.loader;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.milushifa.miplayer.media.model.Track;

import java.util.ArrayList;
import java.util.List;

public class TracksLoader {
    public List<Track> getAllTracks(Context context){
        List<Track> trackList = new ArrayList<>();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] mProjection = new String[]{
                MediaStore.Audio.AudioColumns._ID,
                MediaStore.Audio.AudioColumns.TITLE,
                MediaStore.Audio.AudioColumns.ALBUM_ID,
                MediaStore.Audio.AudioColumns.ALBUM,
                MediaStore.Audio.AudioColumns.ARTIST_ID,
                MediaStore.Audio.AudioColumns.ARTIST,
                MediaStore.Audio.AudioColumns.DURATION,
                MediaStore.Audio.AudioColumns.TRACK,
        };
        String mSortOrder = MediaStore.Audio.Media.DEFAULT_SORT_ORDER;

        Cursor cursor = context.getContentResolver().query(uri, mProjection, null, null, mSortOrder);

        if(cursor!=null && cursor.moveToFirst()){
            do{
                trackList.add(new Track(cursor.getLong(0),
                        cursor.getString(1),
                        cursor.getLong(2),
                        cursor.getString(3),
                        cursor.getLong(4),
                        cursor.getString(5),
                        cursor.getInt(6),
                        cursor.getInt(7)));
            }while(cursor.moveToNext());
            cursor.close();
        }
        return trackList;
    }


    public List<Track> getAllSongsFromAlbum(Context context, long album_id){
        List<Track> songList = new ArrayList<>();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] mProjection = new String[]{
                MediaStore.Audio.AudioColumns._ID,
                MediaStore.Audio.AudioColumns.TITLE,
                MediaStore.Audio.AudioColumns.ALBUM_ID,
                MediaStore.Audio.AudioColumns.ALBUM,
                MediaStore.Audio.AudioColumns.ARTIST_ID,
                MediaStore.Audio.AudioColumns.ARTIST,
                MediaStore.Audio.AudioColumns.DURATION,
                MediaStore.Audio.AudioColumns.TRACK
        };

        String selection = "is_music=1 and title != '' and album_id=" + album_id;

        String mSortOrder = MediaStore.Audio.Media.DEFAULT_SORT_ORDER;

        Cursor cursor = context.getContentResolver().query(uri, mProjection, selection, null, mSortOrder);

        if(cursor!=null && cursor.moveToFirst()){
            do{
                int trackNumbers = cursor.getInt(7);
                while(trackNumbers>=1000){
                    trackNumbers-=1000;
                }
                songList.add(new Track(
                        cursor.getLong(0),
                        cursor.getString(1),
                        cursor.getLong(2),
                        cursor.getString(3),
                        cursor.getLong(4),
                        cursor.getString(5),
                        cursor.getInt(6),
                        trackNumbers
                ));
            }while(cursor.moveToNext());
            if(cursor!=null){
                cursor.close();
            }
        }
        return songList;
    }
}
