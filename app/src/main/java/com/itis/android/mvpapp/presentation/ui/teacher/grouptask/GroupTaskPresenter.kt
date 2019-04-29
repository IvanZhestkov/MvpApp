package com.itis.android.mvpapp.presentation.ui.teacher.grouptask

import com.arellomobile.mvp.InjectViewState
import com.itis.android.mvpapp.data.repository.TaskSolutionRepository
import com.itis.android.mvpapp.presentation.base.BasePresenter
import com.itis.android.mvpapp.presentation.model.TaskModel
import com.itis.android.mvpapp.presentation.model.UserSolutionModel
import com.itis.android.mvpapp.presentation.rx.transformer.PresentationSingleTransformer
import com.itis.android.mvpapp.router.MainRouter
import com.itis.android.mvpapp.router.initparams.TaskSolutionInitParams
import javax.inject.Inject

@InjectViewState
class GroupTaskPresenter
@Inject constructor() : BasePresenter<GroupTaskView>() {

    @Inject
    lateinit var groupTaskRouter: MainRouter

    @Inject
    lateinit var taskSolutionRepository: TaskSolutionRepository

    private var task: TaskModel? = null

    fun init(task: TaskModel) {
        this.task = task
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadDataForTable()
    }

    fun onRetry() {
        loadDataForTable()
    }

    fun openTaskSolutionScreen(userSolution: UserSolutionModel) {
        groupTaskRouter.openTaskSolutionScreen(TaskSolutionInitParams(userSolution))
    }

    private fun loadDataForTable() {
        taskSolutionRepository
            .getTaskSolutions(task?.disciplineId, task?.taskId)
            .compose(PresentationSingleTransformer())
            .doOnSubscribe {
                viewState.showProgress()
                viewState.hideRetry()
            }
            .doAfterTerminate {
                viewState.hideProgress()
            }
            .subscribe({
                viewState.showTable(it)
            }, {
                viewState.showRetry("Ошибка")
            }).disposeWhenDestroy()
    }
}