package com.devexperto.architectcoders.data

import com.devexperto.architectcoders.data.datasources.MovieLocalDataSource
import com.devexperto.architectcoders.data.datasources.MovieRemoteDataSource
import com.devexperto.architectcoders.domain.Error
import com.devexperto.architectcoders.domain.Movie
import kotlinx.coroutines.flow.Flow

class MoviesRepository(
    private val regionRepository: RegionRepository,
    private val localDataSource: MovieLocalDataSource,
    private val remoteDataSource: MovieRemoteDataSource
) {

    val popularMovies = localDataSource.movies

    fun requestMovieById(id: Int): Flow<Movie> = localDataSource.get(id)

    suspend fun requestPopularMovies(): Error? {
        if (localDataSource.isEmpty()) {
            val movies = remoteDataSource.getPopularMovies(regionRepository.getLastRegion())
            movies.fold(ifLeft = { return it }) {
                localDataSource.save(it)
            }
        }
        return null
    }

    suspend fun switchFavorite(movie: Movie): Error? =
        localDataSource.update(movie.copy(isFavorite = !movie.isFavorite))
}
