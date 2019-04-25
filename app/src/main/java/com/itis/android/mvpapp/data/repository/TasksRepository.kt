package com.itis.android.mvpapp.data.repository

import com.itis.android.mvpapp.presentation.model.Task
import io.reactivex.Single

interface TasksRepository {
    fun getTasks(groupId: String): Single<List<Task>>
}