package com.itis.android.mvpapp.router

import com.itis.android.mvpapp.router.initparams.TestInitParams

interface MainRouter {
    fun openMainScreen(testInitParams: TestInitParams)

    fun openLoginScreen()

    fun goBack()
}