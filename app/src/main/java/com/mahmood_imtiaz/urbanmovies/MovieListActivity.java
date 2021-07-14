package com.mahmood_imtiaz.urbanmovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.mahmood_imtiaz.urbanmovies.Adapters.MovieRecyclerView;
import com.mahmood_imtiaz.urbanmovies.Adapters.OnMovieListener;
import com.mahmood_imtiaz.urbanmovies.Model.MovieModel;
import com.mahmood_imtiaz.urbanmovies.Request.Service;
import com.mahmood_imtiaz.urbanmovies.Response.MovieSearchResponse;
import com.mahmood_imtiaz.urbanmovies.Utils.Credentials;
import com.mahmood_imtiaz.urbanmovies.Utils.MovieApi;
import com.mahmood_imtiaz.urbanmovies.ViewModel.MovieListViewModel;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class  MovieListActivity extends AppCompatActivity implements OnMovieListener {

    public String TAG = this.getClass().getSimpleName();
    Button button;
    private MovieListViewModel movieListViewModel;
    private RecyclerView recyclerView;
    private MovieRecyclerView movieRecyclerViewAdapter;
    boolean isPopular = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Creating ViewModel Object
        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);
        // Calling the observers
        ConfigureRecyclerView();
        ObserveDataChange();
        SetupSearchView();
        ObservePopularMovies();

        //Getting Popular
        movieListViewModel.searchMoviePop(1);


//        button = findViewById(R.id.button);
//
//        button.setOnClickListener(v -> {
//           searchMovieApi("Fast",1);
//        });


    }

    private void ObservePopularMovies() {
        movieListViewModel.getPop().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                if (movieModels!=null){
                    for (MovieModel movieModel:movieModels){
                        //Get the data in log
                        Log.v("Tag","onChanged: " +movieModel.getTitle());
                        movieRecyclerViewAdapter.setmMovies(movieModels);
                    }
                }
            }
        });
    }

    // This method will observe if any data change in MovieListViewModel
    private void ObserveDataChange(){
        movieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
            if (movieModels!=null){
                 for (MovieModel movieModel:movieModels){
                     //Get the data in log
                     Log.v("Tag","onChanged: " +movieModel.getTitle());
                    movieRecyclerViewAdapter.setmMovies(movieModels);
                 }
            }
            }
        });
    }
//    // 4. Calling method in MainActivity
//    private void searchMovieApi(String query,int pageNumber){
//        movieListViewModel.searchMovieApi(query,pageNumber);
//    }

    //5. Add data to RecyclerView
    private void ConfigureRecyclerView(){
        movieRecyclerViewAdapter = new MovieRecyclerView(this);
        recyclerView.setAdapter(movieRecyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));

        //RecyvlerViewPagination
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull @NotNull RecyclerView recyclerView, int newState) {
                 if(!recyclerView.canScrollVertically(1)){
                    movieListViewModel.searchNextpage();
                 }

            }
        });







    }

    @Override
    public void onMovieClick(int position) {
       // Toast.makeText(this, "The Position"+position, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this,MovieDetails.class);
        intent.putExtra("movie",movieRecyclerViewAdapter.getSelectedMovie(position));
        startActivity(intent);

    }

    @Override
    public void onCategoryClick(String category) {

    }

    //Get data form SearchView
    private void SetupSearchView() {
        final androidx.appcompat.widget.SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                movieListViewModel.searchMovieApi(
                        query,
                        1
                );
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPopular=false;
            }
        });

//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                movieListViewModel.searchMovieApi(
//                        query,
//                        1
//                );
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });

    }


//    private void GetRetrofitResponse() {
//        MovieApi movieApi = Service.getMovieApi();
//        Call<MovieSearchResponse> responseCall = movieApi.searchMovie(
//                Credentials.API_KEY,
//                "Jack+Reacher",
//                1
//        );
//        responseCall.enqueue(new Callback<MovieSearchResponse>() {
//            @Override
//            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {
//                if (response.code()==200){
//                    Log.v("TAG","The Response"+String.valueOf(response.body()));
//                    List<MovieModel> movies = new ArrayList<>(response.body().getMovies());
//                    for (MovieModel movieModel :movies){
//                        Log.v("TAG","The Release Date"+movieModel.getRelease_date());
//                    }
//                }else {
//                    try {
//                        Log.v("TAG","Error"+response.errorBody().string());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {
//                Log.v(TAG,"OnFailure"+String.valueOf(t));
//            }
//        });
//
//    }
//
//    private void GetRetrofitResponseMovieID(){
//        MovieApi movieApi = Service.getMovieApi();
//        Call<MovieModel> responseCall = movieApi.getMovie(
//                125,
//                Credentials.API_KEY
//        );
//        responseCall.enqueue(new Callback<MovieModel>() {
//            @Override
//            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
//                if (response.code()==200){
//                    MovieModel movieModel = response.body();
//                    Log.v("MovieTag","Movie By ID " +movieModel.getTitle());
//                }else {
//                    try {
//                        Log.v("MovieTag","Error Getting Movie ID"+response.errorBody().string());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MovieModel> call, Throwable t) {
//
//            }
//        });
//    }


}