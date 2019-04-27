package com.itis.android.mvpapp.presentation.ui.teacher.grouptask

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.itis.android.mvpapp.presentation.base.BaseView

interface GroupTaskView: BaseView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showTable()
}