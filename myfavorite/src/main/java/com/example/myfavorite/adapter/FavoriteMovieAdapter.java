package com.example.myfavorite.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myfavorite.R;
import com.example.myfavorite.entity.Movies;

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteMovieHolder> {
    private Context context;
    private Cursor cursor;
    public FavoriteMovieAdapter(Cursor items, Context context) {
        this.context = context;
        setListMovies(items);
    }
   /* public void setMovies(ArrayList<Movies> movies) {
        this.listMovies = movies;
    }*/

    public void setListMovies(Cursor cursor){
        this.cursor = cursor;
        //this.listMovies.addAll(listMovies);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteMovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_favoritemovie,parent,false);
        return new FavoriteMovieHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteMovieHolder holder, int position) {
        Movies movies = getItem(position);
        holder.tvJudul.setText(movies.getTitleMovie());
        holder.tvDesc.setText(movies.getDescMovie());
        holder.tvRilis.setText(movies.getReleaseDateMovie());
        Glide.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w185"+movies.getPosterMovie())
                .apply(new RequestOptions().override(150,220))
                .into(holder.imgPoster);

    }

    @Override
    public int getItemCount() {
        if (cursor == null)return 0;
        return cursor.getCount();
    }

    public class FavoriteMovieHolder extends RecyclerView.ViewHolder {
        private ImageView imgPoster;
        private TextView tvJudul,tvDesc,tvRilis;
        public FavoriteMovieHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.img_posterfavMovie);
            tvJudul = itemView.findViewById(R.id.tv_titlefavMovie);
            tvDesc = itemView.findViewById(R.id.tv_descriptionfavMovie);
            tvRilis = itemView.findViewById(R.id.tv_releaseFavMovie);
        }
    }
    private Movies getItem(int position) {
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("Position Invalid");
        }
        return new Movies(cursor);
    }
}
