package com.itis.android.mvpapp.ui

import com.arellomobile.mvp.MvpView
import com.itis.android.mvpapp.router.MainRouter
import com.itis.android.mvpapp.router.initparams.TestInitParams
import com.itis.android.mvpapp.ui.base.BasePresenter
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Inject

class AppPresenter
@Inject constructor(
        private val navigatorHolder: NavigatorHolder,
        private val mainRouter: MainRouter
) : BasePresenter<MvpView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        mainRouter.openLoginScreen()
    }

    fun setNavigator(navigator: Navigator) {
        navigatorHolder.setNavigator(navigator)
    }

    fun removeNavigator() {
        navigatorHolder.removeNavigator()
    }
}