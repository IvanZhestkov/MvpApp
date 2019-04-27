package com.itis.android.mvpapp.presentation.ui.teacher.tasksolution

import android.support.v4.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class TaskSolutionScreen: SupportAppScreen() {

    override fun getFragment(): Fragment {
        return TaskSolutionFragment.getInstance()
    }

    override fun getScreenKey(): String {
        return TaskSolutionFragment::class.java.name
    }
}