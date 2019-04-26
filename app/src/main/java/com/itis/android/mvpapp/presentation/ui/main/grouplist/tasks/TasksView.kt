package com.itis.android.mvpapp.presentation.ui.main.grouplist.tasks

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.itis.android.mvpapp.presentation.base.BaseView
import com.itis.android.mvpapp.data.pojo.TaskItem
import com.itis.android.mvpapp.presentation.model.TaskModel

interface TasksView: BaseView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setTasks(items: List<TaskModel>)
}