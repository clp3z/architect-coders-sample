package com.devexperto.architectcoders.domain

import com.devexperto.architectcoders.data.MoviesRepository
import com.devexperto.architectcoders.data.database.Movie
import kotlinx.coroutines.flow.Flow

class RequestMovieUseCase(private val moviesRepository: MoviesRepository) {

    operator fun invoke(movieId: Int): Flow<Movie> = moviesRepository.requestMovieById(movieId)
}
