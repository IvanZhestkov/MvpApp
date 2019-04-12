package com.itis.android.mvpapp.presentation.ui.auth

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpView
import com.itis.android.mvpapp.data.util.CredentialStorage
import com.itis.android.mvpapp.router.MainRouter
import com.itis.android.mvpapp.presentation.base.BasePresenter
import com.itis.android.mvpapp.router.AuthRouter
import com.itis.android.mvpapp.router.MainRouterImpl
import com.itis.android.mvpapp.router.initparams.TestInitParams
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class AuthPresenter
@Inject constructor() : BasePresenter<AuthView>() {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var authRouter: AuthRouter

    @Inject
    lateinit var credentialStorage: CredentialStorage

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        authRouter.openLoginScreen()
    }

    fun setNavigator(navigator: Navigator) {
        navigatorHolder.setNavigator(navigator)
    }

    fun removeNavigator() {
        navigatorHolder.removeNavigator()
    }
}