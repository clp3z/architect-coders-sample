package com.devexperto.architectcoders.data

import com.devexperto.architectcoders.data.PermissionChecker.Permission.COARSE_LOCATION
import com.devexperto.architectcoders.data.datasources.LocationDataSource
import javax.inject.Inject

class RegionRepository @Inject constructor(
    private val locationDataSource: LocationDataSource,
    private val locationPermissionChecker: PermissionChecker
) {

    companion object {
        const val DEFAULT_REGION = "US"
    }

    suspend fun getLastRegion(): String {
        return if (locationPermissionChecker.check(COARSE_LOCATION)) {
            locationDataSource.getLastKnownRegion() ?: DEFAULT_REGION
        } else {
            DEFAULT_REGION
        }
    }
}
