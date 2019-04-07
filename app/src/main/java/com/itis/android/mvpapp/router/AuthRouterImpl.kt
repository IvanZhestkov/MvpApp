package com.itis.android.mvpapp.router

import ru.terrakok.cicerone.Router

class AuthRouterImpl: Router(), AuthRouter {
    override fun openLoginScreen() {
        newRootScreen(Screens.LoginScreen)
    }
}