package com.itis.android.mvpapp.presentation.ui.auth

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface AuthView: MvpView {
    fun startSignIn()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openTeacherScreen()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openStudentScreen()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showProgress()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun hideProgress()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showRetry(errorText: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun hideRetry()
}