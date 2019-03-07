package com.taras.movieapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.taras.movieapp.data.model.Poster

@Dao
interface PosterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj: Poster)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj: List<Poster>)
}