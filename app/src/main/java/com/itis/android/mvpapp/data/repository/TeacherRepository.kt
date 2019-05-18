package com.itis.android.mvpapp.data.repository

import com.itis.android.mvpapp.data.network.pojo.firebase.response.TeacherInfoItem
import com.itis.android.mvpapp.presentation.model.TeacherInfoModel
import io.reactivex.Observable
import io.reactivex.Single

interface TeacherRepository {

    fun getTeacherInfoObservable(userId: String): Observable<TeacherInfoModel>

    fun getTeacherByUID(uid: String): Single<TeacherInfoItem>
}