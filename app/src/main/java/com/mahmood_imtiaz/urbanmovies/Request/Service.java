package com.mahmood_imtiaz.urbanmovies.Request;

import com.mahmood_imtiaz.urbanmovies.Utils.Credentials;
import com.mahmood_imtiaz.urbanmovies.Utils.MovieApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Service {

    private static Retrofit.Builder retrofitbuilder = new Retrofit.Builder()
            .baseUrl(Credentials.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());
    private static Retrofit retrofit = retrofitbuilder.build();
    private static MovieApi movieApi = retrofit.create(MovieApi.class);

    public static MovieApi getMovieApi() {
        return movieApi;
    }

    //Another Approch

//    public static Retrofit retrofit = null;
//
//    public static MovieApi getMovieApi() {
//        if (retrofit == null) {
//            retrofit = new Retrofit.Builder().baseUrl(MovieApi.BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create()).build();
//        }
//        return retrofit.create(MovieApi.class);
//    }

}
