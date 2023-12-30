package com.devexperto.architectcoders

import android.app.Application
import com.devexperto.architectcoders.di.ApplicationComponent
import com.devexperto.architectcoders.di.DaggerApplicationComponent

class App : Application() {

    lateinit var applicationComponent: ApplicationComponent
        private set

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent.factory().create(this)
    }
}
