package com.taras.movieapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.taras.movieapp.data.model.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj: Movie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj: List<Movie>)

    @Query("DELETE FROM tblMovie")
    fun deleteAll()

    @Query("SELECT * FROM tblMovie")
    fun getMovieList(): List<Movie>
}