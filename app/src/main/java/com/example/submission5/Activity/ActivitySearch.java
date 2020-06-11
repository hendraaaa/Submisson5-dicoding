package com.example.submission5.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.submission5.R;
import com.example.submission5.adapter.MovieAdapter;
import com.example.submission5.entity.Movies;
import com.example.submission5.model.MovieModel;
import com.example.submission5.model.TvShowModel;

import java.util.ArrayList;

public class ActivitySearch extends AppCompatActivity {
    private MovieAdapter adapter;
    private MovieModel movieModel;
    private RecyclerView recyclerView;
    private int status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = findViewById(R.id.toolbarSearch);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Cari");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        recyclerView = findViewById(R.id.recycleViewSearch);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        adapter = new MovieAdapter();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            status = bundle.getInt("status");
            if (status == 1){
                String dataMovie = bundle.getString("movie");
                movieModel = new ViewModelProvider(this).get(MovieModel.class);
                movieModel.setMoviesearch(dataMovie);

                movieModel.getMoviesearch().observe(this, new Observer<ArrayList<Movies>>() {
                    @Override
                    public void onChanged(ArrayList<Movies> movies) {
                        if (movies != null){
                            adapter.setData(movies);
                        }
                    }
                });
            }


        }

         //searchTvShow();



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);

    }
    private void searchMovie(){


    }
    private void searchTvShow(){


    }
}
