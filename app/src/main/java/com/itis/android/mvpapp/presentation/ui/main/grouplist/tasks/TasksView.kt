package com.itis.android.mvpapp.presentation.ui.main.grouplist.tasks

import com.itis.android.mvpapp.presentation.base.BaseView
import com.itis.android.mvpapp.presentation.model.Task

interface TasksView: BaseView {

    fun showTasks(items: List<Task>)
}