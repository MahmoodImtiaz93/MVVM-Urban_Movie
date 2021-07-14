package com.mahmood_imtiaz.urbanmovies.Response;

//For Single Movie Request

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mahmood_imtiaz.urbanmovies.Model.MovieModel;

//Singleton Pattern

public class MovieResponse {
    @SerializedName("results")
    @Expose
    private MovieModel movieModel;

    public MovieModel getMovieModel(){
        return movieModel;
    }

    @Override
    public String toString() {
        return "MovieResponse{" +
                "movieModel=" + movieModel +
                '}';
    }
}
