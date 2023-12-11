package com.devexperto.architectcoders.data.datasources

import android.location.Location

interface LocationDataSource {
    suspend fun accessLastKnownLocation(): Location?
}
