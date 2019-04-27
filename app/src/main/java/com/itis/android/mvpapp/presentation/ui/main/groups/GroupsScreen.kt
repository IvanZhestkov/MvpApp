package com.itis.android.mvpapp.presentation.ui.main.groups

import android.support.v4.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class GroupsScreen: SupportAppScreen() {

    override fun getFragment(): Fragment {
        return GroupsFragment.getInstance()
    }

    override fun getScreenKey(): String {
        return GroupsFragment::class.java.name
    }
}