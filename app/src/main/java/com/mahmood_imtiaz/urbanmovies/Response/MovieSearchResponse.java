package com.mahmood_imtiaz.urbanmovies.Response;

// Getting Multiple Movies or Movie Lists -- Popular Movies

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mahmood_imtiaz.urbanmovies.Model.MovieModel;

import java.util.List;

public class MovieSearchResponse {
    @SerializedName("total_pages")
    @Expose
    private  int total_count;

    @SerializedName("results")
    @Expose
    private List<MovieModel> movieModels;

    public int getTotal_count(){
        return total_count;
    }

    public List<MovieModel> getMovies(){
        return movieModels;
    }
}
