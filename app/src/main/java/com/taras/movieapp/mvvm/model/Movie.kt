package com.taras.movieapp.mvvm.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Entity(tableName = "tblMovie")
@Parcelize
data class Movie(

        @PrimaryKey
        @ColumnInfo(name = "id")
        @SerializedName("id")
        var id: Long,

        @ColumnInfo(name = "movieGenre")
        var movieGenre: String,

        @ColumnInfo(name = "vendorkaId")
        @SerializedName("vendorka_id")
        var vendorkaId: Long,

        @ColumnInfo(name = "title")
        @SerializedName("title")
        var title: String,

        @ColumnInfo(name = "slug")
        @SerializedName("slug")
        var slug: String,

        @ColumnInfo(name = "description")
        @SerializedName("description")
        var description: String,

        @ColumnInfo(name = "createdAt")
        @SerializedName("created_at")
        var createdAt: Long,

        @ColumnInfo(name = "updatedAt")
        @SerializedName("updated_at")
        var updatedAt: Long,

//        @ColumnInfo(name = "deletedAt")
        @Ignore
        @SerializedName("deleted_at")
        var deletedAt: String,

//    @ColumnInfo(name = "isEnabled")
//    @SerializedName("is_enabled")
//    var isEnabled: Int,

        @ColumnInfo(name = "isFeatured")
        @SerializedName("is_featured")
        var isFeatured: Boolean,

        @ColumnInfo(name = "vendorId")
        @SerializedName("vendor_id")
        var vendorId: Long,

        @ColumnInfo(name = "metaKeywords")
        @SerializedName("meta_keywords")
        var metaKeywords: String,

        @ColumnInfo(name = "metaDescription")
        @SerializedName("meta_description")
        var metaDescription: String,

        @ColumnInfo(name = "parentId")
        @SerializedName("parent_id")
        var parentId: Long,

        @ColumnInfo(name = "typeId")
        @SerializedName("type_id")
        var typeId: Long,

        @ColumnInfo(name = "type")
        @SerializedName("type")
        var type: String,

        @ColumnInfo(name = "episodeNumber")
        @SerializedName("episode_number")
        var episodeNumber: Long,

        @ColumnInfo(name = "seriesNumber")
        @SerializedName("series_number")
        var seriesNumber: Long,

        @ColumnInfo(name = "episodesCount")
        @SerializedName("episodes_count")
        var episodesCount: Long,

//    @Ignore
//    @SerializedName("genres")
//    var genreList: @RawValue List<Genre>

        @Ignore
        @SerializedName("poster")
        var poster: @RawValue Poster
) : Parcelable {
    constructor() : this(
            0L, "", 0L, "", "", "", 0L, 0L,
            "", false, 0, "", "", 0L,
            0, "", 0L, 0L, 0L, Poster()
    )
}