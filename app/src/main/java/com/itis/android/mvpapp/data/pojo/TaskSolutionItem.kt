package com.itis.android.mvpapp.data.pojo

data class TaskSolutionItem(
    var checking_date: String? = null,
    var commentary: String? = null,
    var score: Int = 0,
    var solution_file_link: String? = null,
    var status: String? = null,
    var uploading_date: String? = null,
    var taskId: String? = null,
    var disciplineId: String? = null,
    var userId: String? = null
)