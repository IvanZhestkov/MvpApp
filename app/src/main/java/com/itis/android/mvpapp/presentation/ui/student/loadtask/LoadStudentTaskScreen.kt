package com.itis.android.mvpapp.presentation.ui.student.loadtask

import android.support.v4.app.Fragment
import com.itis.android.mvpapp.presentation.model.TaskModel
import ru.terrakok.cicerone.android.support.SupportAppScreen

class LoadStudentTaskScreen(val taskModel: TaskModelInitParam): SupportAppScreen() {

    override fun getFragment(): Fragment {
        return LoadStudentTaskFragment.getInstance(taskModel)
    }

    override fun getScreenKey(): String {
        return super.getScreenKey()
    }

}