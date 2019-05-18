package com.itis.android.mvpapp.presentation.ui.student.profile

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.itis.android.mvpapp.presentation.model.StudentInfoModel

interface ProfileView : MvpView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showUserPhoto(url: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showProfile(studentInfoModel: StudentInfoModel)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showButtonLogout()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun hideButtonLogout()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showButtonChat()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun hideButtonChat()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showBackArrow()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun hideBackArrow()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showProgress()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun hideProgress()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showRetry(errorText: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun hideRetry()
}