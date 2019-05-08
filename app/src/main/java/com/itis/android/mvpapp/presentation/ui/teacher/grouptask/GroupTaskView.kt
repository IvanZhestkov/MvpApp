package com.itis.android.mvpapp.presentation.ui.teacher.grouptask

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.itis.android.mvpapp.presentation.base.BaseView
import com.itis.android.mvpapp.presentation.model.UserSolutionModel

interface GroupTaskView: BaseView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showTaskDescription(description: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showTable(solutions: List<UserSolutionModel>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun downloadFile(fileName: String, fileExtension: String, url: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showProgress()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun hideProgress()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showRetry(errorText: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun hideRetry()
}