package com.devexperto.architectcoders.usecases

import com.devexperto.architectcoders.data.MoviesRepository
import com.devexperto.architectcoders.domain.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RequestMovieUseCase @Inject constructor(private val moviesRepository: MoviesRepository) {

    operator fun invoke(movieId: Int): Flow<Movie> = moviesRepository.requestMovieById(movieId)
}
