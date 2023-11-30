package com.devexperto.architectcoders.model.datasource

import com.devexperto.architectcoders.model.database.Movie
import com.devexperto.architectcoders.model.database.MovieDAO
import kotlinx.coroutines.flow.Flow

class MovieLocalDataSource(private val movieDAO: MovieDAO) {

    val movies: Flow<List<Movie>> = movieDAO.getMovies()

    fun get(id: Int): Flow<Movie> = movieDAO.getMovie(id)

    suspend fun isEmpty() = movieDAO.getMoviesCount() == 0

    suspend fun save(movies: List<Movie>) = movieDAO.insertMovie(movies)

    suspend fun update(movie: Movie) = movieDAO.updateMovie(movie)
}
