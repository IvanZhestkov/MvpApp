package com.itis.android.mvpapp.presentation.ui.teacher.dialogs

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.itis.android.mvpapp.presentation.base.BaseView

@StateStrategyType(OneExecutionStateStrategy::class)
interface DialogListView: BaseView {
}