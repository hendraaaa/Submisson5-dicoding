package com.example.submission5.fragment;


import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;


import com.example.submission5.Activity.ActivitySearch;
import com.example.submission5.R;
import com.example.submission5.adapter.MovieAdapter;
import com.example.submission5.entity.Movies;
import com.example.submission5.model.MovieModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMovie extends Fragment {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private MovieAdapter adapter;

    private MovieModel movieModel;


    public FragmentMovie() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_fragment_movie, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycleViewMovie);
        progressBar = view.findViewById(R.id.progressMovie);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        adapter = new MovieAdapter();
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        movieModel = new ViewModelProvider(getActivity()).get(MovieModel.class);
        movieModel.setMovie();
        showLoading(true);

        movieModel.getMovie().observe(getActivity(), new Observer<ArrayList<Movies>>() {
            @Override
            public void onChanged(ArrayList<Movies> movies) {
                if (movies != null){
                    adapter.setData(movies);
                    showLoading(false);
                }
            }
        });
    }
    private void showLoading(Boolean state){
        if (state){
            progressBar.setVisibility(View.VISIBLE);
        }else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search,menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        SearchManager searchManager = (SearchManager)getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setQueryHint("cari");
        ComponentName componentName = new ComponentName(getActivity(), ActivitySearch.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
               if (s.length()>0){
                   Intent intent = new Intent(getActivity(),ActivitySearch.class);
                   intent.putExtra("movie",s);
                   int angka = 1;
                   intent.putExtra("status",angka);
                   startActivity(intent);

               }else {
                   movieModel.setMovie();
               }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //adapter.getFilter().filter(s);
                return false;
            }

        });
    }


}
