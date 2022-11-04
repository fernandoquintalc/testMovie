package com.fernando.test.data.repository

import com.fernando.test.data.datasource.MovieDataSource
import com.fernando.test.data.datasource.StorageMovieDataSource
import com.fernando.test.domain.Genre
import com.fernando.test.domain.Movie
import com.fernando.test.domain.MovieVideo
import kotlinx.coroutines.flow.Flow

class DefaultMovieRepository constructor(
    private val movieDataSource: MovieDataSource,
    private val storageMovieDataSource: StorageMovieDataSource
) :
    MovieRepository {
    override suspend fun getVideosByMovie(movie: Movie): Result<List<MovieVideo>> {
        return movieDataSource.getVideosByMovieId(movie.movieId)
    }

    override suspend fun getMoviesByLanguage(language: String): Flow<List<Movie>> {
        return storageMovieDataSource.getMoviesByLanguage(language)
    }

    override suspend fun getMoviesByYear(year: String): Flow<List<Movie>> {
        return storageMovieDataSource.getMoviesByYear(year)
    }

    override suspend fun getGenre(genreId: Int): Genre {
        return storageMovieDataSource.getGenre(genreId)
    }
}