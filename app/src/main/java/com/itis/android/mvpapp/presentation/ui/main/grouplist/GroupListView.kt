package com.itis.android.mvpapp.presentation.ui.main.grouplist

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.itis.android.mvpapp.presentation.model.Group
import com.itis.android.mvpapp.presentation.base.BaseView
import com.itis.android.mvpapp.presentation.model.TaskModel

interface GroupListView: BaseView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setupViewPager(tasks: List<TaskModel>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showProgress()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun hideProgress()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showRetry(errorText: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun hideRetry()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showTabs()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun hideTabs()
}