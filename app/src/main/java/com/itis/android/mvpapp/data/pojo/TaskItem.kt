package com.itis.android.mvpapp.data.pojo

data class TaskItem(
        var expiration_date: String? = null,
        var description: String? = null,
        var subject: String? = null,
        var name: String? = null,
        var taskId: String? = null,
        var disciplineId: String? = null
)