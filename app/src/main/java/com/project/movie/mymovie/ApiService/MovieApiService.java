package com.project.movie.mymovie.ApiService;

import com.project.movie.mymovie.model.MovieModel;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by Maxence Cheillan on 02/06/2016.
 */
public interface MovieApiService {

    @GET("/movie/popular")
    void getPopularMovies(Callback<MovieModel.MovieResult> cb);

    @GET("/movie/upcoming")
    void getUpComingMovies(Callback<MovieModel.MovieResult> cb);

    @GET("/movie/top_rated")
    void getTopRatedMovies(Callback<MovieModel.MovieResult> cb);

    @GET("/movie/now_playing")
    void getNowPlayingMovies(Callback<MovieModel.MovieResult> cb);

}
