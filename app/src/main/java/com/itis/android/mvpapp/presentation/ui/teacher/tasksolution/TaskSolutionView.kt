package com.itis.android.mvpapp.presentation.ui.teacher.tasksolution

import com.itis.android.mvpapp.presentation.base.BaseView
import com.itis.android.mvpapp.data.network.pojo.firebase.response.UserItem
import java.security.cert.Extension

interface TaskSolutionView : BaseView {

    fun showStudentName(user: UserItem)

    fun setColorStatus(color: Int)

    fun showButtons()

    fun hideButtons()

    fun downloadFile(fileName: String, fileExtension: String, url: String)

    fun updateStatus(status: String)
}