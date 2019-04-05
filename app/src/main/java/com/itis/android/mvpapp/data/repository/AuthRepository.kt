package com.itis.android.mvpapp.data.repository

import com.google.gson.JsonObject
import com.itis.android.mvpapp.model.User
import io.reactivex.Single
import retrofit2.Response

interface AuthRepository {
    fun login(user: User): Single<Response<JsonObject>>
}