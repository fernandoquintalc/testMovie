package com.fernando.test.data.repository

import com.fernando.test.domain.Genre
import com.fernando.test.domain.Movie
import com.fernando.test.domain.MovieVideo
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getVideosByMovie(movie: Movie) : Result<List<MovieVideo>>

    suspend fun getMoviesByLanguage(language : String) : Flow<List<Movie>>
    suspend fun getMoviesByYear(year : String) : Flow<List<Movie>>
    suspend fun getGenre(genreId : Int) : Genre
}