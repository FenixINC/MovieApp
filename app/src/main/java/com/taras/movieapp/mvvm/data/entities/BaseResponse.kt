package com.taras.movieapp.mvvm.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tblBaseResponse")
class BaseResponse(

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Long,

    @ColumnInfo(name = "movieGenre")
    var movieGenre: String,

    @ColumnInfo(name = "code")
    @SerializedName("code")
    var code: Int,

    @ColumnInfo(name = "reason")
    @SerializedName("reason")
    var reason: String,

    @ColumnInfo(name = "total")
    @SerializedName("total")
    var total: Int,

    @ColumnInfo(name = "total_found")
    @SerializedName("total_found")
    var totalFound: Int,

    @Ignore
    @SerializedName("response")
    var responseList: List<Movie>
) {
    constructor() : this(0L, "", 0, "", 0, 0, ArrayList<Movie>())
}