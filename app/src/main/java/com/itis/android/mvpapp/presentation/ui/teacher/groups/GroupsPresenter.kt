package com.itis.android.mvpapp.presentation.ui.teacher.groups

import com.arellomobile.mvp.InjectViewState
import com.itis.android.mvpapp.data.repository.GroupsRepository
import com.itis.android.mvpapp.presentation.base.BasePresenter
import com.itis.android.mvpapp.presentation.rx.transformer.PresentationSingleTransformer
import com.itis.android.mvpapp.router.MainRouter
import com.itis.android.mvpapp.router.initparams.LoadTaskInitParams
import javax.inject.Inject

@InjectViewState
class GroupsPresenter
@Inject constructor() : BasePresenter<GroupsView>() {

    @Inject
    lateinit var groupListRouter: MainRouter

    @Inject
    lateinit var groupsRepository: GroupsRepository


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        update()
    }

    fun openLoadTaskScreen(groupId: String) {
        groupListRouter.openLoadTaskScreen(LoadTaskInitParams(groupId))
    }

    private fun update() {
        groupsRepository
                .getGroupsSingle()
                .compose(PresentationSingleTransformer())
                .doOnSubscribe {
                    viewState.showProgress()
                    viewState.hideRetry()
                    viewState.hideTabs()
                }
                .doAfterTerminate { viewState.hideProgress() }
                .subscribe({
                    viewState.showTabs()
                    viewState.setupViewPager(it.toMutableList())
                }, {
                    viewState.showRetry("Ошибка")
                }).disposeWhenDestroy()
    }

    fun onRetry() {
        update()
    }
}