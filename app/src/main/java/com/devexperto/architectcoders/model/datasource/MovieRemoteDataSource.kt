package com.devexperto.architectcoders.model.datasource

import com.devexperto.architectcoders.model.RemoteConnection
import com.devexperto.architectcoders.model.RemoteResult

class MovieRemoteDataSource(private val apiKey: String) {

    suspend fun getPopularMovies(region: String): RemoteResult {
        return RemoteConnection.service
            .listPopularMovies(
                apiKey = apiKey,
                region = region
            )
    }
}
