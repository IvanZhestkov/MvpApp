package com.itis.android.mvpapp.router

import com.itis.android.mvpapp.presentation.ui.Screens
import ru.terrakok.cicerone.Router

class AuthRouterImpl: Router(), AuthRouter {

    override fun openLoginScreen() {
        newRootScreen(Screens.LoginScreen)
    }
}