package com.devexperto.architectcoders.data.datasources

interface LocationDataSource {
    suspend fun getLastKnownRegion(): String?
}
