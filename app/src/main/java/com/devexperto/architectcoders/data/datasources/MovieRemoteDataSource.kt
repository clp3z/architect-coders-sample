package com.devexperto.architectcoders.data.datasources

import com.devexperto.architectcoders.domain.Movie

interface MovieRemoteDataSource {
    suspend fun getPopularMovies(region: String): List<Movie>
}
