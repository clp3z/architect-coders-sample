package com.devexperto.architectcoders.di

import android.app.Application
import com.devexperto.architectcoders.presentation.detail.DetailViewModelFactory
import com.devexperto.architectcoders.presentation.main.MainViewModelFactory
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        DataModule::class,
        UseCasesModule::class,
        ViewModelsModule::class,
    ]
)
interface ApplicationComponent {

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance application: Application): ApplicationComponent
    }

    val mainViewModelFactoryFactory: MainViewModelFactory
    val detailViewModelFactoryFactory: DetailViewModelFactory
}
