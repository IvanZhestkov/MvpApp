package com.itis.android.mvpapp.presentation.ui.teacher.tasksolution

import android.support.v4.app.Fragment
import com.itis.android.mvpapp.data.network.pojo.firebase.response.UserItem
import com.itis.android.mvpapp.data.pojo.TaskSolutionItem
import com.itis.android.mvpapp.presentation.model.UserSolutionModel
import com.itis.android.mvpapp.router.initparams.TaskSolutionInitParams
import ru.terrakok.cicerone.android.support.SupportAppScreen

class TaskSolutionScreen(val user: UserItem?,
                         val solution: TaskSolutionItem?,
                         val taskDeadline: String?) : SupportAppScreen() {

    override fun getFragment(): Fragment {
        return TaskSolutionFragment.getInstance(user, solution, taskDeadline)
    }

    override fun getScreenKey(): String {
        return TaskSolutionFragment::class.java.name
    }
}