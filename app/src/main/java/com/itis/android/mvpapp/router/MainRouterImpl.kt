package com.itis.android.mvpapp.router

import com.itis.android.mvpapp.presentation.ui.Screens
import com.itis.android.mvpapp.router.initparams.GroupTaskInitParams
import com.itis.android.mvpapp.router.initparams.LoadTaskInitParams
import com.itis.android.mvpapp.router.initparams.TaskSolutionInitParams
import ru.terrakok.cicerone.Router

class MainRouterImpl : MainRouter, Router() {

    override fun openProfileScreen() {
        newRootScreen(Screens.getProfileScreen())
    }

    override fun openGroupListScreen() {
        newRootScreen(Screens.getGroupListScreen())
    }

    override fun openMessagesScreen() {
        newRootScreen(Screens.getMessagesScreen())
    }

    override fun openLoadTaskScreen(loadTaskInitParams: LoadTaskInitParams) {
        navigateTo(Screens.getLoadTaskScreen(loadTaskInitParams))
    }

    override fun openGroupTaskScreen(groupTaskInitParams: GroupTaskInitParams) {
        navigateTo(Screens.getGroupTaskScreen(groupTaskInitParams))
    }

    override fun openTaskSolutionScreen(taskSolutionInitParams: TaskSolutionInitParams) {
        navigateTo(Screens.getTaskSolutionScreen(taskSolutionInitParams))
    }

    override fun goBack() {
        exit()
    }
}
