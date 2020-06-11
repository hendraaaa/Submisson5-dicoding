package com.example.submission5.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.submission5.R;
import com.example.submission5.db.DatabaseConstract;
import com.example.submission5.entity.Movies;
import com.example.submission5.helper.MappingHelper;

import java.util.ArrayList;

import static com.example.submission5.db.DatabaseConstract.CONTENT_URI;
import static com.example.submission5.db.DatabaseConstract.MovieColumns.BACKGROUND;
import static com.example.submission5.db.DatabaseConstract.MovieColumns.DESKRIPSI;
import static com.example.submission5.db.DatabaseConstract.MovieColumns.GAMBAR;
import static com.example.submission5.db.DatabaseConstract.MovieColumns.JUDUL;
import static com.example.submission5.db.DatabaseConstract.MovieColumns.RILIS;
import static com.example.submission5.db.DatabaseConstract.MovieColumns.VOTE;
import static com.example.submission5.db.DatabaseConstract.getColumnString;

public class ActivityDetailFavorite extends AppCompatActivity {
    private ImageView imageViewPoster, imageViewBackground;
    private TextView tvtitle, tvRilis, tvDesc;
    private Movies movies;
   // private Uri uri;
    private Menu menu;
    private int movie_Id;
    public final static String MOVIE_ID = "movie_id";
    boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_favorite);
        Toolbar toolbar = findViewById(R.id.toolbardetailFavorite);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.detail));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imageViewPoster = findViewById(R.id.imagePosterDetailFavorite);
        imageViewBackground = findViewById(R.id.imageBackdropDetailFavorite);
        tvtitle = findViewById(R.id.tvTitle_detailFavorite);
        tvRilis = findViewById(R.id.tv_release_detailfavorite);
        tvDesc = findViewById(R.id.tvDescDetailFavorite);
        movie_Id = getIntent().getIntExtra(MOVIE_ID,movie_Id);
        getMovieSqlite();
        isFavorite = false;
        checkFavorite();


    }

    private void getMovieSqlite() {
        Uri uri = Uri.parse(CONTENT_URI+"/"+movie_Id);

        Cursor cursor = getContentResolver().query(uri,
                null,
                null,
                null,
                null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                movies = new Movies(cursor);

               tvtitle.setText(movies.getTitleMovie());
               tvDesc.setText(movies.getDescMovie());
               tvRilis.setText(movies.getReleaseDateMovie());
               String gambar = "https://image.tmdb.org/t/p/w185"+movies.getPosterMovie();
               String backGround = "https://image.tmdb.org/t/p/w185"+movies.getBackdropMovie();


                Glide.with(this)
                        .load(gambar)
                        .into(imageViewPoster);

                Glide.with(this)
                        .load(backGround)
                        .into(imageViewBackground);


            }
            cursor.close();
        }

    }
    private void loadSqliteData() {
        Cursor cursor = getContentResolver().query(Uri.parse(CONTENT_URI + "/" + movie_Id),
                null,
                null,
                null,
                null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String idIntent = "" + movie_Id;
                String movieId = getColumnString(cursor, DatabaseConstract.MovieColumns.MOVIE_ID);
                if (movieId.equals(idIntent)){
                    isFavorite = true;
                }
            }
            cursor.close();
        } else{
            isFavorite = false;
        }
       setIconFavorite();
    }
    private void checkFavorite() {

        Cursor dataCursor = getContentResolver().query(DatabaseConstract.CONTENT_URI, null, null, null, null);

        ArrayList<Movies> moviesInDatabase = MappingHelper.mapCursorToArrayList(dataCursor);
        for (Movies movies : moviesInDatabase) {
            if (this.movies.getIdMovie() == movies.getIdMovie()) {
                isFavorite = true;
            }
            if (isFavorite == true) {
                break;
            }
        }
    }
    private void deleteFavorite(){
        Uri uriWithId = Uri.parse(CONTENT_URI + "/" + movie_Id);

        getContentResolver().delete(uriWithId,null,null);
       // update(this);
        Toast.makeText(ActivityDetailFavorite.this,getResources().getString(R.string.hapus),Toast.LENGTH_SHORT).show();

    }
    private void addFavourite() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MOVIE_ID,movies.getIdMovie());
        contentValues.put(JUDUL, movies.getTitleMovie());
        contentValues.put(DESKRIPSI, movies.getDescMovie());
        contentValues.put(RILIS, movies.getReleaseDateMovie());
        contentValues.put(GAMBAR, movies.getPosterMovie());
        contentValues.put(BACKGROUND,movies.getBackdropMovie());
        contentValues.put(VOTE,movies.getVoteMovie());
        getContentResolver().insert(CONTENT_URI,contentValues);
      //  update(this);
        Toast.makeText(ActivityDetailFavorite.this,getResources().getString(R.string.tambah),Toast.LENGTH_SHORT).show();



    }
    private void setIconFavorite(){
        if (isFavorite) {
            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.heart));
        } else {
            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.heart1));
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        this.menu = menu;

        setIconFavorite();
        return super.onCreateOptionsMenu(menu);

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }else if (item.getItemId() == R.id.love){
            favoriteButtonPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    private void favoriteButtonPressed() {
        if (isFavorite) {
            deleteFavorite();



        } else {
            addFavourite();


        }
        isFavorite = !isFavorite;
        setIconFavorite();
    }
}
