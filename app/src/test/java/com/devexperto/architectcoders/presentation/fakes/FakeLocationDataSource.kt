package com.devexperto.architectcoders.presentation.fakes

import com.devexperto.architectcoders.data.datasources.LocationDataSource

class FakeLocationDataSource : LocationDataSource {

    var location = "US"

    override suspend fun getLastKnownRegion(): String = location
}
