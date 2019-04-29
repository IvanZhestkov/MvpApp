package com.itis.android.mvpapp.presentation.model

import com.itis.android.mvpapp.data.pojo.TaskSolutionItem
import java.io.Serializable

data class UserSolutionModel(
        var user: User? = null,
        var solution: TaskSolutionItem
): Serializable