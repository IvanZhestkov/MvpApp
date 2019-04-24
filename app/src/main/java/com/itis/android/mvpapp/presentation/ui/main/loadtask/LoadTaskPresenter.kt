package com.itis.android.mvpapp.presentation.ui.main.loadtask

import com.arellomobile.mvp.InjectViewState
import com.itis.android.mvpapp.data.repository.GroupListRepository
import com.itis.android.mvpapp.presentation.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class LoadTaskPresenter
@Inject constructor() : BasePresenter<LoadTaskView>() {

    @Inject
    lateinit var groupListRepository: GroupListRepository

    private var groupId: Int = 0

    fun init(groupId: Int) {
        this.groupId = groupId
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showTaskGroup(getGroupNameById(groupId))
    }

    private fun getGroupNameById(groupId: Int): String {
        return groupListRepository.getGroupById(groupId).name
    }
}