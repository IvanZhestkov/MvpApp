package com.itis.android.mvpapp.data.repository

import com.itis.android.mvpapp.presentation.model.StudentInfoModel
import io.reactivex.Observable
import io.reactivex.Single

interface StudentRepository {

    fun getStudentInfoObservable(userId: String): Observable<StudentInfoModel>

    fun getGroupByUID(userId: String) : Single<String>

    fun getAverageScoreByUID(userId: String): Single<Long>
}