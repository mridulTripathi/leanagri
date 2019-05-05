package com.leanagri;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leanagri.REST.Model.JSONModel;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder>{
    private ArrayList<MovieRecyclerModel> movieRecyclerModelArrayList;
    Context context;

    public MoviesAdapter(ArrayList<MovieRecyclerModel> movieRecyclerModelArrayList, Context context) {
        this.movieRecyclerModelArrayList = movieRecyclerModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MoviesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View listItem = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_card, viewGroup, false);
        return new MyViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesAdapter.MyViewHolder myViewHolder, final int i) {
        myViewHolder.title.setText(movieRecyclerModelArrayList.get(i).getTitle());
        myViewHolder.rating.setText(movieRecyclerModelArrayList.get(i).getRating());
        myViewHolder.releaseDate.setText(movieRecyclerModelArrayList.get(i).getReleaseDate());
        myViewHolder.description.setText(movieRecyclerModelArrayList.get(i).getDescription());

        Picasso.get()
                .load("http://image.tmdb.org/t/p/w185/"+movieRecyclerModelArrayList.get(i).getPosterUrl())
                .fit()
                .noFade()
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .into(myViewHolder.poster);

        final MovieRecyclerModel movieData = movieRecyclerModelArrayList.get(i);

        myViewHolder.movieCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Details.class);
                intent.putExtra(Details.MOVIE, movieData);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieRecyclerModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView rating;
        private TextView releaseDate;
        private TextView description;
        private ImageView poster;
        private CardView movieCard;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            rating = itemView.findViewById(R.id.rating);
            releaseDate = itemView.findViewById(R.id.release_date);
            description = itemView.findViewById(R.id.details);
            poster = itemView.findViewById(R.id.poster_image);
            movieCard = itemView.findViewById(R.id.movie_card);
        }
    }
}
