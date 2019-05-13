package com.itis.android.mvpapp.data.repository

import io.reactivex.Single

interface StudentsRepository {
    fun getStudentsByGroupId(id: String): Single<List<String>>
}