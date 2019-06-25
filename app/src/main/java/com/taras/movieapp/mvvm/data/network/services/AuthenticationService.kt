package com.taras.movieapp.mvvm.data.network.services

import com.taras.movieapp.mvvm.data.entities.Authentication
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AuthenticationService {

    @GET("/3/authentication/token/new")
    fun requestNewToken(@Query("api_key") apiKey: String): Deferred<Response<Authentication>>
}