package com.itis.android.mvpapp.presentation.ui.teacher.newtask

import com.itis.android.mvpapp.presentation.base.BaseView

interface NewTaskView: BaseView {

    fun showTaskGroup(name: String)
}