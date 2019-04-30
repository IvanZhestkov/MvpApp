package com.itis.android.mvpapp.data.repository

import com.itis.android.mvpapp.presentation.model.UserSolutionModel
import io.reactivex.Single

interface TaskSolutionRepository {
    fun getTaskSolutions(disciplineId: String?, taskId: String?, groupId: String?): Single<List<UserSolutionModel>>
}