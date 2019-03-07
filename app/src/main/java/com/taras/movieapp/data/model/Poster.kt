package com.taras.movieapp.data.model

import com.google.gson.annotations.SerializedName

data class Poster(

    @SerializedName("image_id")
    var imageId: Long,

    @SerializedName("content_id")
    var contentId: Long,

    @SerializedName("parent_id")
    var parentId: Long,

    @SerializedName("type")
    var type: String,

    @SerializedName("width")
    var width: Int,

    @SerializedName("height")
    var height: Int,

    @SerializedName("couchdb_url")
    var couchdbUrl: String,

    @SerializedName("url")
    var url: String,

    @SerializedName("source_url")
    var sourceUrl: String,

    @SerializedName("is_synchronized")
    var isSynchronized: Boolean,

    @SerializedName("thumbs")
    var thumbList: Thumb
)