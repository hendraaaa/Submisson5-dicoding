package com.example.submission5.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.submission5.entity.Movies;

import static android.provider.BaseColumns._ID;
import static com.example.submission5.db.DatabaseConstract.MovieColumns.MOVIE_ID;
import static com.example.submission5.db.DatabaseConstract.TABEL;


public class MovieHelper {
    private static String DATABASE_TABLE = TABEL;
    private Context mContext;
    private DatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mDatabase;
    private static MovieHelper INSTANCE;
    private Context context;

    public MovieHelper(Context mContext){
        this.mContext = mContext;
    }
    public static MovieHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieHelper(context);
                }
            }
        }
        return INSTANCE;
    }
    public void open() throws SQLException{
        mDatabaseHelper = new DatabaseHelper(mContext);
        mDatabase = mDatabaseHelper.getWritableDatabase();
    }
   /* public void close(){
        mDatabaseHelper.close();
    }*/
    public long insertProvider(ContentValues contentValues){
        return mDatabase.insert(DATABASE_TABLE, null, contentValues);
    }
    public Cursor queryByIdProvider(String id){
        return mDatabase.query(DATABASE_TABLE,
                null,MOVIE_ID+"=?",new String[]{id},null,null,null,null);
    }
    public int updateProvider(String id, ContentValues contentValues){
        return mDatabase.update(DATABASE_TABLE,contentValues,_ID+"=?",new String[]{id});
    }
    public int deleteProvider(String id){
        return mDatabase.delete(DATABASE_TABLE,MOVIE_ID+"=?",new String[]{id});
    }
    public Cursor queryProvider(){
        return mDatabase.query(DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID+" DESC");
    }
}
