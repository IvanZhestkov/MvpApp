package com.itis.android.mvpapp.router

interface MainRouter {
    fun openProfileScreen()

    fun openGroupListScreen()

    fun openLoadTaskScreen()

    fun openGroupTaskScreen()

    fun openTaskSolutionScreen()

    fun openMessagesScreen()

    fun goBack()
}