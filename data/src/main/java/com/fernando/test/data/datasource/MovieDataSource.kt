package com.fernando.test.data.datasource

import com.fernando.test.domain.Genre
import com.fernando.test.domain.Movie
import com.fernando.test.domain.MovieVideo

interface MovieDataSource {


    suspend fun getVideosByMovieId(movieId : Long) : Result<List<MovieVideo>>


    suspend fun getGenres() : Result<List<Genre>>


}