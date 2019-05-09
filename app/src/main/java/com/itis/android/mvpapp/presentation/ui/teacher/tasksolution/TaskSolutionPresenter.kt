package com.itis.android.mvpapp.presentation.ui.teacher.tasksolution

import com.arellomobile.mvp.InjectViewState
import com.itis.android.mvpapp.data.repository.DialogsRepository
import com.google.firebase.storage.FirebaseStorage
import com.itis.android.mvpapp.R
import com.itis.android.mvpapp.data.repository.MessagesRepository
import com.itis.android.mvpapp.data.repository.TaskSolutionRepository
import com.itis.android.mvpapp.presentation.base.BasePresenter
import com.itis.android.mvpapp.presentation.model.UserSolutionModel
import com.itis.android.mvpapp.presentation.rx.transformer.PresentationObservableTransformer
import com.itis.android.mvpapp.router.MainRouter
import javax.inject.Inject

@InjectViewState
class TaskSolutionPresenter @Inject constructor() : BasePresenter<TaskSolutionView>() {

    private var userSolution: UserSolutionModel? = null

    @Inject
    lateinit var taskSolutionRepository: TaskSolutionRepository

    @Inject
    lateinit var firebaseStorage: FirebaseStorage

    @Inject
    lateinit var dialogsRepository: DialogsRepository

    @Inject
    lateinit var router: MainRouter

    fun init(userSolution: UserSolutionModel) {
        this.userSolution = userSolution
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        checkStateButtons()
        userSolution?.solution?.status?.let { updateSolutionStatus(it) }
        userSolution?.user?.let { viewState.showStudentName(it) }
    }

    private fun checkStateButtons() {
        when (userSolution?.solution?.status) {
            "accepted" -> viewState.hideButtons()
            "rejected" -> viewState.hideButtons()
            else -> viewState.showButtons()
        }
    }

    private fun addSolutionComment(comment: String) {
        val solution = userSolution?.solution
        solution?.comment = comment
        solution?.let { taskSolutionRepository.addSolutionCommnet(it) }
    }

    private fun updateSolutionStatus(status: String) {
        when (status) {
            "accepted" -> {
                viewState.updateStatus("Принято")
                viewState.setColorStatus(R.color.colorGreen)
            }
            "rejected" -> {
                viewState.updateStatus("Отклонено")
                viewState.setColorStatus(R.color.colorRed)
            }
            else -> {
                viewState.updateStatus("Не проверено")
                viewState.setColorStatus(R.color.colorYellow)
            }
        }
    }

    fun updateSolutionStatus(status: String, comment: String) {
        val solution = userSolution?.solution
        solution?.status = status
        solution?.let { taskSolutionRepository.updateSolutionStatus(it) }
        addSolutionComment(comment)
        updateSolutionStatus(status)
        viewState.hideButtons()
    }

    fun downloadTaskSolution() {
        val storageRef = firebaseStorage.reference
        val ref = storageRef.child("test_storage")

        ref.downloadUrl
                .addOnSuccessListener {
                    viewState.downloadFile("Mobile", ".mpeg", it.toString())
                }
                .addOnFailureListener {
                }
    }

    fun onCreateDialog() {
        dialogsRepository
                .createDialog(userSolution?.solution?.userId.orEmpty())
                .compose(PresentationObservableTransformer())
                .subscribe({ dialogId ->
                    router.openDialogScreen(dialogId)
                }, {
                    it.printStackTrace()
                })
                .disposeWhenDestroy()

    }
}