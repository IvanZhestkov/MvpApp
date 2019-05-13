package com.itis.android.mvpapp.presentation.ui.teacher.tasksolution

import android.support.v4.app.Fragment
import com.itis.android.mvpapp.router.initparams.TaskSolutionInitParams
import ru.terrakok.cicerone.android.support.SupportAppScreen

class TaskSolutionScreen(val initParams: TaskSolutionInitParams): SupportAppScreen() {

    override fun getFragment(): Fragment {
        return TaskSolutionFragment.getInstance(initParams)
    }

    override fun getScreenKey(): String {
        return TaskSolutionFragment::class.java.name
    }
}