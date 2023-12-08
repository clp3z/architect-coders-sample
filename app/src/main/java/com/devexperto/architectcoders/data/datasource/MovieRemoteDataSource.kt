package com.devexperto.architectcoders.data.datasource

import com.devexperto.architectcoders.data.RemoteConnection
import com.devexperto.architectcoders.data.RemoteResult

class MovieRemoteDataSource(private val apiKey: String) {

    suspend fun getPopularMovies(region: String): RemoteResult {
        return RemoteConnection.service
            .listPopularMovies(
                apiKey = apiKey,
                region = region
            )
    }
}
