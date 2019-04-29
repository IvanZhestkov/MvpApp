package com.itis.android.mvpapp.presentation.ui.teacher.tasksolution

import com.itis.android.mvpapp.presentation.base.BaseView
import com.itis.android.mvpapp.presentation.model.User

interface TaskSolutionView: BaseView {

    fun showStudentName(user: User)
}