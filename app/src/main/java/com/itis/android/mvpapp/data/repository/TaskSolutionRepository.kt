package com.itis.android.mvpapp.data.repository

import com.itis.android.mvpapp.data.pojo.TaskSolutionItem
import com.itis.android.mvpapp.presentation.model.UploadTaskSolutionModel
import com.itis.android.mvpapp.presentation.model.UserSolutionModel
import io.reactivex.Completable
import io.reactivex.Single

interface TaskSolutionRepository {
    fun getTaskSolutions(disciplineId: String?, taskId: String?, groupId: String?): Single<List<UserSolutionModel>>

    fun getUserTaskSolution(disciplineId: String?, taskId: String?): Single<List<TaskSolutionItem>>

    fun updateSolutionStatus(solution: TaskSolutionItem)

    fun addSolutionComment(solution: TaskSolutionItem)

    fun addTaskSolution(solution: UploadTaskSolutionModel): Completable
}