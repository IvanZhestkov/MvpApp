package com.itis.android.mvpapp.presentation.ui.student.uploadtask

import android.support.v4.app.Fragment
import com.itis.android.mvpapp.presentation.ui.teacher.grouptask.GroupTaskFragment
import com.itis.android.mvpapp.router.initparams.NewTaskInitParams
import ru.terrakok.cicerone.android.support.SupportAppScreen

class NewStudentTaskScreen(val initParams: NewTaskInitParams): SupportAppScreen() {

    override fun getFragment(): Fragment {
        return NewStudentTaskFragment.getInstance(initParams)
    }

    override fun getScreenKey(): String {
        return GroupTaskFragment::class.java.name
    }
}