package com.devexperto.architectcoders.data.datasources

import com.devexperto.architectcoders.domain.Error
import com.devexperto.architectcoders.domain.Movie
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {
    val movies: Flow<List<Movie>>

    fun get(id: Int): Flow<Movie>

    suspend fun isEmpty(): Boolean

    suspend fun save(movies: List<Movie>): Error?

    suspend fun update(movie: Movie): Error?
}
