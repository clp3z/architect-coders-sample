package com.devexperto.architectcoders.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDAO {

    @Query("SELECT * FROM Movie")
    fun getMovies(): Flow<List<Movie>>

    @Query("SELECT * FROM Movie WHERE id = :id")
    fun getMovie(id: Int): Flow<Movie>

    @Query("SELECT COUNT(id) FROM Movie")
    fun getMoviesCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movies: List<Movie>)

    @Update
    fun updateMovie(movie: Movie)
}
