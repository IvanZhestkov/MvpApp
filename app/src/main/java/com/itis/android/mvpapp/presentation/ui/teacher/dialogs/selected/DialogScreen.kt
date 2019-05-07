package com.itis.android.mvpapp.presentation.ui.teacher.dialogs.selected

import android.support.v4.app.Fragment
import com.itis.android.mvpapp.presentation.model.DialogModel
import ru.terrakok.cicerone.android.support.SupportAppScreen

class DialogScreen(val dialog: DialogModel): SupportAppScreen() {

    override fun getFragment(): Fragment {
        return DialogFragment.getInstance(dialog)
    }

    override fun getScreenKey(): String {
        return DialogFragment::class.java.name
    }
}