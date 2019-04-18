package com.itis.android.mvpapp.presentation.ui.main.grouplist.tasks

import com.arellomobile.mvp.InjectViewState
import com.itis.android.mvpapp.presentation.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class TasksPresenter
@Inject constructor() : BasePresenter<TasksView>() {
    private var groupId: Int = 0


    fun init(groupId: Int) {
        this.groupId = groupId
    }
}