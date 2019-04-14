package com.itis.android.mvpapp.router

import com.itis.android.mvpapp.presentation.ui.Screens
import com.itis.android.mvpapp.router.initparams.TestInitParams
import ru.terrakok.cicerone.Router

class MainRouterImpl : MainRouter, Router() {

    override fun openTestScreen(testInitParams: TestInitParams) {
        newRootScreen(Screens.TestScreen(testInitParams))
    }

    override fun openProfileScreen() {
        newRootScreen(Screens.ProfileScreen)
    }

    override fun openLoadTaskScreen() {
        navigateTo(Screens.getLoadTaskScreen())
    }

    override fun openGroupTaskScreen() {
        navigateTo(Screens.getGroupTaskScreen())
    }

    override fun goBack() {
        exit()
    }
}
