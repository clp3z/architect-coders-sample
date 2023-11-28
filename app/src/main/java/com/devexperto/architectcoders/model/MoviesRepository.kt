package com.devexperto.architectcoders.model

import com.devexperto.architectcoders.App
import com.devexperto.architectcoders.model.database.Movie
import com.devexperto.architectcoders.model.datasource.MovieLocalDataSource
import com.devexperto.architectcoders.model.datasource.MovieRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class MoviesRepository(application: App) {

    private val localDataSource = MovieLocalDataSource(application.movieDAO)

    private val remoteDataSource = MovieRemoteDataSource(
        apiKey = "df913d0e8d85eb724270797250eb400f",
        regionRepository = RegionRepository(application)
    )

    val popularMovies = localDataSource.movies

    suspend fun requestPopularMovies() = withContext(Dispatchers.IO) {
        if (localDataSource.isEmpty()) {
            val movies = remoteDataSource.getPopularMovies().results
            localDataSource.save(movies.map { it.toLocalMovie() })
        }
    }

    suspend fun requestMovieById(id: Int): Flow<Movie> = withContext(Dispatchers.IO) {
        localDataSource.getById(id)
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
    posterUrl = posterUrl
)
