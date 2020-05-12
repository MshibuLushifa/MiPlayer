package com.milushifa.miplayer.media.loader;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.milushifa.miplayer.media.model.Genre;

import java.util.ArrayList;
import java.util.List;

public class GenreLoader {

    public List<Genre> getAllGenre(Context context){
        List<Genre> genreList = new ArrayList<>();
        Cursor cursor = makeCursor(context, null, null);
        if(cursor!=null && cursor.moveToFirst()){
            do{
                genreList.add(new Genre(cursor.getLong(0),
                        cursor.getString(1)));
            }while(cursor.moveToNext());
            cursor.close();
        }
        return genreList;
    }

    public Genre getGenreById(Context context, long genreId){
        Genre genre = new Genre();
        Cursor cursor = makeCursor(context, "_id = ?", new String[]{String.valueOf(genreId)});
        if(cursor!=null && cursor.moveToFirst()){
            genre = new Genre(cursor.getLong(0), cursor.getString(1));
        }
        return genre;
    }

    private Cursor makeCursor(Context context, String selection, String[] selectionArgs){
        Uri uri = MediaStore.Audio.Genres.EXTERNAL_CONTENT_URI;
        String []mProjection = new String[]{
                MediaStore.Audio.Genres._ID,
                MediaStore.Audio.GenresColumns.NAME
        };
        String mSortOrder = MediaStore.Audio.Genres.DEFAULT_SORT_ORDER;
        Cursor cursor = context.getContentResolver().query(uri, mProjection, selection, selectionArgs, mSortOrder);
        return cursor;
    }
}
