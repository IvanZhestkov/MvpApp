package com.itis.android.mvpapp.data.repository

import com.itis.android.mvpapp.presentation.model.TaskModel
import com.itis.android.mvpapp.presentation.model.UploadTaskModel
import io.reactivex.Completable
import io.reactivex.Single

interface TasksRepository {

    fun getTasks(): Single<List<TaskModel>>

    fun addTask(task: UploadTaskModel): Completable
}