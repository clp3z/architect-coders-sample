package com.devexperto.architectcoders.di

import com.devexperto.architectcoders.data.PermissionChecker
import com.devexperto.architectcoders.data.datasources.LocationDataSource
import com.devexperto.architectcoders.data.datasources.MovieLocalDataSource
import com.devexperto.architectcoders.data.datasources.MovieRemoteDataSource
import com.devexperto.architectcoders.framework.AndroidPermissionChecker
import com.devexperto.architectcoders.framework.PlayServicesLocationDataSource
import com.devexperto.architectcoders.framework.database.MovieRoomDataSource
import com.devexperto.architectcoders.framework.retrofit.MovieRetrofitDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ApplicationDataModule {

    @Binds
    abstract fun bindLocalDataSource(localDataSource: MovieRoomDataSource): MovieLocalDataSource

    @Binds
    abstract fun bindRemoteDataSource(remoteDataSource: MovieRetrofitDataSource): MovieRemoteDataSource

    @Binds
    abstract fun bindLocationDataSource(locationDataSource: PlayServicesLocationDataSource): LocationDataSource

    @Binds
    abstract fun bindPermissionsChecker(permissionChecker: AndroidPermissionChecker): PermissionChecker
}
