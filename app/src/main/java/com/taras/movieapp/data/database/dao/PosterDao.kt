package com.taras.movieapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import com.taras.movieapp.data.model.Poster

@Dao
interface PosterDao {

    @Insert(onConflict = REPLACE)
    fun insert(obj: Poster)

    @Insert(onConflict = REPLACE)
    fun insert(obj: List<Poster>)
}