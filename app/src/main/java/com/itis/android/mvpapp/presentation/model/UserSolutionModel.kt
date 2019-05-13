package com.itis.android.mvpapp.presentation.model

import com.itis.android.mvpapp.data.network.pojo.firebase.response.UserItem
import com.itis.android.mvpapp.data.pojo.TaskSolutionItem
import java.io.Serializable

data class UserSolutionModel(
        var user: UserItem? = null,
        var solution: TaskSolutionItem? = null
): Serializable