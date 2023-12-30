package com.devexperto.architectcoders.di

import android.app.Application
import androidx.room.Room
import com.devexperto.architectcoders.data.PermissionChecker
import com.devexperto.architectcoders.data.datasources.LocationDataSource
import com.devexperto.architectcoders.data.datasources.MovieLocalDataSource
import com.devexperto.architectcoders.data.datasources.MovieRemoteDataSource
import com.devexperto.architectcoders.framework.AndroidPermissionChecker
import com.devexperto.architectcoders.framework.PlayServicesLocationDataSource
import com.devexperto.architectcoders.framework.database.MovieDatabase
import com.devexperto.architectcoders.framework.database.MovieRoomDataSource
import com.devexperto.architectcoders.framework.retrofit.MovieRetrofitDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
object ApplicationModule {

    @Provides
    @Singleton
    @Named("apiKey")
    fun provideApiKey() = "b1b15e88fa797225412429c1c50c122a"

    @Provides
    @Singleton
    fun provideRoomDatabase(application: Application) =
        Room.databaseBuilder(
            context = application,
            klass = MovieDatabase::class.java,
            name = "movies-database"
        ).build()

    @Provides
    fun provideRemoteDataSource(@Named("apiKey") apiKey: String): MovieRemoteDataSource =
        MovieRetrofitDataSource(apiKey)

    @Provides
    fun provideMovieLocalDataSource(movieDatabase: MovieDatabase): MovieLocalDataSource =
        MovieRoomDataSource(movieDatabase.movieDao())

    @Provides
    fun provideLocationDataSource(application: Application): LocationDataSource =
        PlayServicesLocationDataSource(application)

    @Provides
    fun providePermissionChecker(application: Application): PermissionChecker =
        AndroidPermissionChecker(application)
}
