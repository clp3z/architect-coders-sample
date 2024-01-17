package com.devexperto.architectcoders.presentation.fakes

import com.devexperto.architectcoders.data.datasources.MovieLocalDataSource
import com.devexperto.architectcoders.domain.Error
import com.devexperto.architectcoders.domain.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeLocalDataSource : MovieLocalDataSource {

    val inMemoryMovies = MutableStateFlow<List<Movie>>(emptyList())

    override val movies = inMemoryMovies

    private lateinit var movieFlow: MutableStateFlow<Movie>

    override fun get(id: Int): Flow<Movie> {
        movieFlow = MutableStateFlow(inMemoryMovies.value.first { it.id == id })
        return movieFlow
    }

    override suspend fun isEmpty() = movies.value.isEmpty()

    override suspend fun save(movies: List<Movie>): Error? {
        inMemoryMovies.value = movies
        return null
    }

    override suspend fun update(movie: Movie): Error? {
        val updatedMovies = inMemoryMovies.value.toMutableList()
        val index = inMemoryMovies.value.indexOfFirst { it.id == movie.id }
        updatedMovies[index] = movie
        inMemoryMovies.value = updatedMovies

        if (::movieFlow.isInitialized) {
            movieFlow.value = movie
        }

        return null
    }
}
