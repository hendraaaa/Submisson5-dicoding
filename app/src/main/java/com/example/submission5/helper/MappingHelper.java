package com.example.submission5.helper;

import android.database.Cursor;

import com.example.submission5.db.DatabaseConstract;
import com.example.submission5.entity.Movies;

import java.util.ArrayList;

public class MappingHelper {
    public static ArrayList<Movies>mapCursorToArrayList(Cursor movieCursor){
        ArrayList<Movies>moviesList = new ArrayList<>();
        while (movieCursor.moveToNext()){
            int id = movieCursor.getInt(movieCursor.getColumnIndexOrThrow(DatabaseConstract.MovieColumns.MOVIE_ID));
            String title = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseConstract.MovieColumns.JUDUL));
            String description = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseConstract.MovieColumns.DESKRIPSI));
            String rilis = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseConstract.MovieColumns.RILIS));
            String gambar = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseConstract.MovieColumns.GAMBAR));
            String background = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseConstract.MovieColumns.BACKGROUND));
            String vote = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DatabaseConstract.MovieColumns.VOTE));
            moviesList.add(new Movies(id,title,description,rilis,gambar,background,vote));
        }
        return moviesList;
    }

}
