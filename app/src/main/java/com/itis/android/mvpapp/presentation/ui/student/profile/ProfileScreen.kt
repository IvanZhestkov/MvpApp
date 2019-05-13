package com.itis.android.mvpapp.presentation.ui.student.profile

import ru.terrakok.cicerone.android.support.SupportAppScreen

class ProfileScreen : SupportAppScreen() {

    override fun getFragment(): Fragment {
        return ProfileFragment.getInstance()
    }

    override fun getScreenKey(): String {
        return ProfileFragment::class.java.name
    }
}