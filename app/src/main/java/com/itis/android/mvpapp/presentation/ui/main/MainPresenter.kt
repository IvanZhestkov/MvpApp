package com.itis.android.mvpapp.presentation.ui.main

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpView
import com.itis.android.mvpapp.data.util.CredentialStorage
import com.itis.android.mvpapp.router.MainRouter
import com.itis.android.mvpapp.presentation.base.BasePresenter
import com.itis.android.mvpapp.router.MainRouterImpl
import com.itis.android.mvpapp.router.initparams.TestInitParams
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class MainPresenter
@Inject constructor() : BasePresenter<MainView>() {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var mainRouter: MainRouter

    @Inject
    lateinit var preferences: CredentialStorage

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        mainRouter.openProfileScreen()
       // checkAuth()
    }

    fun openTestScreen() {
//        mainRouter.openTestScreen(TestInitParams("test_screen"))
        //mainRouter.openProfileScreen()
    }

    private fun checkAuth() {
        /*preferences
                .getSaveTokenSingle()
                .subscribe({
                    viewState.signedIn()
                }, {
                    viewState.startSignIn()
                })
                .disposeWhenDestroy()*/
        viewState.signedIn()
    }

    fun setNavigator(navigator: Navigator) {
        navigatorHolder.setNavigator(navigator)
    }

    fun removeNavigator() {
        navigatorHolder.removeNavigator()
    }
}