package com.itis.android.mvpapp.data.repository

import com.itis.android.mvpapp.data.pojo.TeacherDisciplineItem
import io.reactivex.Single

interface DisciplinesRepository {

    fun getDisciplinesSingle(): Single<List<TeacherDisciplineItem>>

    fun getDisciplineById(id: String): Single<TeacherDisciplineItem>
}