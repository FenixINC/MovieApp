package com.taras.movieapp.data.model

import com.google.gson.annotations.SerializedName

data class Session(

    @SerializedName("success")
    var success: Boolean,

    @SerializedName("session_id")
    var sessionId: String
)