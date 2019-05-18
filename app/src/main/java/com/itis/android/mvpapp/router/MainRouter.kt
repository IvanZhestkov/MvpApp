package com.itis.android.mvpapp.router

import com.itis.android.mvpapp.data.network.pojo.firebase.response.UserItem
import com.itis.android.mvpapp.data.pojo.TaskSolutionItem
import com.itis.android.mvpapp.presentation.model.DialogModel
import com.itis.android.mvpapp.presentation.model.TaskModel
import com.itis.android.mvpapp.presentation.model.UserSolutionModel
import com.itis.android.mvpapp.presentation.ui.student.loadtask.TaskModelInitParam
import com.itis.android.mvpapp.router.initparams.NewTaskInitParams
import com.itis.android.mvpapp.router.initparams.GroupTaskInitParams
import com.itis.android.mvpapp.router.initparams.TaskSolutionInitParams

interface MainRouter {
    fun openProfileScreen(userId: String)

    fun openProfileScreenForStudent(userId: String)

    fun openGroupListScreen()

    fun openNewTaskScreen(newTaskInitParams: NewTaskInitParams)

    fun openGroupTaskScreen(groupTaskInitParams: GroupTaskInitParams)

    fun openTaskSolutionScreen(taskSolutionInitParams: TaskSolutionInitParams)

    fun openTaskSolutionScreen2(user: UserItem?, solution: TaskSolutionItem?, taskDeadline: String?)

    fun openDialogScreen(dialogId: String, username: String)

    fun openDialogListScreen()

    fun openTaskListScreen()

    fun goBack()

    fun openStudentProfile(userId: String)

    fun openStudentProfileForTeacher(userId: String)

    fun openLoadStudentTaskScreen(taskModel: TaskModelInitParam)
}