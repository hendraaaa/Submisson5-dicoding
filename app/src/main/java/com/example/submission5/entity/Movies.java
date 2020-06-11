package com.example.submission5.entity;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.submission5.db.DatabaseConstract;

import static android.provider.BaseColumns._ID;
import static com.example.submission5.db.DatabaseConstract.MovieColumns.BACKGROUND;
import static com.example.submission5.db.DatabaseConstract.MovieColumns.DESKRIPSI;
import static com.example.submission5.db.DatabaseConstract.MovieColumns.GAMBAR;
import static com.example.submission5.db.DatabaseConstract.MovieColumns.JUDUL;
import static com.example.submission5.db.DatabaseConstract.MovieColumns.MOVIE_ID;
import static com.example.submission5.db.DatabaseConstract.MovieColumns.RILIS;
import static com.example.submission5.db.DatabaseConstract.MovieColumns.VOTE;

public class Movies implements Parcelable {
    private int idMovie;
    private String titleMovie;
    private String descMovie;
    private String releaseDateMovie;
    private String posterMovie;
    private String backdropMovie;
    private String voteMovie;

    public Movies(Parcel in) {
        idMovie = in.readInt();
        titleMovie = in.readString();
        descMovie = in.readString();
        releaseDateMovie = in.readString();
        posterMovie = in.readString();
        backdropMovie = in.readString();
        voteMovie = in.readString();
    }

    public static final Creator<Movies> CREATOR = new Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel in) {
            return new Movies(in);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };

    public int getIdMovie() {
        return idMovie;
    }

    public void setIdMovie(int idMovie) {
        this.idMovie = idMovie;
    }

    public String getTitleMovie() {
        return titleMovie;
    }

    public void setTitleMovie(String titleMovie) {
        this.titleMovie = titleMovie;
    }

    public String getDescMovie() {
        return descMovie;
    }

    public void setDescMovie(String descMovie) {
        this.descMovie = descMovie;
    }

    public String getReleaseDateMovie() {
        return releaseDateMovie;
    }

    public void setReleaseDateMovie(String releaseDateMovie) {
        this.releaseDateMovie = releaseDateMovie;
    }

    public String getPosterMovie() {
        return posterMovie;
    }

    public void setPosterMovie(String posterMovie) {
        this.posterMovie = posterMovie;
    }

    public String getBackdropMovie() {
        return backdropMovie;
    }

    public void setBackdropMovie(String backdropMovie) {
        this.backdropMovie = backdropMovie;
    }

    public String getVoteMovie() {
        return voteMovie;
    }

    public void setVoteMovie(String voteMovie) {
        this.voteMovie = voteMovie;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(idMovie);
        parcel.writeString(titleMovie);
        parcel.writeString(descMovie);
        parcel.writeString(releaseDateMovie);
        parcel.writeString(posterMovie);
        parcel.writeString(backdropMovie);
        parcel.writeString(voteMovie);
    }
   public Movies(){

    }

    public Movies(int idMovie,String titleMovie,String descMovie,String releaseDateMovie,String posterMovie,String backdropMovie,String voteMovie){
        this.idMovie = idMovie;
        this.titleMovie = titleMovie;
        this.descMovie = descMovie;
        this.releaseDateMovie = releaseDateMovie;
        this.posterMovie = posterMovie;
        this.backdropMovie = backdropMovie;
        this.voteMovie = voteMovie;
    }
   public Movies(Cursor cursor){
       this.idMovie = DatabaseConstract.getColumnInt(cursor,MOVIE_ID);
       this.titleMovie = DatabaseConstract.getColumnString(cursor, JUDUL);
       this.descMovie = DatabaseConstract.getColumnString(cursor, DESKRIPSI);
       this.releaseDateMovie = DatabaseConstract.getColumnString(cursor, RILIS);
       this.posterMovie = DatabaseConstract.getColumnString(cursor, GAMBAR);
       this.voteMovie = DatabaseConstract.getColumnString(cursor,VOTE);
       this.backdropMovie = DatabaseConstract.getColumnString(cursor,BACKGROUND);
   }
}
