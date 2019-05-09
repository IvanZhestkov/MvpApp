package com.itis.android.mvpapp.data.repository

import com.itis.android.mvpapp.data.network.pojo.firebase.response.UserItem
import io.reactivex.Single

interface UserRepository {
    fun getUser(): Single<UserItem>

    fun getUserById(id: String): Single<UserItem>
}