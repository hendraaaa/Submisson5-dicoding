package com.example.submission5.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.example.submission5.db.DatabaseConstract;
import com.example.submission5.db.MovieHelper;

import static com.example.submission5.db.DatabaseConstract.AUTHORITY;
import static com.example.submission5.db.DatabaseConstract.CONTENT_URI;

public class MovieProvider extends ContentProvider {
    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;
    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    static{
        URI_MATCHER.addURI(AUTHORITY, DatabaseConstract.TABEL,MOVIE);
        URI_MATCHER.addURI(AUTHORITY,DatabaseConstract.TABEL+"/#",MOVIE_ID);
    }
    private MovieHelper movieHelper;

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int deleted;
        switch (URI_MATCHER.match(uri)){
            case MOVIE_ID:
                deleted = movieHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }
        if (deleted>0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return deleted;
    }

    @Override
    public String getType(Uri uri) {
       return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
       long added;
       switch (URI_MATCHER.match(uri)){
           case MOVIE:
               added = movieHelper.insertProvider(values);
               break;
           default:
               added = 0;
               break;
       }
       if (added > 0){
           getContext().getContentResolver().notifyChange(CONTENT_URI,null);
       }
       return Uri.parse(CONTENT_URI+"/"+added);
    }

    @Override
    public boolean onCreate() {
        movieHelper = MovieHelper.getInstance(getContext());
        movieHelper = new MovieHelper(getContext());
        movieHelper.open();

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor;
        switch (URI_MATCHER.match(uri)){
            case MOVIE:

                cursor = movieHelper.queryProvider();

                break;
            case MOVIE_ID:

                cursor = movieHelper.queryByIdProvider(uri.getLastPathSegment());

                break;
            default:

                cursor = null;

                break;
        }
        if (cursor!=null){

            cursor.setNotificationUri(getContext().getContentResolver(),uri);
        }
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int updated;
        switch (URI_MATCHER.match(uri)){
            case MOVIE_ID:
                updated = movieHelper.updateProvider(uri.getLastPathSegment(),values);
                break;
            default:
                updated = 0;
                break;
        }
        if (updated > 0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return updated;
    }
}
