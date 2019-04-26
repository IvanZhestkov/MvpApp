package com.itis.android.mvpapp.presentation.ui.main.grouplist

import com.arellomobile.mvp.InjectViewState
import com.itis.android.mvpapp.data.repository.GroupListRepository
import com.itis.android.mvpapp.data.repository.TasksRepository
import com.itis.android.mvpapp.presentation.base.BasePresenter
import com.itis.android.mvpapp.presentation.rx.transformer.PresentationSingleTransformer
import com.itis.android.mvpapp.router.MainRouter
import com.itis.android.mvpapp.router.initparams.LoadTaskInitParams
import javax.inject.Inject

@InjectViewState
class GroupListPresenter
@Inject constructor() : BasePresenter<GroupListView>() {

    @Inject
    lateinit var groupListRouter: MainRouter

    @Inject
    lateinit var tasksRepository: TasksRepository

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        update()
    }

    fun openLoadTaskScreen(groupId: Int) {
        groupListRouter.openLoadTaskScreen(LoadTaskInitParams(groupId))
    }

    private fun update() {
        tasksRepository
                .getTasks()
                .compose(PresentationSingleTransformer())
                .doOnSubscribe {
                    viewState.showProgress()
                    viewState.hideRetry()
                    viewState.hideTabs()
                }
                .doAfterTerminate { viewState.hideProgress() }
                .subscribe({
                    viewState.showTabs()
                    viewState.setupViewPager(it)
                }, {
                    it.printStackTrace()
                }).disposeWhenDestroy()
    }

    fun onRetry() {
        update()
    }
}