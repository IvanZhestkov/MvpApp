package com.itis.android.mvpapp.data.repository

import com.itis.android.mvpapp.presentation.model.User
import io.reactivex.Completable
import io.reactivex.Single

interface UserRepository {
    fun getUser(): Single<User>

    fun getUserById(id: String): Single<User>
}