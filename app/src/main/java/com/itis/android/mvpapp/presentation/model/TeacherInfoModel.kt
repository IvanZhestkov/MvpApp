package com.itis.android.mvpapp.presentation.model

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
        val disciplines: List<TeacherDisciplineItem>
)

object TeacherInfoModelMapper {

    fun map(teacherInfoItem: TeacherInfoItem, disciplines: List<TeacherDisciplineItem>): TeacherInfoModel {
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
                disciplines
        )
    }
}