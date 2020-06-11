package com.example.myfavorite.helper;

import android.database.Cursor;


import com.example.myfavorite.db.DatabaseConstract;
import com.example.myfavorite.entity.Movies;

import java.util.ArrayList;

public class MappingHelper {
    public static ArrayList<Movies>mapCursorToArrayList(Cursor movieCursor){
        ArrayList<Movies>moviesList = new ArrayList<>();
        while (movieCursor.moveToNext()){
            int id = movieCursor.getInt(movieCursor.getColumnIndexOrThrow(DatabaseConstract.MovieColumns._ID));
            String title = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseConstract.MovieColumns.JUDUL));
            String description = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseConstract.MovieColumns.DESKRIPSI));
            String rilis = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseConstract.MovieColumns.RILIS));
            String gambar = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseConstract.MovieColumns.GAMBAR));
            String vote = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseConstract.MovieColumns.VOTE));
            moviesList.add(new Movies(id,title,description,rilis,gambar,vote));
        }
        return moviesList;
    }

}
