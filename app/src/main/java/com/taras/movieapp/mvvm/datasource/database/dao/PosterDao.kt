package com.taras.movieapp.mvvm.datasource.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import com.taras.movieapp.mvvm.model.Poster

@Dao
interface PosterDao {

    @Insert(onConflict = REPLACE)
    fun insert(obj: Poster)

    @Insert(onConflict = REPLACE)
    fun insert(obj: List<Poster>)
}