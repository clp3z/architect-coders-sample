package com.devexperto.architectcoders.data

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.app.Application
import android.location.Geocoder
import android.location.Location

class RegionRepository(application: Application) {

    companion object {
        const val DEFAULT_REGION = "US"
    }

    private val locationDataSource = PlayServicesLocationDataSource(application)
    private val locationPermissionChecker = PermissionChecker(application, ACCESS_COARSE_LOCATION)
    private val geocoder = Geocoder(application)

    suspend fun findLastRegion(): String = findLastLocation().toRegion()

    private suspend fun findLastLocation(): Location? {
        val isGranted = locationPermissionChecker.check()
        return if (isGranted) locationDataSource.accessLastKnownLocation() else null
    }

    @Suppress("DEPRECATION")
    private fun Location?.toRegion(): String {
        val addresses = this?.let {
            geocoder.getFromLocation(latitude, longitude, 1)
        }
        return addresses?.firstOrNull()?.countryCode ?: DEFAULT_REGION
    }
}
