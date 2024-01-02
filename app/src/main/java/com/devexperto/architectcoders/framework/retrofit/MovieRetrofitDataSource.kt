package com.devexperto.architectcoders.framework.retrofit

import arrow.core.Either
import com.devexperto.architectcoders.data.datasources.MovieRemoteDataSource
import com.devexperto.architectcoders.di.ApiKey
import com.devexperto.architectcoders.domain.Error
import com.devexperto.architectcoders.domain.Movie
import com.devexperto.architectcoders.framework.tryCall
import javax.inject.Inject

class MovieRetrofitDataSource @Inject constructor(
    @ApiKey private val apiKey: String
) : MovieRemoteDataSource {

    override suspend fun getPopularMovies(region: String): Either<Error, List<Movie>> = tryCall {
        RemoteConnection.service
            .listPopularMovies(apiKey, region)
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
