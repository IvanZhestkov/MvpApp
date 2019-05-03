package com.itis.android.mvpapp.presentation.ui.teacher.newtask

import com.arellomobile.mvp.InjectViewState
import com.itis.android.mvpapp.presentation.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class NewTaskPresenter
@Inject constructor() : BasePresenter<NewTaskView>() {

    private var groupName: String = ""

    fun init(groupName: String) {
        this.groupName = groupName
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showTaskGroup(groupName)
    }

    private fun getGroupNameById(groupId: Int): String {
        return "asd"
    }
}