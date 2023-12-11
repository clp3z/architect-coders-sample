package com.devexperto.architectcoders.data.datasources

import com.devexperto.architectcoders.domain.Movie
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {
    val movies: Flow<List<Movie>>

    fun get(id: Int): Flow<Movie>

    suspend fun isEmpty(): Boolean

    suspend fun save(movies: List<Movie>)

    suspend fun update(movie: Movie)
}
