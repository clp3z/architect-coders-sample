package com.devexperto.architectcoders.framework.database

import com.devexperto.architectcoders.data.datasources.MovieLocalDataSource
import com.devexperto.architectcoders.domain.Error
import com.devexperto.architectcoders.domain.Movie
import com.devexperto.architectcoders.framework.tryCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import com.devexperto.architectcoders.framework.database.Movie as DatabaseMovie

class MovieRoomDataSource @Inject constructor(
    private val movieDAO: MovieDAO
) : MovieLocalDataSource {

    override val movies: Flow<List<Movie>> = movieDAO.getMovies().toDomainModel()

    override fun get(id: Int): Flow<Movie> = movieDAO.getMovie(id).map { it.toDomainModel() }

    override suspend fun isEmpty() = movieDAO.getMoviesCount() == 0

    override suspend fun save(movies: List<Movie>): Error? = tryCall {
        movieDAO.insertMovie(movies.fromDomainModel())
    }.fold(
        ifLeft = { it },
        ifRight = { null }
    )

    override suspend fun update(movie: Movie): Error? = tryCall {
        movieDAO.updateMovie(movie.fromDomainModel())
    }.fold(
        ifLeft = { it },
        ifRight = { null }
    )
}

private fun DatabaseMovie.toDomainModel() = Movie(
    id = id,
    title = title,
    overview = overview,
    releaseDate = releaseDate,
    posterUrl = posterUrl,
    originalTitle = originalTitle,
    originalLanguage = originalLanguage,
    popularity = popularity,
    voteAverage = voteAverage,
    isFavorite = isFavorite
)

private fun Movie.fromDomainModel() = DatabaseMovie(
    id = id,
    title = title,
    overview = overview,
    releaseDate = releaseDate,
    posterUrl = posterUrl,
    originalTitle = originalTitle,
    originalLanguage = originalLanguage,
    popularity = popularity,
    voteAverage = voteAverage,
    isFavorite = isFavorite
)

private fun Flow<List<DatabaseMovie>>.toDomainModel() = map {
    it.map { movie -> movie.toDomainModel() }
}

private fun List<Movie>.fromDomainModel() = map { it.fromDomainModel() }
