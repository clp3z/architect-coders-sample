package com.devexperto.architectcoders.di

import com.devexperto.architectcoders.presentation.detail.DetailViewModelFactory
import com.devexperto.architectcoders.presentation.main.MainViewModelFactory
import com.devexperto.architectcoders.usecases.GetPopularMoviesUseCase
import com.devexperto.architectcoders.usecases.RequestMovieUseCase
import com.devexperto.architectcoders.usecases.RequestPopularMoviesUseCase
import com.devexperto.architectcoders.usecases.SwitchFavoriteUseCase
import dagger.Module
import dagger.Provides

@Module
object ViewModelsModule {

    @Provides
    fun provideMainViewModelFactory(
        getPopularMoviesUseCase: GetPopularMoviesUseCase,
        requestPopularMoviesUseCase: RequestPopularMoviesUseCase
    ) = MainViewModelFactory(getPopularMoviesUseCase, requestPopularMoviesUseCase)

    @Provides
    fun provideDetailViewModelFactory(
        requestMovieUseCase: RequestMovieUseCase,
        switchFavoriteUseCase: SwitchFavoriteUseCase
    ) = DetailViewModelFactory(requestMovieUseCase, switchFavoriteUseCase)
}
