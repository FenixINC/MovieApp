package com.taras.movieapp.mvvm.data.entities

import com.google.gson.annotations.SerializedName

data class Session(

    @SerializedName("success")
    var success: Boolean,

    @SerializedName("session_id")
    var sessionId: String
)