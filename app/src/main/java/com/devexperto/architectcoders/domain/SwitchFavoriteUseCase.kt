package com.devexperto.architectcoders.domain

import com.devexperto.architectcoders.data.Error
import com.devexperto.architectcoders.data.MoviesRepository
import com.devexperto.architectcoders.data.database.Movie

class SwitchFavoriteUseCase(private val moviesRepository: MoviesRepository) {

    suspend operator fun invoke(movie: Movie): Error? = moviesRepository.switchFavorite(movie)
}
