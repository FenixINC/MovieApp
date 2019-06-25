package com.taras.movieapp.mvvm.data.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "tblPoster")
@Parcelize
data class Poster(

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Long,

    @ColumnInfo(name = "imageId")
    @SerializedName("image_id")
    var imageId: Long,

    @ColumnInfo(name = "contentId")
    @SerializedName("content_id")
    var contentId: Long,

    @ColumnInfo(name = "parentId")
    @SerializedName("parent_id")
    var parentId: Long,

    @ColumnInfo(name = "type")
    @SerializedName("type")
    var type: String,

    @ColumnInfo(name = "width")
    @SerializedName("width")
    var width: Int,

    @ColumnInfo(name = "height")
    @SerializedName("height")
    var height: Int,

    @ColumnInfo(name = "couchdbUrl")
    @SerializedName("couchdb_url")
    var couchdbUrl: String,

    @ColumnInfo(name = "url")
    @SerializedName("url")
    var url: String,

    @ColumnInfo(name = "sourceUrl")
    @SerializedName("source_url")
    var sourceUrl: String,

    @ColumnInfo(name = "isSynchronized")
    @SerializedName("is_synchronized")
    var isSynchronized: Boolean/*,

    @ColumnInfo(name = "thumbs")
    @SerializedName("thumbs")
    var thumbList: @RawValue Thumb*/
) : Parcelable {
    constructor() : this(
        0L, 0L, 0L, 0L, "", 0, 0, "", "", "", false
    )
}