package com.itis.android.mvpapp.router

import com.itis.android.mvpapp.router.initparams.TestInitParams
import ru.terrakok.cicerone.Router

class MainRouterImpl : Router(), MainRouter {

    override fun openLoginScreen() {
        newRootScreen(Screens.LoginScreen)
    }

    override fun openMainScreen(testInitParams: TestInitParams) {
        newRootScreen(Screens.TestScreen(testInitParams))
    }

    /*override fun openMainScreen() {
        newRootScreen(Screens.TestScreen)
    }

    override fun openOperationChangeScreen(operationChangeInitParams: OperationChangeInitParams) {
        navigateTo(Screens.OperationChangeScreen(operationChangeInitParams))
    }

    override fun openMonthDetailScreen(monthDetailInitParams: MonthDetailInitParams) {
        navigateTo(Screens.MonthDetailScreen(monthDetailInitParams))
    }

    override fun openCalcScreen(calcInitParams: CalcInitParams) {
        navigateTo(Screens.CalcScreen(calcInitParams))
    }*/

    override fun goBack() {
        exit()
    }
}
