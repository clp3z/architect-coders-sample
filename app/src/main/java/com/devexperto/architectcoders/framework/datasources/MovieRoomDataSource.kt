package com.devexperto.architectcoders.framework.datasources

import com.devexperto.architectcoders.data.datasources.MovieLocalDataSource
import com.devexperto.architectcoders.domain.Movie
import com.devexperto.architectcoders.framework.database.MovieDAO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.devexperto.architectcoders.framework.database.Movie as DatabaseMovie

class MovieRoomDataSource(private val movieDAO: MovieDAO) : MovieLocalDataSource {

    override val movies: Flow<List<Movie>> = movieDAO.getMovies().toDomainModel()

    override fun get(id: Int): Flow<Movie> = movieDAO.getMovie(id).map { it.toDomainModel() }

    override suspend fun isEmpty() = movieDAO.getMoviesCount() == 0

    override suspend fun save(movies: List<Movie>) = movieDAO.insertMovie(movies.fromDomainModel())

    override suspend fun update(movie: Movie) = movieDAO.updateMovie(movie.fromDomainModel())
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
