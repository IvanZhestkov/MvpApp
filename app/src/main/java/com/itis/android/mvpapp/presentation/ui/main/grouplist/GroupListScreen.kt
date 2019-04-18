package com.itis.android.mvpapp.presentation.ui.main.grouplist

import android.support.v4.app.Fragment
import com.itis.android.mvpapp.presentation.ui.main.grouptask.GroupTaskFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class GroupListScreen: SupportAppScreen() {

    override fun getFragment(): Fragment {
        return GroupListFragment.getInstance()
    }

    override fun getScreenKey(): String {
        return GroupListFragment::class.java.name
    }
}