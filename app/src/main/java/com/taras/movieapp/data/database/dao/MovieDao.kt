package com.taras.movieapp.data.database.dao

import androidx.lifecycle.LiveData
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

    @Query("DELETE FROM tblMovie WHERE movieGenre = :movieGenre")
    fun deleteByGenre(movieGenre: String)

    @Query("SELECT * FROM tblMovie")
    fun getMovieList(): LiveData<List<Movie>>

    @Query("SELECT * FROM tblMovie WHERE movieGenre = :movieGenre")
    fun getMoviesByGenre(movieGenre: String): LiveData<List<Movie>>
}