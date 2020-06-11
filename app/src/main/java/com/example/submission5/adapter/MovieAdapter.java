package com.example.submission5.adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.submission5.Activity.DetailMoviesActivity;
import com.example.submission5.R;
import com.example.submission5.entity.Movies;

import java.util.ArrayList;

import static com.example.submission5.db.DatabaseConstract.CONTENT_URI;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> implements Filterable {
    private ArrayList<Movies> mData = new ArrayList<>();
    private ArrayList<Movies>list;
    public void setData(ArrayList<Movies>items){
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
        list = new ArrayList<>(items);
    }
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_movie,parent,false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder holder, int position) {
        holder.bind(mData.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Movies movies = new Movies();
                String status = "2";
                Intent intent = new Intent(holder.itemView.getContext(), DetailMoviesActivity.class);
                Uri uri =Uri.parse(CONTENT_URI +"/"+movies.getIdMovie());
                intent.putExtra(DetailMoviesActivity.EXTRA_DATA,mData.get(holder.getAdapterPosition()));
                intent.putExtra(DetailMoviesActivity.LOCAL_STATUS,status);
                intent.setData(uri);
                holder.itemView.getContext().startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public Filter getFilter() {
        return filtering;
    }
    private Filter filtering = new Filter(){

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Movies>filterlist = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0){
                filterlist.addAll(list);
            }else {
                String filteringPattern = charSequence.toString().toLowerCase().trim();
                for (Movies item : list){
                    if (item.getTitleMovie().toLowerCase().contains(filteringPattern)){
                        filterlist.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filterlist;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mData.clear();
            mData.addAll((ArrayList)filterResults.values);
            notifyDataSetChanged();

        }
    };

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView tvRating,tvTitle;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_postermovie);
            tvRating = itemView.findViewById(R.id.tv_ratingmovie);
            tvTitle = itemView.findViewById(R.id.tvTitlemovie);
        }
        void bind(Movies movies){
            String url_image = "https://image.tmdb.org/t/p/w185" + movies.getPosterMovie();
            tvRating.setText(movies.getVoteMovie());
            tvTitle.setText(movies.getTitleMovie());
            Glide.with(itemView.getContext())
                    .load(url_image)
                    .apply(new RequestOptions().override(120,250))
                    .into(imageView);
        }
    }
}
