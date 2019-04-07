package com.itis.android.mvpapp.data.util

import io.reactivex.Completable
import io.reactivex.Single

interface CredentialStorage {

    fun getSaveTokenCompletable(token: String): Completable

    fun getSaveTokenSingle(): Single<String>

    fun clear()
}