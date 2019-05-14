package com.itis.android.mvpapp.presentation.ui.student.loadtask

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.itis.android.mvpapp.presentation.base.BaseView

interface LoadStudentTaskView: BaseView{

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showProgress()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun hideProgress()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setAllData()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setProfersor(name: String)

    fun downloadFile(fileName: String, fileExtension: String, url: String)
}