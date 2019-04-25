package com.itis.android.mvpapp.data.repository

import com.itis.android.mvpapp.presentation.model.User
import io.reactivex.Single

interface LoginRepository {
    fun getUser(): Single<User>
}