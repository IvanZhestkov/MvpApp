package com.itis.android.mvpapp.presentation.model

import com.itis.android.mvpapp.data.pojo.TaskSolutionItem

data class TaskSolutionModel(
        var user: User? = null,
        var solution: TaskSolutionItem
)