package com.milushifa.miplayer.media.loader;


import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.milushifa.miplayer.media.model.Album;

import java.util.ArrayList;
import java.util.List;

public class AlbumLoader {

    public List<Album> getAllAlbums(Context context){
        List<Album> albumList = new ArrayList<>();
        Cursor cursor = makeCursor(context, null, null);

        if(cursor!=null && cursor.moveToFirst()){
            do{
                albumList.add(new Album(cursor.getLong(0),
                        cursor.getString(1),
                        cursor.getLong(2),
                        cursor.getString(3),
                        cursor.getInt(4),
                        cursor.getInt(5)));
            }while(cursor.moveToNext());
            cursor.close();
        }
        return albumList;
    }

    private Album getAlbumByAlbumId(Context context, long albumId){
        Album album = new Album();
        Cursor cursor = makeCursor(context, "_id = ?", new String[]{String.valueOf(albumId)});
        if(cursor!=null && cursor.moveToFirst()){
            album = new Album(cursor.getLong(0),
                    cursor.getString(1),
                    cursor.getLong(2),
                    cursor.getString(3),
                    cursor.getInt(4),
                    cursor.getInt(5));
            cursor.close();
        }
        return album;
    }


    private Cursor makeCursor(Context context, String selection, String []selectArgs){
        Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        String []mProjection = new String[]{
                MediaStore.Audio.Albums._ID,
                MediaStore.Audio.AlbumColumns.ALBUM,
                MediaStore.Audio.AlbumColumns.ARTIST_ID,
                MediaStore.Audio.AlbumColumns.ARTIST,
                MediaStore.Audio.AlbumColumns.NUMBER_OF_SONGS,
                MediaStore.Audio.AlbumColumns.FIRST_YEAR
        };
        String mSortOrder = MediaStore.Audio.Albums.DEFAULT_SORT_ORDER;
        Cursor cursor = context.getContentResolver().query(uri, mProjection, selection, selectArgs, mSortOrder);
        return cursor;
    }
}
