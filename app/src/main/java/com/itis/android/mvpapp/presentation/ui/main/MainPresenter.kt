package com.itis.android.mvpapp.presentation.ui.main

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpView
import com.itis.android.mvpapp.router.MainRouter
import com.itis.android.mvpapp.presentation.base.BasePresenter
import com.itis.android.mvpapp.presentation.ui.Screens
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class MainPresenter
@Inject constructor(

) : BasePresenter<MvpView>() {

    @Inject
    @field:MainQualifier
    lateinit var router: Router

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.newRootScreen(Screens.LoginScreen)
    }
}