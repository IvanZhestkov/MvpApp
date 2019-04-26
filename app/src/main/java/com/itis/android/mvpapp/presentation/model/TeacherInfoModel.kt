package com.itis.android.mvpapp.presentation.model

import com.google.firebase.auth.FirebaseAuth
import com.itis.android.mvpapp.data.pojo.TeacherDisciplineItem
import com.itis.android.mvpapp.data.pojo.TeacherInfoItem

data class TeacherInfoModel(
        var id: Int? = null,
        var email: String? = null,
        var firstName: String? = null,
        var lastName: String? = null,
        var middleName: String? = null,
        var birthDate: String? = null,
        var phone: String? = null,
        var photo: String? = null,
        var role: UserRole? = null,
        val disciplines: List<TeacherDisciplineModel>
)

object TeacherInfoModelMapper {

    fun map(teacherInfoItem: TeacherInfoItem, disciplines: List<TeacherDisciplineItem>): TeacherInfoModel {
        val mapCourse: MutableMap<String?, MutableList<String?>> = HashMap()

        disciplines.filter { it.professor_id == FirebaseAuth.getInstance().currentUser?.uid }.forEach { course ->
                  mapCourse[course.subject_name] =
                          (mapCourse[course.subject_name]
                                  ?: mutableListOf()).also { it.add(course.group_id) }
              }


        val mappedDisciplines = mapCourse.toList().map { TeacherDisciplineModel(it.first, it.second.toList()) }

        return TeacherInfoModel(
                teacherInfoItem.id,
                teacherInfoItem.email,
                teacherInfoItem.first_name,
                teacherInfoItem.last_name,
                teacherInfoItem.middle_name,
                teacherInfoItem.birth_date,
                teacherInfoItem.phone,
                teacherInfoItem.photo,
                teacherInfoItem.role,
                mappedDisciplines
        )
    }
}