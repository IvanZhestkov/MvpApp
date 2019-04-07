package com.itis.android.mvpapp.router

import com.itis.android.mvpapp.router.initparams.TestInitParams
import ru.terrakok.cicerone.Router

class MainRouterImpl : MainRouter, Router() {

    override fun openTestScreen(testInitParams: TestInitParams) {
        newRootScreen(Screens.TestScreen(testInitParams))
    }

    override fun goBack() {
        exit()
    }
}
