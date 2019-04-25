package com.itis.android.mvpapp.data.repository

import com.itis.android.mvpapp.presentation.model.TeacherInfoModel
import io.reactivex.Observable

interface TeacherRepository {

    fun getTeacherInfoObservable(): Observable<TeacherInfoModel>
}