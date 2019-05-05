package com.leanagri;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.leanagri.REST.Model.JSONModel;
import com.leanagri.REST.Model.Results;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.concurrent.ConcurrentMap;

public class Home extends AppCompatActivity {
    private static final String URL = "https://api.themoviedb.org/3/discover/movie?api_key=67959dd45e86972972e2bf8152f6e3ac&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=";
    private static int page = 1;
    private Dialog progressBar;

    RecyclerView movieList;
    CardView movieCard;

    MoviesAdapter moviesAdapter;

    ArrayList<MovieRecyclerModel> theList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        movieList = findViewById(R.id.movie_list);

        movieCard = findViewById(R.id.movie_card);

        setUpProgressBar();
        getData();
        setOrders();
    }

    private void setOrders(){
        ImageView rating = findViewById(R.id.rating_button);
        ImageView date = findViewById(R.id.date_button);
        ImageView title = findViewById(R.id.title_button);

        rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Home.this, "Sorting With Rating!", Toast.LENGTH_SHORT).show();
                sortByRating();
                populateRecyclerView();
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Home.this, "Sorting with Date Released!", Toast.LENGTH_SHORT).show();
                sortByDateReleased();
                populateRecyclerView();
            }
        });

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Home.this, "Sorting with the Name!", Toast.LENGTH_SHORT).show();
                sortByName();
                populateRecyclerView();
            }
        });

    }

    private void setUpProgressBar(){
        progressBar = new Dialog(this);
        progressBar.setCancelable(false);
        progressBar.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    private void getData(){
        progressBar.show();
        StringRequest request = new StringRequest(URL+page, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                JSONModel data = gson.fromJson(response, JSONModel.class);

                theList = getConvertedData(data);

                populateRecyclerView();
                progressBar.hide();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    private ArrayList<MovieRecyclerModel> getConvertedData(JSONModel data){
        ArrayList<MovieRecyclerModel> list = new ArrayList<>();
        MovieRecyclerModel movieRecyclerModel;

        for (Results results : data.getResults()){
            movieRecyclerModel = new MovieRecyclerModel();

            movieRecyclerModel.setTitle(results.getTitle());
            movieRecyclerModel.setPosterUrl(results.getPoster_path());
            movieRecyclerModel.setRating(String.valueOf(results.getVote_average()));
            movieRecyclerModel.setReleaseDate(results.getRelease_date());
            movieRecyclerModel.setDescription(results.getOverview());

            list.add(movieRecyclerModel);
        }

        return list;
    }

    private void populateRecyclerView(){
        movieList.setLayoutManager(new LinearLayoutManager(this));
        moviesAdapter = new MoviesAdapter(theList, Home.this);
        movieList.setAdapter(moviesAdapter);
    }

    private ArrayList<MovieRecyclerModel> sortByName(){
        Collections.sort(theList, new NameComparator());
        return theList;
    }

    private ArrayList<MovieRecyclerModel> sortByDateReleased(){
        Collections.sort(theList, new DateComparator());
        return theList;
    }

    private ArrayList<MovieRecyclerModel> sortByRating(){
        Collections.sort(theList, new RatingComparator());
        return theList;
    }
}

class RatingComparator implements Comparator<MovieRecyclerModel>{

    @Override
    public int compare(MovieRecyclerModel o1, MovieRecyclerModel o2) {
        return Double.compare(Double.parseDouble(o1.getRating()), Double.parseDouble(o2.getRating()));
    }
}

class NameComparator implements Comparator<MovieRecyclerModel>{

    @Override
    public int compare(MovieRecyclerModel o1, MovieRecyclerModel o2) {
        return o1.getTitle().compareToIgnoreCase(o2.getTitle());
    }
}

class DateComparator implements Comparator<MovieRecyclerModel>{

    @Override
    public int compare(MovieRecyclerModel o1, MovieRecyclerModel o2) {
        Date date1 = null;
        Date date2 = null;

        try {
            date1=new SimpleDateFormat("yyyy-MM-dd").parse(o1.getReleaseDate());
            date2=new SimpleDateFormat("yyyy-MM-dd").parse(o2.getReleaseDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date1.before(date2)) {
            return -1;
        } else if (date1.after(date2)) {
            return 1;
        } else {
            return 0;
        }
    }
}