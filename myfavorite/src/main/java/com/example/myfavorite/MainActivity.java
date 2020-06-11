package com.example.myfavorite;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.myfavorite.adapter.FavoriteMovieAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import static com.example.myfavorite.db.DatabaseConstract.CONTENT_URI;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private RecyclerView rvMovies;
    private FavoriteMovieAdapter adapter;
    private Cursor list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressFavoriteMovie);
        rvMovies = findViewById(R.id.recycleViewFavoriteMovie);

        adapter = new FavoriteMovieAdapter(list,this);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        rvMovies.setAdapter(adapter);
        new LoadFavouriteAsync().execute();

    }
    private void showSnackbarMessage(String message) {
        Snackbar.make(rvMovies, message, Snackbar.LENGTH_SHORT).show();
    }
    private class LoadFavouriteAsync extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor items) {
            super.onPostExecute(items);
            progressBar.setVisibility(View.GONE);

            list = items;
            adapter.setListMovies(list);

            if (list.getCount() == 0) {
                showSnackbarMessage("Tidak ada Data");
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        new LoadFavouriteAsync().execute();
    }
}
