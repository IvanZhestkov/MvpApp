package com.itis.android.mvpapp.data.pojo

import com.google.firebase.database.PropertyName
import com.itis.android.mvpapp.presentation.model.UserRole

data class TeacherInfoItem(
        @field:PropertyName("id")
        var id: Int? = null,
        @field:PropertyName("email")
        var email: String? = null,
        @field:PropertyName("first_name")
        var first_name: String? = null,
        @field:PropertyName("last_name")
        var last_name: String? = null,
        @field:PropertyName("middle_name")
        var middle_name: String? = null,
        @field:PropertyName("birth_date")
        var birth_date: String? = null,
        @field:PropertyName("phone")
        var phone: String? = null,
        @field:PropertyName("photo")
        var photo: String? = null,
        @field:PropertyName("role")
        var role: UserRole? = null
)