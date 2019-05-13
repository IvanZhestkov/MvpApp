package com.itis.android.mvpapp.presentation.ui.teacher.groups.tasks

import com.arellomobile.mvp.InjectViewState
import com.itis.android.mvpapp.presentation.base.BasePresenter
import com.itis.android.mvpapp.presentation.model.GroupModel
import com.itis.android.mvpapp.presentation.model.TaskModel
import com.itis.android.mvpapp.router.MainRouter
import com.itis.android.mvpapp.router.initparams.GroupTaskInitParams
import javax.inject.Inject

@InjectViewState
class TasksPresenter
@Inject constructor() : BasePresenter<TasksView>() {

    @Inject
    lateinit var tasksRouter: MainRouter

    @Inject
    lateinit var group: GroupModel

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        if (group.tasks.isEmpty()) {
            viewState.showEmptyState()
        } else {
            viewState.setTasks(group.tasks)
        }
    }

    fun openGroupTaskScreen(task: TaskModel) {
        tasksRouter.openGroupTaskScreen(GroupTaskInitParams(task))
    }
}