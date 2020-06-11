package com.example.submission5.Activity;

import android.appwidget.AppWidgetManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.submission5.R;
import com.example.submission5.adapter.FavoriteMovieAdapter;
import com.example.submission5.db.DatabaseConstract;
import com.example.submission5.db.MovieHelper;
import com.example.submission5.entity.Movies;
import com.example.submission5.helper.MappingHelper;
import com.example.submission5.widget.ImageMovieBannerWidget;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.submission5.db.DatabaseConstract.CONTENT_URI;
import static com.example.submission5.db.DatabaseConstract.MovieColumns.BACKGROUND;
import static com.example.submission5.db.DatabaseConstract.MovieColumns.DESKRIPSI;
import static com.example.submission5.db.DatabaseConstract.MovieColumns.GAMBAR;
import static com.example.submission5.db.DatabaseConstract.MovieColumns.JUDUL;
import static com.example.submission5.db.DatabaseConstract.MovieColumns.MOVIE_ID;
import static com.example.submission5.db.DatabaseConstract.MovieColumns.RILIS;
import static com.example.submission5.db.DatabaseConstract.MovieColumns.VOTE;
import static com.example.submission5.db.DatabaseConstract.getColumnString;

public class DetailMoviesActivity extends AppCompatActivity {
    public static final String EXTRA_DATA = "extra_data";
    private ImageView imageViewPoster, imageViewBackground;
    private TextView tvtitle, tvRilis, tvDesc;
    private Movies movies;
    private Uri uri;
    private Menu menu;
    boolean isFavorite = false;
    private AppWidgetManager manager;
    public final static String MOVIE_ID = "movie_id";
    public static String LOCAL_STATUS = "local_status";
    private int movie_Id;
    private String check;


    Context mcontex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movies);
        Toolbar toolbar = findViewById(R.id.toolbardetailMovie);
        toolbar.setTitle(getResources().getString(R.string.detail));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageViewPoster = findViewById(R.id.imagePosterDetailMovies);
        imageViewBackground = findViewById(R.id.imageBackdropDetailMovies);
        tvtitle = findViewById(R.id.tvTitle_detailMovie);
        tvRilis = findViewById(R.id.tv_release_detailMovie);
        tvDesc = findViewById(R.id.tvDescDetailMovie);
        uri = getIntent().getData();
        movie_Id = getIntent().getIntExtra(MOVIE_ID,movie_Id);
        check = getIntent().getStringExtra(LOCAL_STATUS);
        movies = getIntent().getParcelableExtra(EXTRA_DATA);
        detail();
        checkFavorite();


    }

    private void detail() {

                tvtitle.setText(movies.getTitleMovie());
                tvDesc.setText(movies.getDescMovie());
                tvRilis.setText(movies.getReleaseDateMovie());
                String url_image = "https://image.tmdb.org/t/p/w185" + movies.getPosterMovie();
                String url_imageBack = "https://image.tmdb.org/t/p/w185" + movies.getBackdropMovie();

                Glide.with(this)
                        .load(url_image)
                        .into(imageViewPoster);
                Glide.with(this)
                        .load(url_imageBack)
                        .into(imageViewBackground);


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
        update(this);
        Toast.makeText(DetailMoviesActivity.this,getResources().getString(R.string.tambah),Toast.LENGTH_SHORT).show();



    }
    private void deleteFavorite(){
        Uri uriWithId = Uri.parse(CONTENT_URI + "/" + movies.getIdMovie());

        getContentResolver().delete(uriWithId,null,null);
        update(this);
        Toast.makeText(DetailMoviesActivity.this,getResources().getString(R.string.hapus),Toast.LENGTH_SHORT).show();

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

    private void favoriteButtonPressed() {
        if (isFavorite) {
           deleteFavorite();



        } else {
            addFavourite();


        }
        isFavorite = !isFavorite;
        setIconFavorite();
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
    private void update(Context context){
        Intent i = new Intent(context, ImageMovieBannerWidget.class);
        i.setAction(ImageMovieBannerWidget.UPDATE_WIDGET);
        context.sendBroadcast(i);

    }




}
