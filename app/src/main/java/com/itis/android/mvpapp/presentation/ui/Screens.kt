package com.itis.android.mvpapp.presentation.ui

import com.itis.android.mvpapp.presentation.model.DialogModel
import com.itis.android.mvpapp.presentation.ui.auth.login.LoginFragment
import com.itis.android.mvpapp.presentation.ui.teacher.dialogs.DialogListScreen
import com.itis.android.mvpapp.presentation.ui.teacher.dialogs.selected.DialogScreen
import com.itis.android.mvpapp.presentation.ui.student.tasks.StudentTasksScreen
import com.itis.android.mvpapp.presentation.ui.teacher.groups.GroupsScreen
import com.itis.android.mvpapp.presentation.ui.teacher.grouptask.GroupTaskScreen
import com.itis.android.mvpapp.presentation.ui.teacher.newtask.NewTaskScreen
import com.itis.android.mvpapp.presentation.ui.teacher.profile.ProfileScreen
import com.itis.android.mvpapp.presentation.ui.teacher.tasksolution.TaskSolutionScreen
import com.itis.android.mvpapp.router.initparams.GroupTaskInitParams
import com.itis.android.mvpapp.router.initparams.TaskSolutionInitParams
import com.itis.android.mvpapp.router.initparams.NewTaskInitParams
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

    fun getNewTaskScreen(initParams: NewTaskInitParams): NewTaskScreen = NewTaskScreen(initParams)

    fun getGroupTaskScreen(initParams: GroupTaskInitParams): GroupTaskScreen = GroupTaskScreen(initParams)

    fun getTaskSolutionScreen(initParams: TaskSolutionInitParams): TaskSolutionScreen = TaskSolutionScreen(initParams)

    fun getDialogListScreen(): DialogListScreen = DialogListScreen()

    fun getDialogScreen(dialogId: String, username: String): DialogScreen = DialogScreen(dialogId, username)

    fun getTasksListScreen(): StudentTasksScreen = StudentTasksScreen()
}