package com.itis.android.mvpapp.data.repository

import com.itis.android.mvpapp.data.network.pojo.firebase.response.TeacherDisciplineItem
import io.reactivex.Single

interface DisciplinesRepository {

    fun getDisciplinesSingle(userId: String): Single<List<TeacherDisciplineItem>>

    fun getDisciplineById(id: String): Single<TeacherDisciplineItem>

    fun getDisciplineByNameAndGroup(name: String, group: String): Single<TeacherDisciplineItem>
}