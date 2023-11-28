package com.devexperto.architectcoders.model.datasource

import com.devexperto.architectcoders.model.RegionRepository
import com.devexperto.architectcoders.model.RemoteConnection
import com.devexperto.architectcoders.model.RemoteResult

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
