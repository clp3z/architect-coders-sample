package com.devexperto.architectcoders.di

import android.app.Application
import androidx.room.Room
import com.devexperto.architectcoders.framework.database.MovieDAO
import com.devexperto.architectcoders.framework.database.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    @ApiKey
    fun provideApiKey(): String = "df913d0e8d85eb724270797250eb400f"

    @Provides
    @Singleton
    fun provideRoomDatabase(application: Application): MovieDatabase = Room.databaseBuilder(
        context = application,
        klass = MovieDatabase::class.java,
        name = "movies-database"
    ).build()

    @Provides
    @Singleton
    fun provideMovieDatabaseDAO(database: MovieDatabase): MovieDAO = database.movieDao()
}
