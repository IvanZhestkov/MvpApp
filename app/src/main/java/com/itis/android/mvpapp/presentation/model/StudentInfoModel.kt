package com.itis.android.mvpapp.presentation.model

data class StudentInfoModel(
    var id: Int? = null,
    var email: String? = null,
    var firstName: String? = null,
    var lastName: String? = null,
    var middleName: String? = null,
    var birthDate: String? = null,
    var phone: String? = null,
    var photo: String? = null,
    var role: UserRole? = null,
    var averageScore : String? = null,
    var groupId : String? = null
)