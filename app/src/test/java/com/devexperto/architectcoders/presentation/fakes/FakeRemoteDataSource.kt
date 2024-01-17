package com.devexperto.architectcoders.presentation.fakes

import arrow.core.Either
import arrow.core.right
import com.devexperto.architectcoders.data.datasources.MovieRemoteDataSource
import com.devexperto.architectcoders.domain.Error
import com.devexperto.architectcoders.domain.Movie
import com.devexperto.architectcoders.testShared.sampleMovieList

class FakeRemoteDataSource : MovieRemoteDataSource {

    var movies = sampleMovieList

    override suspend fun getPopularMovies(region: String): Either<Error, List<Movie>> =
        movies.right()
}
