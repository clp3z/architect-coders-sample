package com.devexperto.architectcoders.model

import android.annotation.SuppressLint
import android.location.Location
import com.devexperto.architectcoders.ui.main.MainActivity
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

interface LocationDataSource {
    suspend fun accessLastKnownLocation(): Location?
}

class PlayServicesLocationDataSource(activity: MainActivity) : LocationDataSource {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)

    @SuppressLint("MissingPermission")
    override suspend fun accessLastKnownLocation(): Location? =
        suspendCancellableCoroutine { continuation ->
            fusedLocationClient.lastLocation.addOnCompleteListener {
                continuation.resume(it.result)
            }
        }
}
