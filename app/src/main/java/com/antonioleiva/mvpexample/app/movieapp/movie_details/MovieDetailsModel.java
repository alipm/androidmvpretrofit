package com.antonioleiva.mvpexample.app.movieapp.movie_details;

import android.util.Log;

import com.antonioleiva.mvpexample.app.movieapp.model.Movie;
import com.antonioleiva.mvpexample.app.movieapp.network.ApiClient;
import com.antonioleiva.mvpexample.app.movieapp.network.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.antonioleiva.mvpexample.app.movieapp.network.ApiClient.API_KEY;
import static com.antonioleiva.mvpexample.app.movieapp.utils.Constants.CREDITS;

public class MovieDetailsModel implements MovieDetailsContract.Model {

    private final String TAG = "MovieDetailsModel";


    @Override
    public void getMovieDetails(OnFinishedListener onFinishedListener, int movieId) {

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<Movie> call = apiService.getMovieDetails(movieId, API_KEY, CREDITS);

        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Movie movie = response.body();
                Log.d(TAG, "Movie data received: " + movie.toString());
                onFinishedListener.onFinished(movie);
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Log.e(TAG, t.toString());
                onFinishedListener.onFailure(t);
            }
        });
    }
}
