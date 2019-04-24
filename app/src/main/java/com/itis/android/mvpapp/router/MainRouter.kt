package com.itis.android.mvpapp.router

import com.itis.android.mvpapp.router.initparams.LoadTaskInitParams

interface MainRouter {
    fun openProfileScreen()

    fun openGroupListScreen()

    fun openLoadTaskScreen(loadTaskInitParams: LoadTaskInitParams)

    fun openGroupTaskScreen()

    fun openTaskSolutionScreen()

    fun openMessagesScreen()

    fun goBack()
}