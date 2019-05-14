package com.itis.android.mvpapp.router

import com.itis.android.mvpapp.data.network.pojo.firebase.response.UserItem
import com.itis.android.mvpapp.data.pojo.TaskSolutionItem
import com.itis.android.mvpapp.presentation.model.DialogModel
import com.itis.android.mvpapp.presentation.model.TaskModel
import com.itis.android.mvpapp.presentation.model.UserSolutionModel
import com.itis.android.mvpapp.presentation.ui.Screens
import com.itis.android.mvpapp.presentation.ui.student.loadtask.TaskModelInitParam
import com.itis.android.mvpapp.router.initparams.NewTaskInitParams
import com.itis.android.mvpapp.router.initparams.GroupTaskInitParams
import com.itis.android.mvpapp.router.initparams.TaskSolutionInitParams
import ru.terrakok.cicerone.Router

class MainRouterImpl : MainRouter, Router() {

    override fun openStudentProfile() {
        newRootScreen(Screens.getStudentProfile())
    }
    override fun openTaskListScreen() {
        newRootScreen(Screens.getStudentTasksListScreen())
    }

    override fun openProfileScreen() {
        newRootScreen(Screens.getProfileScreen())
    }

    override fun openGroupListScreen() {
        newRootScreen(Screens.getGroupListScreen())
    }

    override fun openDialogListScreen() {
        newRootScreen(Screens.getDialogListScreen())
    }

    override fun openNewTaskScreen(newTaskInitParams: NewTaskInitParams) {
        navigateTo(Screens.getNewTaskScreen(newTaskInitParams))
    }

    override fun openGroupTaskScreen(groupTaskInitParams: GroupTaskInitParams) {
        navigateTo(Screens.getGroupTaskScreen(groupTaskInitParams))
    }

    override fun openTaskSolutionScreen(taskSolutionInitParams: TaskSolutionInitParams) {
        navigateTo(Screens.getTaskSolutionScreen(taskSolutionInitParams))
    }

    override fun openTaskSolutionScreen2(user: UserItem?, solution: TaskSolutionItem?, taskDeadline: String?) {
        navigateTo(Screens.getTaskSolutionScreen2(user, solution, taskDeadline))
    }

    override fun openDialogScreen(dialogId: String, username: String) {
        navigateTo(Screens.getDialogScreen(dialogId, username))
    }

    override fun goBack() {
        exit()
    }

    override fun openLoadStudentTaskScreen(taskModel: TaskModelInitParam) {
        navigateTo(Screens.getStudentLoadTaskScreen(taskModel))
    }
}
