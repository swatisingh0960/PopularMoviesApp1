package com.example.swatisingh.popularmoviesapp1.utilities;

import android.content.Context;

import com.example.swatisingh.popularmoviesapp1.Model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TheMovieDBJsonUtils {

    public static List<Movie> getJson(Context context, String moviesJsonStr) throws JSONException{
        List<Movie> movies = new ArrayList<>();
        JSONObject moviesJson = new JSONObject(moviesJsonStr);

        JSONArray results = moviesJson.getJSONArray("results");
        for(int i =0; i < results.length(); i++){
        String title = "";
        String releaseDate = "";
        String imageUrl = "";
        double rating = 0.0;
        String plot = "";

        JSONObject object = results.getJSONObject(i);
        title = object.getString("title");
        releaseDate = object.getString("release_date");
        rating = object.getDouble("vote_average");
        plot = object.getString("overview");
        imageUrl = object.getString("poster_path");

        movies.add(new Movie(title, releaseDate, imageUrl, rating, plot));
        }
    return movies;
    }

}
