package com.itis.android.mvpapp.data.repository

import com.itis.android.mvpapp.presentation.model.TaskSolutionModel
import io.reactivex.Single

interface TaskSolutionRepository {
    fun getTaskSolutions(disciplineId: String?, taskId: String?): Single<List<TaskSolutionModel>>
}