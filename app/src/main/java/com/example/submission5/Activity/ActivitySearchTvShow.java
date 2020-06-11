package com.example.submission5.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.submission5.R;
import com.example.submission5.adapter.MovieAdapter;
import com.example.submission5.entity.Movies;
import com.example.submission5.model.TvShowModel;

import java.util.ArrayList;

public class ActivitySearchTvShow extends AppCompatActivity {

    private MovieAdapter adapter;
    private TvShowModel tvShowModel;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_tv_show);
        Toolbar toolbar = findViewById(R.id.toolbarSearchTvShow);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Cari");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        recyclerView = findViewById(R.id.recycleViewSearchTvShow);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        adapter = new MovieAdapter();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        Bundle bundle = getIntent().getExtras();
        String dataTvShow = bundle.getString("tvShow");
        tvShowModel = new ViewModelProvider(this).get(TvShowModel.class);
        tvShowModel.setSearchTvShow(dataTvShow);

        tvShowModel.getSearchTvShow().observe(this, new Observer<ArrayList<Movies>>() {
            @Override
            public void onChanged(ArrayList<Movies> movies) {
                if (movies != null){
                    adapter.setData(movies);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
