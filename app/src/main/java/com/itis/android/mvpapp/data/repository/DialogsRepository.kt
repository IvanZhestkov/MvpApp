package com.itis.android.mvpapp.data.repository

import com.itis.android.mvpapp.presentation.model.DialogModel
import io.reactivex.Observable

interface DialogsRepository {

    fun createDialog(studentId: String): Observable<String>

    fun getDialogs(): Observable<DialogModel>
}