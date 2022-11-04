package com.fernando.test.usecases

import com.fernando.test.data.repository.MovieRepository
import com.fernando.test.domain.Movie
import kotlinx.coroutines.flow.Flow

class GetMovieListByYearUseCase constructor(private val movieRepository: MovieRepository) {

    suspend operator fun invoke(year : String) : Flow<List<Movie>> {
        return movieRepository.getMoviesByYear(year)
    }
}