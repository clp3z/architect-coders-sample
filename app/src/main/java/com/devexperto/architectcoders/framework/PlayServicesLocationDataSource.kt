package com.devexperto.architectcoders.framework

import android.annotation.SuppressLint
import android.app.Application
import android.location.Geocoder
import android.location.Location
import com.devexperto.architectcoders.data.datasources.LocationDataSource
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class PlayServicesLocationDataSource @Inject constructor(
    application: Application
) : LocationDataSource {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)
    private val geocoder = Geocoder(application)

    override suspend fun getLastKnownRegion(): String? = getLastKnownLocation().toRegion()

    @SuppressLint("MissingPermission")
    private suspend fun getLastKnownLocation(): Location? =
        suspendCancellableCoroutine { continuation ->
            fusedLocationClient.lastLocation.addOnCompleteListener {
                continuation.resume(it.result)
            }
        }

    @Suppress("DEPRECATION")
    private fun Location?.toRegion(): String? {
        val addresses = this?.let {
            geocoder.getFromLocation(latitude, longitude, 1)
        }
        return addresses?.firstOrNull()?.countryCode
    }
}
