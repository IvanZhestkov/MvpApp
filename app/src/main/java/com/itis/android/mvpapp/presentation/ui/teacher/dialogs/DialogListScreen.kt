package com.itis.android.mvpapp.presentation.ui.teacher.dialogs

import android.support.v4.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class DialogListScreen: SupportAppScreen() {

    override fun getFragment(): Fragment {
        return DialogListFragment.getInstance()
    }

    override fun getScreenKey(): String {
        return DialogListFragment::class.java.name
    }
}