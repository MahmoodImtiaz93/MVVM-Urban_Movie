package com.mahmood_imtiaz.urbanmovies.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mahmood_imtiaz.urbanmovies.Model.MovieModel;
import com.mahmood_imtiaz.urbanmovies.Repository.MovieRepository;

import java.util.List;

public class MovieListViewModel extends ViewModel {

    // MutableLiveData extends LiveData Class. or MutableLiveData is a sub class of LiveData


    //Getting Data from Repository
    private MovieRepository movieRepository;

    public MovieListViewModel( ) {
        movieRepository = MovieRepository.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies(){
        return movieRepository.getMovies();
    }

    public LiveData<List<MovieModel>> getPop(){
        return movieRepository.getPop();
    }

    //3. Calling method in ViewModel
    public void searchMovieApi(String query,int pageNumber){
         movieRepository.searchMovieApi(query,pageNumber);
    }

    public void searchMoviePop(int pageNumber){
        movieRepository.searchMoviePop(pageNumber);
    }



    public void searchNextpage(){
        movieRepository.searchNextPage();
    }
}
