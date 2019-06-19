package com.taras.movieapp.mvvm.datasource.database.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.taras.movieapp.mvvm.model.Movie

@Dao
interface MovieDao {

    @Insert(onConflict = REPLACE)
    fun insert(obj: Movie)

    @Insert(onConflict = REPLACE)
    fun insert(obj: List<Movie>)

    @Query("DELETE FROM tblMovie")
    fun deleteAll()

    @Query("DELETE FROM tblMovie WHERE movieGenre = :movieGenre")
    fun deleteByGenre(movieGenre: String)

    @Query("SELECT * FROM tblMovie")
    fun getMovieList(): LiveData<List<Movie>>

    @Query("SELECT * FROM tblMovie WHERE movieGenre = :movieGenre")
    fun getMoviesByGenre(movieGenre: String): LiveData<List<Movie>>

    @Query("SELECT * FROM tblMovie WHERE movieGenre = :movieGenre")
    fun getPagedMoviesByGenre(movieGenre: String): DataSource.Factory<Int, Movie>

    // Suspend functions:
    @Query("SELECT * FROM tblMovie WHERE movieGenre = :movieGenre")
    /*suspend*/ fun getMoviesByGenreSuspend(movieGenre: String): List<Movie>
}