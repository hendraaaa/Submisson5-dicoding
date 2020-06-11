package com.example.submission5.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.submission5.BuildConfig;
import com.example.submission5.entity.Movies;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TvShowModel extends ViewModel {

    private static final String API_KEY = BuildConfig.TheMovieDBAPI;
    private MutableLiveData<ArrayList<Movies>> listTvShow = new MutableLiveData<>();

    public void setTvShow(){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movies> listItems = new ArrayList<>();
        String url = "http://api.themoviedb.org/3/discover/tv?api_key=" + API_KEY + "&language=en-US";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String reuslt = new String(responseBody);
                    JSONObject responObject = new JSONObject(reuslt);
                    JSONArray list = responObject.getJSONArray("results");

                    for (int i = 0; i < list.length();i++){
                        JSONObject tvShows = list.getJSONObject(i);
                        Movies movies = new Movies();
                        movies.setIdMovie(tvShows.getInt("id"));
                        movies.setTitleMovie(tvShows.getString("original_name"));
                        movies.setDescMovie(tvShows.getString("overview"));
                        movies.setBackdropMovie(tvShows.getString("backdrop_path"));
                        movies.setPosterMovie(tvShows.getString("poster_path"));
                        movies.setReleaseDateMovie(tvShows.getString("first_air_date"));
                        movies.setVoteMovie(tvShows.getString("vote_count"));
                        listItems.add(movies);
                    }
                    listTvShow.postValue(listItems);
                }catch (JSONException e){
                    Log.d("onSuccess1: ",e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure: ",error.getMessage());

            }
        });
    }
    public void setSearchTvShow(String s){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movies> listItems = new ArrayList<>();
        String url = "http://api.themoviedb.org/3/search/tv?api_key="+API_KEY+"&language=en-US&query="+s;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String reuslt = new String(responseBody);
                    JSONObject responObject = new JSONObject(reuslt);
                    JSONArray list = responObject.getJSONArray("results");

                    for (int i = 0; i < list.length();i++){
                        JSONObject tvShows = list.getJSONObject(i);
                        Movies movies = new Movies();
                        movies.setIdMovie(tvShows.getInt("id"));
                        movies.setTitleMovie(tvShows.getString("original_name"));
                        movies.setDescMovie(tvShows.getString("overview"));
                        movies.setBackdropMovie(tvShows.getString("backdrop_path"));
                        movies.setPosterMovie(tvShows.getString("poster_path"));
                        movies.setReleaseDateMovie(tvShows.getString("first_air_date"));
                        movies.setVoteMovie(tvShows.getString("vote_count"));
                        listItems.add(movies);
                    }
                    listTvShow.postValue(listItems);
                }catch (JSONException e){
                    Log.d("onSuccess1: ",e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure: ",error.getMessage());

            }
        });
    }


    public LiveData<ArrayList<Movies>> getTvShow(){
        return listTvShow;
    }
    public LiveData<ArrayList<Movies>> getSearchTvShow(){
        return listTvShow;
    }
}
