package com.project.movie.mymovie.ApiService;

import com.project.movie.mymovie.model.SeasonModel;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by Maxence Cheillan on 06/06/2016.
 */
public interface SerieApiService {
    @GET("/tv/popular")
    void getPopularTvSeries(Callback<SeasonModel.SeasonResult> cb);

    @GET("/tv/on_the_air")
    void getOnTheAirTvSeries(Callback<SeasonModel.SeasonResult> cb);

    @GET("/tv/airing_today")
    void getAiringTodayTvSeries(Callback<SeasonModel.SeasonResult> cb);

    @GET("/tv/top_rated")
    void getTopRatedTvSeries(Callback<SeasonModel.SeasonResult> cb);

}
