package com.devexperto.architectcoders.usecases

import com.devexperto.architectcoders.data.Error
import com.devexperto.architectcoders.data.MoviesRepository
import com.devexperto.architectcoders.domain.Movie

class SwitchFavoriteUseCase(private val moviesRepository: MoviesRepository) {

    suspend operator fun invoke(movie: Movie): Error? = moviesRepository.switchFavorite(movie)
}