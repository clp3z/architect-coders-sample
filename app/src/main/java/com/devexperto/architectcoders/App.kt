package com.devexperto.architectcoders

import android.app.Application
import androidx.room.Room
import com.devexperto.architectcoders.data.database.MovieDatabase

class App : Application() {

    private lateinit var movieDatabase: MovieDatabase
    val movieDAO by lazy { movieDatabase.movieDao() }

    override fun onCreate() {
        super.onCreate()

        movieDatabase = Room.databaseBuilder(
            context = this,
            klass = MovieDatabase::class.java,
            name = "movies-database"
        ).build()
    }
}
