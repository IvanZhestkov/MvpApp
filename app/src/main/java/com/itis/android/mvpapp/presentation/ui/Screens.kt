package com.itis.android.mvpapp.presentation.ui

import com.itis.android.mvpapp.presentation.ui.auth.login.LoginFragment
import com.itis.android.mvpapp.presentation.ui.student.tasks.StudentTasksScreen
import com.itis.android.mvpapp.presentation.ui.teacher.groups.GroupsScreen
import com.itis.android.mvpapp.presentation.ui.teacher.grouptask.GroupTaskScreen
import com.itis.android.mvpapp.presentation.ui.teacher.loadtask.LoadTaskScreen
import com.itis.android.mvpapp.presentation.ui.teacher.messages.MessagesScreen
import com.itis.android.mvpapp.presentation.ui.teacher.profile.ProfileScreen
import com.itis.android.mvpapp.presentation.ui.teacher.tasksolution.TaskSolutionScreen
import com.itis.android.mvpapp.router.initparams.LoadTaskInitParams
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    object LoginScreen : SupportAppScreen() {
        override fun getFragment() = LoginFragment.newInstance()

        override fun getScreenKey(): String {
            return LoginFragment::class.java.name
        }
    }

    fun getProfileScreen(): ProfileScreen = ProfileScreen()

    fun getGroupListScreen(): GroupsScreen = GroupsScreen()

    fun getLoadTaskScreen(initParams: LoadTaskInitParams): LoadTaskScreen = LoadTaskScreen(initParams)

    fun getGroupTaskScreen(): GroupTaskScreen = GroupTaskScreen()

    fun getTaskSolutionScreen(): TaskSolutionScreen = TaskSolutionScreen()

    fun getMessagesScreen(): MessagesScreen = MessagesScreen()

    fun getTasksListScreen(): StudentTasksScreen = StudentTasksScreen()
}