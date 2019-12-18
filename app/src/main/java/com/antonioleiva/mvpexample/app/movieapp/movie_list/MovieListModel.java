package com.antonioleiva.mvpexample.app.movieapp.movie_list;

import android.util.Log;

import com.antonioleiva.mvpexample.app.movieapp.model.Movie;
import com.antonioleiva.mvpexample.app.movieapp.model.MovieListResponse;
import com.antonioleiva.mvpexample.app.movieapp.network.ApiClient;
import com.antonioleiva.mvpexample.app.movieapp.network.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.antonioleiva.mvpexample.app.movieapp.network.ApiClient.API_KEY;

public class MovieListModel implements MovieListContract.Model {
    private final String TAG = "MovieListModel";

    @Override
    public void getMovieList(OnFinishedListener onFinishedListener, int pageNo) {

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<MovieListResponse> call = apiService.getPopularMovies(API_KEY, pageNo);
        call.enqueue(new Callback<MovieListResponse>() {
            @Override
            public void onResponse(Call<MovieListResponse> call, Response<MovieListResponse> response) {
                List<Movie> movies = response.body().getResults();
                Log.d(TAG, "Number of movies received: " + movies.size());
                onFinishedListener.onFinished(movies);
            }

            @Override
            public void onFailure(Call<MovieListResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                onFinishedListener.onFailure(t);
            }
        });
    }
}
