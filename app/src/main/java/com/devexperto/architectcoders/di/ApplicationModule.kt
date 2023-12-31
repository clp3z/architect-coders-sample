package com.devexperto.architectcoders.di

import android.app.Application
import androidx.room.Room
import com.devexperto.architectcoders.framework.database.MovieDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object ApplicationModule {

    @Provides
    @Singleton
    @ApiKey
    fun provideApiKey() = "df913d0e8d85eb724270797250eb400f"

    @Provides
    @Singleton
    fun provideRoomDatabase(application: Application) =
        Room.databaseBuilder(
            context = application,
            klass = MovieDatabase::class.java,
            name = "movies-database"
        ).build()

    @Provides
    @Singleton
    fun provideMovieDAO(database: MovieDatabase) = database.movieDao()
}
