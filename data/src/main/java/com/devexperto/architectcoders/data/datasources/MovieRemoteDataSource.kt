package com.devexperto.architectcoders.data.datasources

import arrow.core.Either
import com.devexperto.architectcoders.domain.Error
import com.devexperto.architectcoders.domain.Movie

interface MovieRemoteDataSource {
    suspend fun getPopularMovies(region: String): Either<Error, List<Movie>>
}
