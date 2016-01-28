package com.github.movixdev.movix.Movie;

import com.github.movixdev.movix.Movie.Movie;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by Maxence Cheillan on 24/01/16.
 */

public interface MoviesApiService {
    @GET("/movie/popular")
    void getPopularMovies(Callback<Movie.MovieResult> cb);

    @GET("/movie/upcoming")
    void getUpComingMovies(Callback<Movie.MovieResult> cb);
}
