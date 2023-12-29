package com.devexperto.architectcoders

import android.app.Application
import androidx.room.Room
import com.devexperto.architectcoders.data.MoviesRepository
import com.devexperto.architectcoders.data.PermissionChecker
import com.devexperto.architectcoders.data.RegionRepository
import com.devexperto.architectcoders.data.datasources.LocationDataSource
import com.devexperto.architectcoders.data.datasources.MovieLocalDataSource
import com.devexperto.architectcoders.data.datasources.MovieRemoteDataSource
import com.devexperto.architectcoders.framework.AndroidPermissionChecker
import com.devexperto.architectcoders.framework.PlayServicesLocationDataSource
import com.devexperto.architectcoders.framework.database.MovieDatabase
import com.devexperto.architectcoders.framework.database.MovieRoomDataSource
import com.devexperto.architectcoders.framework.retrofit.MovieRetrofitDataSource
import com.devexperto.architectcoders.presentation.detail.DetailViewModel
import com.devexperto.architectcoders.presentation.main.MainViewModel
import com.devexperto.architectcoders.usecases.GetPopularMoviesUseCase
import com.devexperto.architectcoders.usecases.RequestMovieUseCase
import com.devexperto.architectcoders.usecases.RequestPopularMoviesUseCase
import com.devexperto.architectcoders.usecases.SwitchFavoriteUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

fun Application.initDI() {
    startKoin {
        androidLogger(level = Level.ERROR)
        androidContext(this@initDI)
        modules(appModule, dataModule, useCasesModule)
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

    factory<MovieRemoteDataSource> { MovieRetrofitDataSource(get(named("apiKey"))) }
    factoryOf(::MovieRoomDataSource) bind MovieLocalDataSource::class
    factoryOf(::PlayServicesLocationDataSource) bind LocationDataSource::class
    factoryOf(::AndroidPermissionChecker) bind PermissionChecker::class

    viewModelOf(::MainViewModel)
    viewModelOf(::DetailViewModel)
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
