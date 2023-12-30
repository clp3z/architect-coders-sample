package com.devexperto.architectcoders.di

import com.devexperto.architectcoders.data.MoviesRepository
import com.devexperto.architectcoders.usecases.GetPopularMoviesUseCase
import com.devexperto.architectcoders.usecases.RequestMovieUseCase
import com.devexperto.architectcoders.usecases.RequestPopularMoviesUseCase
import com.devexperto.architectcoders.usecases.SwitchFavoriteUseCase
import dagger.Module
import dagger.Provides

@Module
object UseCasesModule {

    @Provides
    fun provideGetPopularMoviesUseCase(moviesRepository: MoviesRepository) =
        GetPopularMoviesUseCase(moviesRepository)

    @Provides
    fun provideRequestMovieUseCase(moviesRepository: MoviesRepository) =
        RequestMovieUseCase(moviesRepository)

    @Provides
    fun provideRequestPopularMoviesUseCase(moviesRepository: MoviesRepository) =
        RequestPopularMoviesUseCase(moviesRepository)

    @Provides
    fun provideSwitchFavoriteUseCase(moviesRepository: MoviesRepository) =
        SwitchFavoriteUseCase(moviesRepository)
}
