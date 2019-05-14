package com.itis.android.mvpapp.presentation.ui.teacher.grouptask

import android.support.v4.app.Fragment
import com.itis.android.mvpapp.router.initparams.GroupTaskInitParams
import ru.terrakok.cicerone.android.support.SupportAppScreen

class GroupTaskScreen(val initParams: GroupTaskInitParams): SupportAppScreen() {

    override fun getFragment(): Fragment {
        return GroupTaskFragment.getInstance(initParams)
    }

    override fun getScreenKey(): String {
        return GroupTaskFragment::class.java.name
    }
}