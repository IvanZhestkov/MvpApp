package com.itis.android.mvpapp.presentation.ui.student.uploadtask

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.itis.android.mvpapp.presentation.base.BaseView

interface NewStudentTaskView: BaseView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showTaskGroup(name: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setSpinnerAdapter(items: List<String>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showProgress()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun hideProgress()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showRetry(errorText: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun hideRetry()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun openFileChoose()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setButtonEnabled(enabled: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showChoosedFile(fileName: String)
}