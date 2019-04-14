package com.itis.android.mvpapp.presentation.ui.main.profile

import android.support.v4.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class ProfileScreen: SupportAppScreen() {

    override fun getFragment(): Fragment {
        return ProfileFragment.getInstance()
    }

    override fun getScreenKey(): String {
        return ProfileFragment::class.java.name
    }
}