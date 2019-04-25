package com.itis.android.mvpapp.data.repository

import io.reactivex.Completable
import io.reactivex.Observable

interface ConnectionRepository {

    fun getCheckConnection(): Observable<Boolean>
}