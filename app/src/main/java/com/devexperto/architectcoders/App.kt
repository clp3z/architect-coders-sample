package com.devexperto.architectcoders

import android.app.Application
import androidx.room.Room
import com.devexperto.architectcoders.model.database.MovieDatabase

class App : Application() {

    lateinit var databse: MovieDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        databse = Room.databaseBuilder(
            context = this,
            klass = MovieDatabase::class.java,
            name = "movies-database"
        ).build()
    }
}
