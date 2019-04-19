package com.itis.android.mvpapp.router

import com.itis.android.mvpapp.presentation.ui.Screens
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

    override fun openLoadTaskScreen() {
        navigateTo(Screens.getLoadTaskScreen())
    }

    override fun openGroupTaskScreen() {
        navigateTo(Screens.getGroupTaskScreen())
    }

    override fun openTaskSolutionScreen() {
        navigateTo(Screens.getTaskSolutionScreen())
    }

    override fun goBack() {
        exit()
    }
}
