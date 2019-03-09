package com.taras.movieapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.taras.movieapp.data.model.BaseResponse

@Dao
interface BaseResponseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj: BaseResponse)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj: List<BaseResponse>)

    @Query("DELETE FROM tblBaseResponse")
    fun deleteAll()

    @Query("DELETE FROM tblBaseResponse WHERE movieGenre = :movieGenre")
    fun deleteByGenre(movieGenre: String)

    @Query("SELECT * FROM tblBaseResponse")
    fun getResponse(): BaseResponse

    @Query("SELECT * FROM tblBaseResponse WHERE movieGenre = :movieGenre")
    fun getResponseByType(movieGenre: String): BaseResponse
}