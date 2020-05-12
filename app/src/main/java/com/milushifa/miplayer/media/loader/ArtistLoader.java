package com.milushifa.miplayer.media.loader;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.milushifa.miplayer.media.model.Artist;

import java.util.ArrayList;
import java.util.List;

public class ArtistLoader {

    public List<Artist> getAllArtist(Context context){
        List<Artist> artistList = new ArrayList<>();

        Cursor cursor = makeCursor(context, null, null);
        if(cursor!=null&&cursor.moveToFirst()){
            do{
                artistList.add(new Artist(
                        cursor.getLong(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getInt(3)
                ));
            }while(cursor.moveToNext());
        }
        if(cursor!=null){
            cursor.close();
        }

        return artistList;
    }
    public Artist getArtistById(Context context, long artistId){
        Artist artist = new Artist();
        Cursor cursor = makeCursor(context, "_id = ?", new String[]{String.valueOf(artistId)});
        if(cursor!=null&&cursor.moveToFirst()){
            artist = new Artist(cursor.getLong(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getInt(3));
            cursor.close();
        }
        return artist;
    }

    private static Cursor makeCursor(Context context, String selection, String[] selectionArgs){
        Uri uri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
        String[] mProjection = new String[]{
                MediaStore.Audio.Artists._ID,
                MediaStore.Audio.Artists.ARTIST,
                MediaStore.Audio.Artists.NUMBER_OF_ALBUMS,
                MediaStore.Audio.Artists.NUMBER_OF_TRACKS
        };
        String sortOrder = MediaStore.Audio.Artists.DEFAULT_SORT_ORDER;
        Cursor cursor = context.getContentResolver().query(uri, mProjection, selection, selectionArgs, sortOrder);
        return cursor;
    }
}
