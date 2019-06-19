package com.taras.movieapp.mvvm.model

import com.google.gson.annotations.SerializedName

data class Authentication(

    @SerializedName("success")
    var success: Boolean,

    @SerializedName("expires_at")
    var expiresAt: String,

    @SerializedName("request_token")
    var requestToken: String
)