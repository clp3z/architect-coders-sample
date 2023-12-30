package com.devexperto.architectcoders

import android.app.Application
import androidx.room.Room
import com.devexperto.architectcoders.data.MoviesRepository
import com.devexperto.architectcoders.data.RegionRepository
import com.devexperto.architectcoders.framework.database.MovieDatabase
import com.devexperto.architectcoders.usecases.GetPopularMoviesUseCase
import com.devexperto.architectcoders.usecases.RequestMovieUseCase
import com.devexperto.architectcoders.usecases.RequestPopularMoviesUseCase
import com.devexperto.architectcoders.usecases.SwitchFavoriteUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.ksp.generated.defaultModule

fun Application.initDI() {
    startKoin {
        androidLogger(level = Level.ERROR)
        androidContext(this@initDI)
        modules(appModule, dataModule, useCasesModule, defaultModule)
    }
}

private val appModule = module {
    single(named("apiKey")) { "df913d0e8d85eb724270797250eb400f" }

    single {
        Room.databaseBuilder(
            context = get(),
            klass = MovieDatabase::class.java,
            name = "movies-database"
        ).build()
    }

    single { get<MovieDatabase>().movieDao() }
}

private val dataModule = module {
    factoryOf(::RegionRepository)
    factoryOf(::MoviesRepository)
}

private val useCasesModule = module {
    factoryOf(::GetPopularMoviesUseCase)
    factoryOf(::RequestMovieUseCase)
    factoryOf(::RequestPopularMoviesUseCase)
    factoryOf(::SwitchFavoriteUseCase)
}
