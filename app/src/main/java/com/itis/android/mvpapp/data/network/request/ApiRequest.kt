package com.itis.android.mvpapp.data.network.request

import com.google.gson.JsonObject
import com.itis.android.mvpapp.presentation.model.User
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiRequest {

    @POST("auth/login/")
    fun login(@Body user: User): Single<Response<JsonObject>>
}