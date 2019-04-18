package com.itis.android.mvpapp.presentation.ui.main.grouplist

import com.arellomobile.mvp.InjectViewState
import com.itis.android.mvpapp.R
import com.itis.android.mvpapp.model.Group
import com.itis.android.mvpapp.presentation.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class GroupListPresenter
@Inject constructor() : BasePresenter<GroupListView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        initGroups()
    }

    private fun initGroups() {
        viewState.setGroups(getGroups())
    }

    private fun getGroups() = listOf(
            Group(0, "11-601"),
            Group(1, "11-602"),
            Group(2, "11-603"),
            Group(3, "11-604"),
            Group(4, "11-605"),
            Group(5, "11-606"),
            Group(6, "11-607"),
            Group(7, "11-608")
    )
}