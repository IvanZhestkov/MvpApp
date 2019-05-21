package com.itis.android.mvpapp.presentation.ui.teacher.grouptask

import com.arellomobile.mvp.InjectViewState
import com.google.firebase.storage.FirebaseStorage
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

    @Inject
    lateinit var firebaseStorage: FirebaseStorage

    private var task: TaskModel? = null

    fun init(task: TaskModel) {
        this.task = task
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        showTaskName()
        showTaskDescription()
        loadDataForTable()
    }

    private fun showTaskName() {
        task?.name?.let { viewState.showTaskName(it) }
    }

    private fun showTaskDescription() {
        task?.description?.let { viewState.showTaskDescription(it) }
    }

    private fun loadDataForTable() {
        taskSolutionRepository
                .getTaskSolutions(task?.disciplineId, task?.taskId, task?.groupNumber)
                .compose(PresentationSingleTransformer())
                .doOnSubscribe {
                    viewState.showProgress()
                    viewState.hideRetry()
                }
                .doAfterTerminate {
                    viewState.hideProgress()
                }
                .subscribe({
                    if (it.isEmpty()) {
                        viewState.hideTable()
                    } else {
                        viewState.showTable()
                        viewState.showTableSolutions(it)
                    }
                }, {
                    viewState.showRetry("Ошибка")
                }).disposeWhenDestroy()
    }

    fun onRetry() {
        loadDataForTable()
    }

    fun openTaskSolutionScreen(userSolution: UserSolutionModel) {
        groupTaskRouter.openTaskSolutionScreen2(userSolution.user, userSolution.solution, task?.expiration_date)
    }

    fun dowloadTask() {
        val storageRef = firebaseStorage.reference
        val ref = task?.filePath?.let { storageRef.child(it) }

        ref?.downloadUrl
                ?.addOnSuccessListener {
                    viewState.downloadFile(task?.filePath.toString(), ".pdf", it.toString())
                }
                ?.addOnFailureListener {
                    viewState.showErrorDialog("Ошибка скачивания. Задание не доступно для скачивания!")
                }
    }
}