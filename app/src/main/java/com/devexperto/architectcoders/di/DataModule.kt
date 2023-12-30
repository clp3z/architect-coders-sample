package com.devexperto.architectcoders.di

import com.devexperto.architectcoders.data.MoviesRepository
import com.devexperto.architectcoders.data.PermissionChecker
import com.devexperto.architectcoders.data.RegionRepository
import com.devexperto.architectcoders.data.datasources.LocationDataSource
import com.devexperto.architectcoders.data.datasources.MovieLocalDataSource
import com.devexperto.architectcoders.data.datasources.MovieRemoteDataSource
import dagger.Module
import dagger.Provides

@Module
object DataModule {

    @Provides
    fun provideRegionsRepository(
        locationDataSource: LocationDataSource,
        permissionChecker: PermissionChecker
    ) = RegionRepository(locationDataSource, permissionChecker)

    @Provides
    fun provideMoviesRepository(
        regionRepository: RegionRepository,
        movieLocalDataSource: MovieLocalDataSource,
        movieRemoteDataSource: MovieRemoteDataSource
    ) = MoviesRepository(regionRepository, movieLocalDataSource, movieRemoteDataSource)
}
