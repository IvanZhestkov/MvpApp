package com.itis.android.mvpapp.presentation.ui.teacher.tasksolution

import com.itis.android.mvpapp.presentation.base.BaseView
import com.itis.android.mvpapp.presentation.model.User
import java.security.cert.Extension

interface TaskSolutionView : BaseView {

    fun showStudentName(user: User)

    fun setColorStatus(color: Int)

    fun showButtons()

    fun hideButtons()

    fun downloadFile(fileName: String, fileExtension: String, url: String)

    fun updateStatus(status: String)
}