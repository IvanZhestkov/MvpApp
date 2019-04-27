package com.itis.android.mvpapp.presentation.ui.teacher.grouptask

import android.support.v4.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class GroupTaskScreen: SupportAppScreen() {

    override fun getFragment(): Fragment {
        return GroupTaskFragment.getInstance()
    }

    override fun getScreenKey(): String {
        return GroupTaskFragment::class.java.name
    }
}