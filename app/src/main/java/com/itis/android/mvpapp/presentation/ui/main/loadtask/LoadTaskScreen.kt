package com.itis.android.mvpapp.presentation.ui.main.loadtask

import android.support.v4.app.Fragment
import com.itis.android.mvpapp.presentation.ui.main.grouptask.GroupTaskFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class LoadTaskScreen: SupportAppScreen() {

    override fun getFragment(): Fragment {
        return LoadTaskFragment.getInstance()
    }

    override fun getScreenKey(): String {
        return GroupTaskFragment::class.java.name
    }
}