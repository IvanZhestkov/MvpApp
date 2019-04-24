package com.itis.android.mvpapp.presentation.ui.main.loadtask

import android.support.v4.app.Fragment
import com.itis.android.mvpapp.presentation.ui.main.grouptask.GroupTaskFragment
import com.itis.android.mvpapp.router.initparams.LoadTaskInitParams
import ru.terrakok.cicerone.android.support.SupportAppScreen

class LoadTaskScreen(val initParams: LoadTaskInitParams): SupportAppScreen() {

    override fun getFragment(): Fragment {
        return LoadTaskFragment.getInstance(initParams)
    }

    override fun getScreenKey(): String {
        return GroupTaskFragment::class.java.name
    }
}