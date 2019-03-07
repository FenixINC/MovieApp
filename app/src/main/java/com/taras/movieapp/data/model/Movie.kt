package com.taras.movieapp.data.model

import com.google.gson.annotations.SerializedName

data class Movie(

//    @SerializedName("id")
//    var id: Long,
//
    @SerializedName("vendorka_id")
    var vendorkaId: Long,

    @SerializedName("title")
    var title: String,

    @SerializedName("slug")
    var slug: String,

    @SerializedName("description")
    var description: String,

    @SerializedName("created_at")
    var createdAt: Long,

    @SerializedName("updated_at")
    var updatedAt: Long,

    @SerializedName("deleted_at")
    var deletedAt: String,

    @SerializedName("is_enabled")
    var isEnabled: Int,

    @SerializedName("is_featured")
    var isFeatured: Boolean,

    @SerializedName("vendor_id")
    var vendorId: Long,

    @SerializedName("meta_keywords")
    var metaKeywords: String,

    @SerializedName("meta_description")
    var metaDescription: String,

    @SerializedName("parent_id")
    var parentId: Long,

    @SerializedName("type_id")
    var typeId: Long,

    @SerializedName("type")
    var type: String,

    @SerializedName("episode_number")
    var episodeNumber: Long,

    @SerializedName("series_number")
    var seriesNumber: Long,

    @SerializedName("episodes_count")
    var episodesCount: Long,

    @SerializedName("genres")
    var genreList: List<Genre>,

    @SerializedName("poster")
    var poster: Poster
)