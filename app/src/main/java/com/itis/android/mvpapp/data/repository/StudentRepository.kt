package com.itis.android.mvpapp.data.repository

import com.itis.android.mvpapp.presentation.model.StudentInfoModel
import io.reactivex.Observable

interface StudentRepository {

    fun getStudentInfoObservable(): Observable<StudentInfoModel>
}