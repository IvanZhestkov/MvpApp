package com.itis.android.mvpapp.presentation.ui.teacher.dialogs.selected

import android.support.v4.app.Fragment
import com.itis.android.mvpapp.presentation.model.DialogModel
import ru.terrakok.cicerone.android.support.SupportAppScreen

class DialogScreen(val dialogId: String, val username: String): SupportAppScreen() {

    override fun getFragment(): Fragment {
        return DialogFragment.getInstance(dialogId, username)
    }

    override fun getScreenKey(): String {
        return DialogFragment::class.java.name
    }
}