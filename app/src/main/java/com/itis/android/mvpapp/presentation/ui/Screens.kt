package com.itis.android.mvpapp.presentation.ui

import com.itis.android.mvpapp.presentation.ui.auth.login.LoginFragment
import com.itis.android.mvpapp.presentation.ui.main.grouplist.GroupListScreen
import com.itis.android.mvpapp.presentation.ui.main.grouptask.GroupTaskScreen
import com.itis.android.mvpapp.presentation.ui.main.loadtask.LoadTaskScreen
import com.itis.android.mvpapp.presentation.ui.main.messages.MessagesScreen
import com.itis.android.mvpapp.presentation.ui.main.profile.ProfileScreen
import com.itis.android.mvpapp.presentation.ui.main.tasksolution.TaskSolutionScreen
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    object LoginScreen : SupportAppScreen() {
        override fun getFragment() = LoginFragment.newInstance()

        override fun getScreenKey(): String {
            return LoginFragment::class.java.name
        }
    }

    fun getProfileScreen(): ProfileScreen = ProfileScreen()

    fun getGroupListScreen(): GroupListScreen = GroupListScreen()

    fun getLoadTaskScreen(): LoadTaskScreen = LoadTaskScreen()

    fun getGroupTaskScreen(): GroupTaskScreen = GroupTaskScreen()

    fun getTaskSolutionScreen(): TaskSolutionScreen = TaskSolutionScreen()

    fun getMessagesScreen(): MessagesScreen = MessagesScreen()
}