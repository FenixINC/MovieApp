package com.taras.movieapp.mvvm.data.entities

import com.google.gson.annotations.SerializedName

data class Genre(

    @SerializedName("genre_id")
    var genreId: Long,

    @SerializedName("name")
    var name: String,

    @SerializedName("slug")
    var slug: String
)