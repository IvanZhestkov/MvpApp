package com.itis.android.mvpapp.presentation.ui.teacher.groups

import com.arellomobile.mvp.InjectViewState
import com.google.firebase.auth.FirebaseAuth
import com.itis.android.mvpapp.data.repository.GroupsRepository
import com.itis.android.mvpapp.presentation.base.BasePresenter
import com.itis.android.mvpapp.presentation.rx.transformer.PresentationSingleTransformer
import com.itis.android.mvpapp.router.MainRouter
import com.itis.android.mvpapp.router.initparams.NewTaskInitParams
import javax.inject.Inject

@InjectViewState
class GroupsPresenter
@Inject constructor() : BasePresenter<GroupsView>() {

    @Inject
    lateinit var groupListRouter: MainRouter

    @Inject
    lateinit var groupsRepository: GroupsRepository

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        update()
    }

    fun openLoadTaskScreen(groupId: String) {
        groupListRouter.openNewTaskScreen(NewTaskInitParams(groupId))
    }

    private fun update() {
        groupsRepository
                .getGroupsSingle(firebaseAuth.currentUser?.uid.toString())
                .compose(PresentationSingleTransformer())
                .doOnSubscribe {
                    viewState.showProgress()
                    viewState.hideRetry()
                    viewState.hideTabs()
                    viewState.hideFAB()
                }
                .doAfterTerminate { viewState.hideProgress() }
                .subscribe({
                    viewState.showTabs()
                    viewState.showFAB()
                    viewState.setupViewPager(it.toMutableList())
                }, {
                    viewState.showRetry("Ошибка")
                }).disposeWhenDestroy()
    }

    fun onRetry() {
        update()
    }
}