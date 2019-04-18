package com.itis.android.mvpapp.presentation.ui.main.profile

import com.arellomobile.mvp.InjectViewState
import com.itis.android.mvpapp.presentation.base.BasePresenter
import com.itis.android.mvpapp.router.MainRouter
import javax.inject.Inject

@InjectViewState
class ProfilePresenter
@Inject constructor() : BasePresenter<ProfileView>() {

    @Inject
    lateinit var profileRouter: MainRouter

    fun openGroupTaskScreen() {
        profileRouter.openGroupListScreen()
    }
}