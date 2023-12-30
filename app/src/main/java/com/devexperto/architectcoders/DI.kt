package com.devexperto.architectcoders

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.devexperto.architectcoders.data.DataModule
import com.devexperto.architectcoders.framework.database.MovieDatabase
import com.devexperto.architectcoders.usecases.UseCasesModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.annotation.ComponentScan
import org.koin.core.annotation.Module
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.ksp.generated.module

fun Application.initDI() {
    startKoin {
        androidLogger(level = Level.ERROR)
        androidContext(this@initDI)
        modules(AppModule().module, DataModule().module, UseCasesModule().module)
    }
}

@Module
@ComponentScan
class AppModule {

    @Single
    @Named("apiKey")
    fun apiKey(): String = "df913d0e8d85eb724270797250eb400f"

    @Single
    fun provideRoomDatabase(context: Context): MovieDatabase = Room.databaseBuilder(
        context = context,
        klass = MovieDatabase::class.java,
        name = "movies-database"
    ).build()

    @Single
    fun provideMovieDao(movieDatabase: MovieDatabase) = movieDatabase.movieDao()
}
