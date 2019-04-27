package com.itis.android.mvpapp.presentation.ui.teacher.profile

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.itis.android.mvpapp.presentation.model.TeacherInfoModel

interface ProfileView: MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showProfile(teacherInfoModel: TeacherInfoModel)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showProgress()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun hideProgress()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showRetry(errorText: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun hideRetry()
}