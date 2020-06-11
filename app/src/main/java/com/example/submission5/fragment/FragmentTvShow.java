package com.example.submission5.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.example.submission5.Activity.ActivitySearch;
import com.example.submission5.Activity.ActivitySearchTvShow;
import com.example.submission5.R;
import com.example.submission5.adapter.MovieAdapter;
import com.example.submission5.adapter.TvShowAdapter;
import com.example.submission5.entity.Movies;
import com.example.submission5.model.TvShowModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTvShow extends Fragment {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TvShowModel tvShowModel;
    private MovieAdapter adapter;


    public FragmentTvShow() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_fragment_tv_show, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycleViewTvShow);
        progressBar = view.findViewById(R.id.progressBarTvShow);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapter = new MovieAdapter();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        tvShowModel = new ViewModelProvider(getActivity()).get(TvShowModel.class);
        tvShowModel.setTvShow();
        showLoading(true);

        tvShowModel.getTvShow().observe(getActivity(), new Observer<ArrayList<Movies>>() {
            @Override
            public void onChanged(ArrayList<Movies> tvShows) {
                if (tvShows != null) {
                    adapter.setData(tvShows);
                    showLoading(false);
                }
            }
        });
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search,menu);
        super.onCreateOptionsMenu(menu, inflater);
        android.widget.SearchView searchView = (android.widget.SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setQueryHint("Cari");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String tv) {
               if (tv.length()>0){
                   Intent intent = new Intent(getActivity(), ActivitySearchTvShow.class);
                   intent.putExtra("tvShow",tv);
                   startActivity(intent);
               }else {
                   tvShowModel.setTvShow();
               }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                tvShowModel.setTvShow();
                return false;
            }
        });
    }

}
