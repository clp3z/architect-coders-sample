package com.devexperto.architectcoders.model

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.location.Geocoder
import android.location.Location
import com.devexperto.architectcoders.ui.MainActivity

class RegionRepository(activity: MainActivity) {

    companion object {
        const val DEFAULT_REGION = "US"
    }

    private val locationDataSource = PlayServicesLocationDataSource(activity)
    private val locationPermissionChecker = PermissionChecker(activity, ACCESS_COARSE_LOCATION)
    private val geocoder = Geocoder(activity)

    suspend fun findLastRegion(): String = findLastLocation().toRegion()

    private suspend fun findLastLocation(): Location? {
        val isGranted = locationPermissionChecker.request()
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
