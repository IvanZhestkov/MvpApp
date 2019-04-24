package com.itis.android.mvpapp.presentation.ui.main.grouplist.tasks

import com.arellomobile.mvp.InjectViewState
import com.itis.android.mvpapp.presentation.base.BasePresenter
import com.itis.android.mvpapp.router.MainRouter
import javax.inject.Inject

@InjectViewState
class TasksPresenter
@Inject constructor() : BasePresenter<TasksView>() {

    @Inject
    lateinit var tasksRouter: MainRouter

    private var groupId: Int = 0

    fun init(groupId: Int) {
        this.groupId = groupId
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadTasks()
    }

    private fun loadTasks() {
        viewState.showTasks()
    }

    fun openGroupTaskScreen() {
        tasksRouter.openGroupTaskScreen()
    }
}