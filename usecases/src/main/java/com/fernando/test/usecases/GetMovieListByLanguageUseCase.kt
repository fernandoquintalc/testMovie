package com.fernando.test.usecases

import com.fernando.test.data.repository.MovieRepository
import com.fernando.test.domain.Movie
import kotlinx.coroutines.flow.Flow

class GetMovieListByLanguageUseCase constructor(private val movieRepository: MovieRepository) {

    suspend operator fun invoke(language : String) : Flow<List<Movie>> {
        return movieRepository.getMoviesByLanguage(language)
    }
}