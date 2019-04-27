package com.itis.android.mvpapp.data.repository

import com.itis.android.mvpapp.presentation.model.User
import io.reactivex.Completable
import io.reactivex.Single

interface LoginRepository {
    fun login(email: String, password: String): Single<User>

    fun getUser(): Single<User>
}