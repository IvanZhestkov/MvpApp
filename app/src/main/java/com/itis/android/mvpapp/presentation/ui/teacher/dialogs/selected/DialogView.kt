package com.itis.android.mvpapp.presentation.ui.teacher.dialogs.selected

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.itis.android.mvpapp.presentation.base.BaseView

@StateStrategyType(OneExecutionStateStrategy::class)
interface DialogView: BaseView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun startListeningAdapter()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun stopListeningAdapter()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setButtonEnabled(enabled: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun clearMessageField()
}