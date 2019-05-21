package com.itis.android.mvpapp.presentation.ui.student.loadtask

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.itis.android.mvpapp.data.pojo.TaskSolutionItem
import com.itis.android.mvpapp.presentation.base.BaseView

interface LoadStudentTaskView: BaseView{

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showCommentary(text: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showStatus(status: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setColorStatus(color: Int)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setAllData()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setProfersor(name: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun hideLoadButton()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showSolution(solution: TaskSolutionItem)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun hideSolution()

    fun downloadFile(fileName: String, fileExtension: String, url: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun openFileChoose()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showProgress()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun hideProgress()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showRetry(errorText: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun hideRetry()
}