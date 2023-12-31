package com.devexperto.architectcoders.presentation.detail

import com.devexperto.architectcoders.usecases.RequestMovieUseCase
import com.devexperto.architectcoders.usecases.SwitchFavoriteUseCase
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
object DetailFragmentModule {

    @Provides
    fun provideDetailViewModelFactory(
        requestMovieUseCase: RequestMovieUseCase,
        switchFavoriteUseCase: SwitchFavoriteUseCase
    ) = DetailViewModelFactory(requestMovieUseCase, switchFavoriteUseCase)
}

@Subcomponent(modules = [DetailFragmentModule::class])
interface DetailFragmentComponent {
    val detailViewModelFactory: DetailViewModelFactory
}
