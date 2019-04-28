package com.itis.android.mvpapp.router

import com.itis.android.mvpapp.router.initparams.GroupTaskInitParams
import com.itis.android.mvpapp.router.initparams.LoadTaskInitParams

interface MainRouter {
    fun openProfileScreen()

    fun openGroupListScreen()

    fun openLoadTaskScreen(loadTaskInitParams: LoadTaskInitParams)

    fun openGroupTaskScreen(groupTaskInitParams: GroupTaskInitParams)

    fun openTaskSolutionScreen()

    fun openMessagesScreen()

    fun goBack()
}