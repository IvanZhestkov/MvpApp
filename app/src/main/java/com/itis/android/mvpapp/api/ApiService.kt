package com.itis.android.mvpapp.api

import com.google.gson.JsonObject
import com.itis.android.mvpapp.model.User
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("auth/login/")
    fun login(@Body user: User): Single<Response<JsonObject>>
}