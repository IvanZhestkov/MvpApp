package com.itis.android.mvpapp.presentation.ui.auth.login

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.itis.android.mvpapp.model.User
import com.itis.android.mvpapp.presentation.base.BaseView

interface LoginView : BaseView {

    fun showEmailError(hasError: Boolean)

    fun showPasswordError(hasError: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun openMainScreen()
}