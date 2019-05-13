package com.itis.android.mvpapp.router

import com.itis.android.mvpapp.presentation.model.DialogModel
import com.itis.android.mvpapp.router.initparams.NewTaskInitParams
import com.itis.android.mvpapp.router.initparams.GroupTaskInitParams
import com.itis.android.mvpapp.router.initparams.TaskSolutionInitParams

interface MainRouter {
    fun openProfileScreen()

    fun openGroupListScreen()

    fun openNewTaskScreen(newTaskInitParams: NewTaskInitParams)

    fun openGroupTaskScreen(groupTaskInitParams: GroupTaskInitParams)

    fun openTaskSolutionScreen(taskSolutionInitParams: TaskSolutionInitParams)

    fun openDialogScreen(dialogId: String, username: String)

    fun openDialogListScreen()

    fun goBack()
}