package com.itis.android.mvpapp.presentation.ui.teacher.messages

import android.support.v4.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class MessagesScreen: SupportAppScreen() {

    override fun getFragment(): Fragment {
        return MessagesFragment.getInstance()
    }

    override fun getScreenKey(): String {
        return MessagesFragment::class.java.name
    }
}