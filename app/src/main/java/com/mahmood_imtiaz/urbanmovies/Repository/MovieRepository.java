package com.mahmood_imtiaz.urbanmovies.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mahmood_imtiaz.urbanmovies.Model.MovieModel;
import com.mahmood_imtiaz.urbanmovies.Request.MovieApiClient;

import java.util.List;

public class MovieRepository {

    private static MovieRepository instance;

    private static MovieApiClient movieApiClient;

    private String mQuery;
    private int mPageNumber;



    public static MovieRepository getInstance() {
        if (instance == null) {
            instance = new MovieRepository();
        }
        return instance;
    }
    private MovieRepository() {
        movieApiClient = MovieApiClient.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies() {
        return movieApiClient.getMovies();
    }

    // Getting Popular
    public LiveData<List<MovieModel>> getPop() {
        return movieApiClient.getMoviesPop();
    }




    // 2. Calling the Method in repository
    public void searchMovieApi(String query,int pageNumber){
        mQuery =query;
        mPageNumber=pageNumber;
        movieApiClient.searchMovieApi(query,pageNumber);
    }

    public void searchMoviePop( int pageNumber){
        mPageNumber=pageNumber;
        movieApiClient.searchMoviePop(pageNumber);
    }

    public void searchNextPage(){
        searchMovieApi(mQuery,mPageNumber+1);
    }

}

