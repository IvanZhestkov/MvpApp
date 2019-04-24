package com.itis.android.mvpapp.presentation.ui.main.grouplist

import com.arellomobile.mvp.InjectViewState
import com.itis.android.mvpapp.R
import com.itis.android.mvpapp.data.repository.GroupListRepository
import com.itis.android.mvpapp.model.Group
import com.itis.android.mvpapp.presentation.base.BasePresenter
import com.itis.android.mvpapp.router.MainRouter
import com.itis.android.mvpapp.router.initparams.LoadTaskInitParams
import javax.inject.Inject

@InjectViewState
class GroupListPresenter
@Inject constructor() : BasePresenter<GroupListView>() {

    @Inject
    lateinit var groupListRouter: MainRouter

    @Inject
    lateinit var groupListRepository: GroupListRepository

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        initGroups()
    }

    private fun initGroups() {
        viewState.setGroups(groupListRepository.getGroups())
    }

    fun openLoadTaskScreen(groupId: Int) {
        groupListRouter.openLoadTaskScreen(LoadTaskInitParams(groupId))
    }
}