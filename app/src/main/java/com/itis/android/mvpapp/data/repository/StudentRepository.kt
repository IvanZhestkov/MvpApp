package com.itis.android.mvpapp.data.repository

import com.itis.android.mvpapp.presentation.model.StudentInfoModel
import io.reactivex.Observable
import io.reactivex.Single

interface StudentRepository {

    fun getStudentInfoObservable(): Observable<StudentInfoModel>

    fun getGroupByUID() : Single<String>

    fun getAverageScoreByUID(): Single<Long>
}