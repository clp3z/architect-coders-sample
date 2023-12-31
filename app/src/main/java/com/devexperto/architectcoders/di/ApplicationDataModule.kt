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

@Module
abstract class ApplicationDataModule {

    @Binds
    abstract fun bindMovieRemoteDataSource(retrofitDataSource: MovieRetrofitDataSource): MovieRemoteDataSource

    @Binds
    abstract fun bindMovieLocalDataSource(dataSource: MovieRoomDataSource): MovieLocalDataSource

    @Binds
    abstract fun bindLocationDataSource(locationDataSource: PlayServicesLocationDataSource): LocationDataSource

    @Binds
    abstract fun bindPermissionChecker(permissionChecker: AndroidPermissionChecker): PermissionChecker
}
