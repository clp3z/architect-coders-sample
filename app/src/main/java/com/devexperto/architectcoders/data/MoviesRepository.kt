package com.devexperto.architectcoders.data

import com.devexperto.architectcoders.App
import com.devexperto.architectcoders.data.datasources.MovieLocalDataSource
import com.devexperto.architectcoders.data.datasources.MovieRemoteDataSource
import com.devexperto.architectcoders.domain.Movie
import com.devexperto.architectcoders.framework.datasources.MovieRetrofitDataSource
import com.devexperto.architectcoders.framework.datasources.MovieRoomDataSource
import kotlinx.coroutines.flow.Flow

class MoviesRepository(application: App) {

    private val localDataSource: MovieLocalDataSource = MovieRoomDataSource(application.movieDAO)
    private val remoteDataSource: MovieRemoteDataSource = MovieRetrofitDataSource("df913d0e8d85eb724270797250eb400f")
    private val regionRepository = RegionRepository(application)

    val popularMovies = localDataSource.movies

    fun requestMovieById(id: Int): Flow<Movie> = localDataSource.get(id)

    suspend fun requestPopularMovies(): Error? = tryCall {
        if (localDataSource.isEmpty()) {
            val movies = remoteDataSource
                .getPopularMovies(regionRepository.findLastRegion())

            localDataSource.save(movies)
        }
    }

    suspend fun switchFavorite(movie: Movie): Error? = tryCall {
        localDataSource.update(movie.copy(isFavorite = !movie.isFavorite))
    }
}
