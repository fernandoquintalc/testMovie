package com.fernando.test.data.datasource

import com.fernando.test.domain.Genre
import com.fernando.test.domain.Movie
import kotlinx.coroutines.flow.Flow

interface StorageMovieDataSource {

    fun getMoviesByLanguage(language : String) : Flow<List<Movie>>
    fun getMoviesByYear(year : String) : Flow<List<Movie>>
    fun getGenre(genreId : Int) : Genre
}