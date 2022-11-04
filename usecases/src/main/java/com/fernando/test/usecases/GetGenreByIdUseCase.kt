package com.fernando.test.usecases

import com.fernando.test.data.repository.MovieRepository
import com.fernando.test.domain.Genre

class GetGenreByIdUseCase  constructor(private val movieRepository: MovieRepository) {

    suspend operator fun invoke(genreId : Int) : Genre {
        return movieRepository.getGenre(genreId)
    }
}