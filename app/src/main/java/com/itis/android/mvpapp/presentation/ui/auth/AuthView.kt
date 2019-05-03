package com.itis.android.mvpapp.presentation.ui.auth

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.itis.android.mvpapp.presentation.base.BaseView

interface AuthView: BaseView {
    fun startSignIn()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openTeacherScreen()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openStudentScreen()
}