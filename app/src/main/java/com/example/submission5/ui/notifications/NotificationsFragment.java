package com.example.submission5.ui.notifications;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.submission5.R;
import com.example.submission5.adapter.FavoriteMovieAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import static com.example.submission5.db.DatabaseConstract.CONTENT_URI;

public class NotificationsFragment extends Fragment  {
    private ProgressBar progressBar;
    private RecyclerView rvMovies;
    private FavoriteMovieAdapter adapter;
    private Cursor list;

    private static final String EXTRA_STATE = "extra_state";


    public NotificationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notifications, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = view.findViewById(R.id.progressFavoriteMovie);
        rvMovies = view.findViewById(R.id.recycleViewFavoriteMovie);

        adapter = new FavoriteMovieAdapter(list,getContext());
        rvMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMovies.setAdapter(adapter);



    }


    @Override
    public void onResume() {
        super.onResume();
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
            return getActivity().getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor items) {
            super.onPostExecute(items);
            progressBar.setVisibility(View.GONE);

            list = items;
            adapter.setListMovies(list);

            if (list.getCount() == 0) {
                showSnackbarMessage(getResources().getString(R.string.snackbar));
            }

        }
    }

}

