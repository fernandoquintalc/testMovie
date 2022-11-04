package com.fernando.test.framework.web.retrofit

import com.fernando.test.domain.MovieVideo
import com.fernando.test.framework.web.model.GenresDto
import com.fernando.test.framework.web.model.MovieDto
import com.fernando.test.framework.web.model.ResultMovies
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/upcoming")
    fun getMoviesUpcoming(@Query("page") page : Long): Call<ResultMovies<MovieDto>>

    @GET("movie/top_rated")
    fun getMoviesTopRated(@Query("page") page : Long): Call<ResultMovies<MovieDto>>

    @GET("movie/{movieId}/videos")
    fun getVideosByMovieId(@Path("movieId") movieId : Long) : Call<ResultMovies<MovieVideo>>

    @GET("genre/movie/list")
    fun getGenres() : Call<GenresDto>
}