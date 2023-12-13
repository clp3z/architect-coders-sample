package com.devexperto.architectcoders.framework.datasources

import com.devexperto.architectcoders.data.datasources.MovieRemoteDataSource
import com.devexperto.architectcoders.domain.Movie
import com.devexperto.architectcoders.framework.retrofit.RemoteConnection
import com.devexperto.architectcoders.framework.retrofit.RemoteMovie

class MovieRetrofitDataSource(private val apiKey: String) : MovieRemoteDataSource {

    override suspend fun getPopularMovies(region: String): List<Movie> {
        return RemoteConnection.service
            .listPopularMovies(
                apiKey = apiKey,
                region = region
            )
            .results
            .toDomainModel()
    }
}

private fun RemoteMovie.toDomainModel() = Movie(
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

private fun List<RemoteMovie>.toDomainModel() = map { it.toDomainModel() }
