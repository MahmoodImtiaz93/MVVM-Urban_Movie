package com.mahmood_imtiaz.urbanmovies.Request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.mahmood_imtiaz.urbanmovies.AppExecutors;
import com.mahmood_imtiaz.urbanmovies.Model.MovieModel;
import com.mahmood_imtiaz.urbanmovies.Response.MovieSearchResponse;
import com.mahmood_imtiaz.urbanmovies.Utils.Credentials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class MovieApiClient {
    // LiveData for Search
    private MutableLiveData<List<MovieModel>> mMovies;
    private static MovieApiClient instance;

    // Global runnable for Search Requests
    private RetriveMovieRunnable retriveMovieRunnable;


    //LiveData for popular moveis
    private MutableLiveData<List<MovieModel>> mMoviesPop;
    // Global runnable for Get Popular Requests
    private RetriveMovieRunnablePop retriveMovieRunnablePop;




    public static MovieApiClient getInstance() {
        if (instance == null) {
            instance = new MovieApiClient();
        }
        return instance;
    }


    private MovieApiClient() {
        // For Search
        mMovies = new MutableLiveData<>();
        // For Popular Movies
        mMoviesPop = new MutableLiveData<>();
    }


    //For Search
    public LiveData<List<MovieModel>> getMovies() {
        return mMovies;
    }

    // For Popular Movies
    public LiveData<List<MovieModel>> getMoviesPop() {
        return mMoviesPop;
    }



    // 1. This method will call through the classes
    public void searchMovieApi(String query, int pageNumber) {
        if (retriveMovieRunnable != null) {
            retriveMovieRunnable = null;
        }

        retriveMovieRunnable = new RetriveMovieRunnable(query, pageNumber);
        final Future myHandler = AppExecutors.getInstance().mNetworkIO().submit(retriveMovieRunnable);

        AppExecutors.getInstance().mNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //TimeOut or Cancelling Retrofit Call
                myHandler.cancel(true);
            }
        }, 3000, TimeUnit.MILLISECONDS);
    }

    public void searchMoviePop(int pageNumber) {
        if (retriveMovieRunnablePop != null) {
            retriveMovieRunnablePop = null;
        }

        retriveMovieRunnablePop = new RetriveMovieRunnablePop(pageNumber);
        final Future myHandler2 = AppExecutors.getInstance().mNetworkIO().submit(retriveMovieRunnablePop);

        AppExecutors.getInstance().mNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                //TimeOut or Cancelling Retrofit Call
                myHandler2.cancel(true);
            }
        }, 1000, TimeUnit.MILLISECONDS);
    }

    // Retreiving Data from RestApi by runnable class
    // We have 2 types of Queries : 1. Movies by ID & 2. Search Movies

    private class RetriveMovieRunnable implements Runnable {
        private String query;
        private int pageNumber;
        boolean cancleRequest;

        public RetriveMovieRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancleRequest = false;
        }

        @Override
        public void run() {
            //Getting the Response Objects
            try {
                Response response = getMovies(query, pageNumber).execute();

                if (cancleRequest) {
                    return;
                }

                if (response.code() == 200) {
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse) response.body()).getMovies());
                    if (pageNumber == 1) {
                        //Sending data to Live Data
                        //PostValue : used for background thread
                        //setValue: not for background thread
                        mMovies.postValue(list);
                    } else {
                        List<MovieModel> currentMovies = mMovies.getValue();
                        currentMovies.addAll(list);
                        mMovies.postValue(currentMovies);
                    }
                } else {
                    String error = response.errorBody().string();
                    Log.v("Tag", "Error" + error);
                    mMovies.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();

                mMovies.postValue(null);
            }


        }

        // Search Query
        private Call<MovieSearchResponse> getMovies(String query, int pageNumber) {
            return Service.getMovieApi().searchMovie(
                    Credentials.API_KEY,
                    query,
                    pageNumber

            );
        }

        private void cancleRequest() {
            Log.v("Tag", "Cancelling Request");
            cancleRequest = true;
        }
    }

    private class RetriveMovieRunnablePop implements Runnable {
        private int pageNumber;
        boolean cancleRequest;

        public RetriveMovieRunnablePop(int pageNumber) {
            this.pageNumber = pageNumber;
            cancleRequest = false;
        }

        @Override
        public void run() {
            //Getting the Response Objects
            try {
                Response response2 = getPop(pageNumber).execute();

                if (cancleRequest) {
                    return;
                }

                if (response2.code() == 200) {
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse) response2.body()).getMovies());
                    if (pageNumber == 1) {
                        //Sending data to Live Data
                        //PostValue : used for background thread
                        //setValue: not for background thread
                        mMoviesPop.postValue(list);
                    } else {
                        List<MovieModel> currentMovies = mMoviesPop.getValue();
                        currentMovies.addAll(list);
                        mMoviesPop.postValue(currentMovies);
                    }
                } else {
                    String error = response2.errorBody().string();
                    Log.v("Tag", "Error" + error);
                    mMoviesPop.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();

                mMoviesPop.postValue(null);
            }


        }

        // Search Query
        private Call<MovieSearchResponse> getPop(int pageNumber) {
            return Service.getMovieApi().getPopular(
                    Credentials.API_KEY,
                    pageNumber
            );
        }

        private void cancleRequest() {
            Log.v("Tag", "Cancelling Request");
            cancleRequest = true;
        }
    }


}
