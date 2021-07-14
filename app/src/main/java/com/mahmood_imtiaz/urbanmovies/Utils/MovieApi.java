package com.mahmood_imtiaz.urbanmovies.Utils;

import com.mahmood_imtiaz.urbanmovies.Model.MovieModel;
import com.mahmood_imtiaz.urbanmovies.Response.MovieResponse;
import com.mahmood_imtiaz.urbanmovies.Response.MovieSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {
    //https://api.themoviedb.org/3/search/movie?api_key=7942b64d6372dc0eca2f3722b1e3abe7&query=Jack+Reacher
    @GET("/3/search/movie")
    Call<MovieSearchResponse> searchMovie(
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") int page
    );

    // Get Movie By Id
    //https://api.themoviedb.org/3/movie/500?api_key=7942b64d6372dc0eca2f3722b1e3abe7
    @GET("/3/movie/{movie_id}?")
    Call<MovieModel>getMovie(
            @Path("movie_id") int movie_id,
            @Query("api_key") String key
    );

    //Get popular Movies
    @GET("/3/movie/popular")
    Call<MovieSearchResponse> getPopular(
            @Query("api_key") String key,
            @Query("page") int page
    );
}
