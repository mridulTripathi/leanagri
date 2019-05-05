package com.leanagri;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Details extends AppCompatActivity {

    public static final String MOVIE = "TheMovieDetail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent i = getIntent();
        MovieRecyclerModel movie = (MovieRecyclerModel)i.getSerializableExtra(MOVIE);

        ImageView poster = findViewById(R.id.poster_image);
        TextView title = findViewById(R.id.title);
        TextView releaseDate = findViewById(R.id.release_date);
        TextView rating = findViewById(R.id.rating);
        TextView details = findViewById(R.id.details);

        title.setText(movie.getTitle());
        releaseDate.setText(movie.getReleaseDate());
        rating.setText(movie.getRating());
        details.setText(movie.getDescription());

        Picasso.get().load("http://image.tmdb.org/t/p/w185/"+movie.getPosterUrl()).into(poster);

    }
}
