package com.itis.android.mvpapp.presentation.ui.student.tasks

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.itis.android.mvpapp.presentation.base.BaseView
import com.itis.android.mvpapp.presentation.model.TaskModel

interface StudentTasksView : BaseView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setTasks(items: List<TaskModel>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showEmptyState()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun hideEmptyState()

}