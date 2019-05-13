package com.itis.android.mvpapp.data.repository

import com.itis.android.mvpapp.data.network.pojo.firebase.response.UserItem
import io.reactivex.Single

interface LoginRepository {
    fun login(email: String, password: String): Single<UserItem>
}