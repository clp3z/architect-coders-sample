package com.devexperto.architectcoders.di

import android.app.Application
import com.devexperto.architectcoders.presentation.detail.DetailFragmentComponent
import com.devexperto.architectcoders.presentation.detail.DetailFragmentModule
import com.devexperto.architectcoders.presentation.main.MainFragmentComponent
import com.devexperto.architectcoders.presentation.main.MainFragmentModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        DataModule::class,
        UseCasesModule::class
    ]
)
interface ApplicationComponent {

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance application: Application): ApplicationComponent
    }

    fun plus(mainFragmentModule: MainFragmentModule): MainFragmentComponent

    fun plus(detailFragmentModule: DetailFragmentModule): DetailFragmentComponent
}
