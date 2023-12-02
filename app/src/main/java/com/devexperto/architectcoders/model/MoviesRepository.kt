package com.devexperto.architectcoders.model

import com.devexperto.architectcoders.App
import com.devexperto.architectcoders.model.database.Movie
import com.devexperto.architectcoders.model.datasource.MovieLocalDataSource
import com.devexperto.architectcoders.model.datasource.MovieRemoteDataSource
import kotlinx.coroutines.flow.Flow

class MoviesRepository(application: App) {

    private val localDataSource = MovieLocalDataSource(application.movieDAO)
    private val remoteDataSource = MovieRemoteDataSource("df913d0e8d85eb724270797250eb400f")
    private val regionRepository = RegionRepository(application)

    val popularMovies = localDataSource.movies

    fun requestMovieById(id: Int): Flow<Movie> = localDataSource.get(id)

    suspend fun requestPopularMovies(): Error? = tryCall {
        if (localDataSource.isEmpty()) {
            val movies = remoteDataSource
                .getPopularMovies(regionRepository.findLastRegion())
                .results
            localDataSource.save(movies.map { it.toLocalMovie() })
        }
    }

    suspend fun switchFavorite(movie: Movie): Error? = tryCall {
        localDataSource.update(movie.copy(isFavorite = !movie.isFavorite))
    }
}

private fun RemoteMovie.toLocalMovie() = Movie(
    id = id,
    title = title,
    overview = overview,
    releaseDate = releaseDate,
    originalTitle = originalTitle,
    originalLanguage = originalLanguage,
    popularity = popularity,
    voteAverage = voteAverage,
    posterUrl = posterUrl,
    isFavorite = false
)
