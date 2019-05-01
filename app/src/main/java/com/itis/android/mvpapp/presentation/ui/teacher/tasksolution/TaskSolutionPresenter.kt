package com.itis.android.mvpapp.presentation.ui.teacher.tasksolution

import com.arellomobile.mvp.InjectViewState
import com.itis.android.mvpapp.data.repository.TaskSolutionRepository
import com.itis.android.mvpapp.presentation.base.BasePresenter
import com.itis.android.mvpapp.presentation.model.UserSolutionModel
import javax.inject.Inject

@InjectViewState
class TaskSolutionPresenter @Inject constructor() : BasePresenter<TaskSolutionView>() {

    private var userSolution: UserSolutionModel? = null

    @Inject
    lateinit var taskSolutionRepository: TaskSolutionRepository

    fun init(userSolution: UserSolutionModel) {
        this.userSolution = userSolution
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        userSolution?.user?.let { viewState.showStudentName(it) }
    }

    fun updateSolutionStatus(status: String) {
        userSolution?.solution?.let { taskSolutionRepository.updateSolutionStatus(it, status) }
    }
}