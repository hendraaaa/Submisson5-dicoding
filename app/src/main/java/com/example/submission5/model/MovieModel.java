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

public class MovieModel extends ViewModel {
    private static final String API_KEY = BuildConfig.TheMovieDBAPI;
    private MutableLiveData<ArrayList<Movies>>listMovie = new MutableLiveData<>();

    public void setMovie(){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movies> listItems = new ArrayList<>();
        String url = "http://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY + "&language=en-US";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String reuslt = new String(responseBody);
                    JSONObject responObject = new JSONObject(reuslt);
                    JSONArray list = responObject.getJSONArray("results");

                    for (int i = 0; i < list.length();i++){
                        JSONObject movie = list.getJSONObject(i);
                        Movies movies = new Movies();
                        movies.setIdMovie(movie.getInt("id"));
                        movies.setTitleMovie(movie.getString("original_title"));
                        movies.setDescMovie(movie.getString("overview"));
                        movies.setReleaseDateMovie(movie.getString("release_date"));
                        movies.setBackdropMovie(movie.getString("backdrop_path"));
                        movies.setPosterMovie(movie.getString("poster_path"));
                        movies.setVoteMovie(movie.getString("vote_count"));
                        listItems.add(movies);
                    }
                    listMovie.postValue(listItems);
                }catch (JSONException e){
                    Log.d("onSuccess: ",e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure: ",error.getMessage());

            }
        });
    }
    public void setMoviesearch(String s){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movies> listItems = new ArrayList<>();
       // String url = "http://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY + "&language=en-US";
        String url = "http://api.themoviedb.org/3/search/movie?api_key=" + API_KEY + "&language=en-US&query="+s;

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String reuslt = new String(responseBody);
                    JSONObject responObject = new JSONObject(reuslt);
                    JSONArray list = responObject.getJSONArray("results");

                    for (int i = 0; i < list.length();i++){
                        JSONObject movie = list.getJSONObject(i);
                        Movies movies = new Movies();
                        movies.setIdMovie(movie.getInt("id"));
                        movies.setTitleMovie(movie.getString("original_title"));
                        movies.setDescMovie(movie.getString("overview"));
                        movies.setReleaseDateMovie(movie.getString("release_date"));
                        movies.setBackdropMovie(movie.getString("backdrop_path"));
                        movies.setPosterMovie(movie.getString("poster_path"));
                        movies.setVoteMovie(movie.getString("vote_count"));
                        listItems.add(movies);
                    }
                    listMovie.postValue(listItems);
                }catch (JSONException e){
                    Log.d("onSuccess: ",e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure: ",error.getMessage());

            }
        });
    }
    public LiveData<ArrayList<Movies>> getMovie(){
        return listMovie;
    }
    public LiveData<ArrayList<Movies>> getMoviesearch(){
        return listMovie;
    }
}
