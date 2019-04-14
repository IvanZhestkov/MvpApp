package com.itis.android.mvpapp.presentation.ui

import com.itis.android.mvpapp.router.initparams.TestInitParams
import com.itis.android.mvpapp.presentation.ui.auth.login.LoginFragment
import com.itis.android.mvpapp.presentation.ui.main.grouptask.GroupTaskScreen
import com.itis.android.mvpapp.presentation.ui.main.loadtask.LoadTaskScreen
import com.itis.android.mvpapp.presentation.ui.main.profile.ProfileFragment
import com.itis.android.mvpapp.presentation.ui.main.test.TestFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

    object LoginScreen : SupportAppScreen() {
        override fun getFragment() = LoginFragment.newInstance()

        override fun getScreenKey(): String {
            return LoginFragment::class.java.name
        }
    }

    object ProfileScreen : SupportAppScreen() {
        override fun getFragment() = ProfileFragment.newInstance()

        override fun getScreenKey(): String {
            return ProfileFragment::class.java.name
        }
    }

    data class TestScreen(
            val initParams: TestInitParams
    ) : SupportAppScreen() {
        override fun getFragment() = TestFragment.newInstance(initParams)
    }

    fun getLoadTaskScreen(): LoadTaskScreen = LoadTaskScreen()

    fun getGroupTaskScreen(): GroupTaskScreen = GroupTaskScreen()
}