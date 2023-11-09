package com.devexperto.architectcoders.model

import com.devexperto.architectcoders.ui.MainActivity

class MoviesRepository(activity: MainActivity) {

    private val apiKey = "df913d0e8d85eb724270797250eb400f"
    private val regionRepository = RegionRepository(activity)

    suspend fun findPopularMovies(): RemoteResult {
        return RemoteConnection.service
            .listPopularMovies(
                apiKey = apiKey,
                region = regionRepository.findLastRegion()
            )
    }
}
