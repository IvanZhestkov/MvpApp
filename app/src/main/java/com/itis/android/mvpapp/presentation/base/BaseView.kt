package com.itis.android.mvpapp.presentation.base

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(OneExecutionStateStrategy::class)
interface BaseView : MvpView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showErrorDialog(text: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showErrorDialog(text: Int)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showWaitDialog()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun hideWaitDialog()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun hideKeyboard()
}