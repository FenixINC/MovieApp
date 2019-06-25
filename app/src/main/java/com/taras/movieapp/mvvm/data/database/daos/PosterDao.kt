package com.taras.movieapp.mvvm.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import com.taras.movieapp.mvvm.data.entities.Poster

@Dao
interface PosterDao {

    @Insert(onConflict = REPLACE)
    fun insert(obj: Poster)

    @Insert(onConflict = REPLACE)
    fun insert(obj: List<Poster>)
}