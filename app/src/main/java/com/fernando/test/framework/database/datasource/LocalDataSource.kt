package com.fernando.test.framework.database.datasource

import com.fernando.test.data.datasource.MovieDataSource
import com.fernando.test.data.datasource.StorageMovieDataSource
import com.fernando.test.domain.Genre
import com.fernando.test.domain.Movie
import com.fernando.test.domain.MovieVideo
import com.fernando.test.framework.database.dao.MovieDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource constructor(private val dao: MovieDao) : StorageMovieDataSource {

    override fun getMoviesByLanguage(language: String): Flow<List<Movie>> {
        return dao.getMoviesByLanguage(language)
    }

    override fun getMoviesByYear(year: String): Flow<List<Movie>> {
        return dao.getMoviesByYear("$year-%")
    }

    override fun getGenre(genreId: Int): Genre {
        return dao.selectGenreById(genreId)
    }


}