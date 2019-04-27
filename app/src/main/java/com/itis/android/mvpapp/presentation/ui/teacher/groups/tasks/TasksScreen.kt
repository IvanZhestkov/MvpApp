package com.itis.android.mvpapp.presentation.ui.teacher.groups.tasks

import android.support.v4.app.Fragment
import com.itis.android.mvpapp.presentation.model.GroupModel
import ru.terrakok.cicerone.android.support.SupportAppScreen

class TasksScreen(val group: GroupModel): SupportAppScreen() {

    override fun getFragment(): Fragment {
        return TasksFragment.getInstance(group)
    }

    override fun getScreenKey(): String {
        return TasksFragment::class.java.name
    }
}