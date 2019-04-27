package com.itis.android.mvpapp.presentation.ui.teacher.tasksolution

import android.os.Bundle
import android.view.View
import com.itis.android.mvpapp.R
import com.itis.android.mvpapp.presentation.base.BaseFragment

class TaskSolutionFragment: BaseFragment(), TaskSolutionView {

    companion object {
        fun getInstance() = TaskSolutionFragment()
    }

    override val mainContentLayout = R.layout.fragment_task_solution

    override val enableBackArrow = true

    override val toolbarTitle = R.string.solution

    override val menu: Int?
        get() = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}