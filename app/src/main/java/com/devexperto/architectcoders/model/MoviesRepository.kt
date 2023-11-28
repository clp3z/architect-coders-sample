package com.devexperto.architectcoders.model

import com.devexperto.architectcoders.App
import com.devexperto.architectcoders.model.database.Movie
import com.devexperto.architectcoders.model.database.MovieDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import com.devexperto.architectcoders.model.Movie as RemoteMovie

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
}

class MovieLocalDataSource(private val movieDAO: MovieDAO) {
    val movies: Flow<List<Movie>> = movieDAO.getMovies()

    fun isEmpty() = movieDAO.getMoviesCount() == 0

    fun save(movies: List<Movie>) {
        movieDAO.insertMovie(movies)
    }
}

class MovieRemoteDataSource(
    private val apiKey: String,
    private val regionRepository: RegionRepository
) {

    suspend fun getPopularMovies(): RemoteResult {
        return RemoteConnection.service
            .listPopularMovies(
                apiKey = apiKey,
                region = regionRepository.findLastRegion()
            )
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
