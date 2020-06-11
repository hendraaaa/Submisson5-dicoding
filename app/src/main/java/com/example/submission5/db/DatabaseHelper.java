package com.example.submission5.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static com.example.submission5.db.DatabaseConstract.TABEL;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME ="dbmovie";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_TABEL = String.format("CREATE TABLE %s"+
                    "(%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL,"+
                    "%s TEXT NOT NULL)",
            DatabaseConstract.TABEL,
            DatabaseConstract.MovieColumns._ID,
            DatabaseConstract.MovieColumns.MOVIE_ID,
            DatabaseConstract.MovieColumns.JUDUL,
            DatabaseConstract.MovieColumns.DESKRIPSI,
            DatabaseConstract.MovieColumns.RILIS,
            DatabaseConstract.MovieColumns.GAMBAR,
            DatabaseConstract.MovieColumns.BACKGROUND,
            DatabaseConstract.MovieColumns.VOTE
    );


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABEL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABEL);
        onCreate(sqLiteDatabase);

    }
    DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
}
