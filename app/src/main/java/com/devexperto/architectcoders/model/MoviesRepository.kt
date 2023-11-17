package com.devexperto.architectcoders.model

import android.app.Application

class MoviesRepository(application: Application) {

    private val apiKey = "df913d0e8d85eb724270797250eb400f"
    private val regionRepository = RegionRepository(application)

    suspend fun findPopularMovies(): RemoteResult {
        return RemoteConnection.service
            .listPopularMovies(
                apiKey = apiKey,
                region = regionRepository.findLastRegion()
            )
    }
}
