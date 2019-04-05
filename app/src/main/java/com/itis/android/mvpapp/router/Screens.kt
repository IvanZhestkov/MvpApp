package com.itis.android.mvpapp.router

import com.itis.android.mvpapp.router.initparams.TestInitParams
import com.itis.android.mvpapp.ui.login.LoginFragment
import com.itis.android.mvpapp.ui.test.TestFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    object LoginScreen : SupportAppScreen() {
        override fun getFragment() = LoginFragment.newInstance()
    }

    data class TestScreen(
            val initParams: TestInitParams
    ) : SupportAppScreen() {
        override fun getFragment() = TestFragment.newInstance(initParams)
    }
}