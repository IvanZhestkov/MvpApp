package com.itis.android.mvpapp.data.repository

import com.itis.android.mvpapp.data.pojo.TaskItem
import com.itis.android.mvpapp.presentation.model.TaskModel
import io.reactivex.Single

interface TasksRepository {
    fun getTasks(): Single<List<TaskModel>>
}