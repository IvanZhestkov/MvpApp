package com.itis.android.mvpapp.presentation.ui.main.grouptask

import com.arellomobile.mvp.InjectViewState
import com.itis.android.mvpapp.presentation.base.BasePresenter
import com.itis.android.mvpapp.router.MainRouter
import javax.inject.Inject

@InjectViewState
class GroupTaskPresenter
@Inject constructor() : BasePresenter<GroupTaskView>() {

    @Inject
    lateinit var groupTaskRouter: MainRouter

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadDataForTable()
    }

    private fun loadDataForTable() {
        viewState.showTable()
    }

    fun openTaskSolutionScreen() {
        groupTaskRouter.openTaskSolutionScreen()
    }
}