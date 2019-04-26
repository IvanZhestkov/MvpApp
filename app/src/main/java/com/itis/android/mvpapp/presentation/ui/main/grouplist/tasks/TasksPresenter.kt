package com.itis.android.mvpapp.presentation.ui.main.grouplist.tasks

import com.arellomobile.mvp.InjectViewState
import com.itis.android.mvpapp.presentation.base.BasePresenter
import com.itis.android.mvpapp.presentation.model.TaskModel
import com.itis.android.mvpapp.router.MainRouter
import javax.inject.Inject

@InjectViewState
class TasksPresenter
@Inject constructor() : BasePresenter<TasksView>() {

    @Inject
    lateinit var tasksRouter: MainRouter

    @Inject
    lateinit var tasks: ArrayList<TaskModel>

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setTasks(tasks)
    }

    fun openGroupTaskScreen() {
        tasksRouter.openGroupTaskScreen()
    }
}