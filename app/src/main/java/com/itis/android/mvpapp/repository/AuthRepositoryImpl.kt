package com.itis.android.mvpapp.repository

import com.google.gson.JsonObject
import com.itis.android.mvpapp.api.ApiService
import com.itis.android.mvpapp.model.User
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject

class AuthRepositoryImpl
@Inject constructor(

) : AuthRepository {

    override fun login(user: User): Single<Response<JsonObject>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}