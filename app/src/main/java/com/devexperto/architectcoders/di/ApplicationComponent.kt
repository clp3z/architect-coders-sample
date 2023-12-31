package com.devexperto.architectcoders.di

import android.app.Application
import com.devexperto.architectcoders.presentation.detail.DetailFragment
import com.devexperto.architectcoders.presentation.main.MainFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules =
    [
        ApplicationModule::class,
        ApplicationDataModule::class
    ]
)
interface ApplicationComponent {

    @Component.Factory
    interface Factory {

        fun create(@BindsInstance application: Application): ApplicationComponent
    }

    fun inject(mainFragment: MainFragment)
    fun inject(mainFragment: DetailFragment)
}
