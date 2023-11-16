package com.devexperto.architectcoders.model

import androidx.appcompat.app.AppCompatActivity

class MoviesRepository(activity: AppCompatActivity) {

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
