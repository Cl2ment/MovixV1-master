package com.github.movixdev.movix.Series;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by isen on 24/01/2016.
 */
public interface TvApiService {
    @GET("/tv/popular")
    void getPopularTvSeries(Callback<Tv.TvResult> cb);

    @GET("/tv/on_the_air")
    void getOnTheAirTvSeries(Callback<Tv.TvResult> cb);
}
