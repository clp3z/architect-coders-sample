package com.devexperto.architectcoders.data

import com.devexperto.architectcoders.data.PermissionChecker.Permission.COARSE_LOCATION
import com.devexperto.architectcoders.data.datasources.LocationDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class RegionRepositoryTest {

    @Test
    fun `Returns default region when location permission is not granted`(): Unit = runBlocking {
        val regionRepository = buildRegionRepository(
            permissionChecker = mock { on { it.check(COARSE_LOCATION) } doReturn false }
        )

        val result = regionRepository.getLastRegion()

        assertEquals(RegionRepository.DEFAULT_REGION, result)
    }

    @Test
    fun `Returns region from location data source when location permission is granted`(): Unit = runBlocking {
        val lastRegion = "ES"
        val regionRepository = buildRegionRepository(
            locationDataSource = mock { onBlocking { it.getLastKnownRegion() } doReturn lastRegion },
            permissionChecker = mock { on { it.check(COARSE_LOCATION) } doReturn true }
        )

        val result = regionRepository.getLastRegion()

        assertEquals(lastRegion, result)
    }
}

private fun buildRegionRepository(
    locationDataSource: LocationDataSource = mock(),
    permissionChecker: PermissionChecker = mock()
) = RegionRepository(locationDataSource, permissionChecker)
