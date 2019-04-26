package com.itis.android.mvpapp.presentation.ui.main.grouplist.tasks

import android.support.v4.app.Fragment
import com.itis.android.mvpapp.presentation.model.TaskModel
import com.itis.android.mvpapp.presentation.ui.main.grouptask.GroupTaskFragment
import com.itis.android.mvpapp.router.initparams.TasksInitParams
import ru.terrakok.cicerone.android.support.SupportAppScreen

class TasksScreen(val tasks: ArrayList<TaskModel>): SupportAppScreen() {

    override fun getFragment(): Fragment {
        return TasksFragment.getInstance(tasks)
    }

    override fun getScreenKey(): String {
        return TasksFragment::class.java.name
    }
}