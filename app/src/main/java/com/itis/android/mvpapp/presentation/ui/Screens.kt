package com.itis.android.mvpapp.presentation.ui

import com.itis.android.mvpapp.router.initparams.TestInitParams
import com.itis.android.mvpapp.presentation.ui.auth.login.LoginFragment
import com.itis.android.mvpapp.presentation.ui.main.grouptask.GroupTaskScreen
import com.itis.android.mvpapp.presentation.ui.main.test.TestFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    object LoginScreen : SupportAppScreen() {
        override fun getFragment() = LoginFragment.newInstance()

        override fun getScreenKey(): String {
            return LoginFragment::class.java.name
        }
    }

    data class TestScreen(
            val initParams: TestInitParams
    ) : SupportAppScreen() {
        override fun getFragment() = TestFragment.newInstance(initParams)
    }

    fun getGroupTaskScreen(): GroupTaskScreen = GroupTaskScreen()
}