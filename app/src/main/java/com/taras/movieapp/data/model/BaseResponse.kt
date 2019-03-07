package com.taras.movieapp.data.model

import com.google.gson.annotations.SerializedName

class BaseResponse(

    @SerializedName("code")
    var code: Int,

    @SerializedName("reason")
    var reason: String,

    @SerializedName("total")
    var total: Int,

    @SerializedName("total_found")
    var totalFound: Int,

    @SerializedName("response")
    var responseList: List<Movie>
)