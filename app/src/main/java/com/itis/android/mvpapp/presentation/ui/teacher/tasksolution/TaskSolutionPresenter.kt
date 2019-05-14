package com.itis.android.mvpapp.presentation.ui.teacher.tasksolution

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.itis.android.mvpapp.data.repository.DialogsRepository
import com.google.firebase.storage.FirebaseStorage
import com.itis.android.mvpapp.R
import com.itis.android.mvpapp.data.repository.TaskSolutionRepository
import com.itis.android.mvpapp.presentation.base.BasePresenter
import com.itis.android.mvpapp.presentation.model.UserSolutionModel
import com.itis.android.mvpapp.presentation.rx.transformer.PresentationObservableTransformer
import com.itis.android.mvpapp.router.MainRouter
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@InjectViewState
class TaskSolutionPresenter @Inject constructor() : BasePresenter<TaskSolutionView>() {

    @Inject
    lateinit var taskSolutionRepository: TaskSolutionRepository

    @Inject
    lateinit var firebaseStorage: FirebaseStorage

    @Inject
    lateinit var dialogsRepository: DialogsRepository

    @Inject
    lateinit var router: MainRouter

    private var userSolution: UserSolutionModel? = null

    private var username: String? = null

    private var taskDeadline: String? = null

    fun init(userSolution: UserSolutionModel, taskDeadline: String?) {
        this.userSolution = userSolution
        this.taskDeadline = taskDeadline
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        checkButtonsState()
        userSolution?.solution?.status?.let { updateSolutionStatus(it) }
        viewState.showStudentName(getUsername(userSolution))
        viewState.showCommentary(userSolution?.solution?.commentary.toString())
    }

    private fun checkButtonsState() {
        when (userSolution?.solution?.status) {
            "accepted" -> viewState.hideButtons()
            "rejected" -> viewState.hideButtons()
            else -> {
                viewState.showButtons()
                if (checkTaskDeadline()) {
                    viewState.hideButtons()
                }
            }
        }
    }

    private fun addSolutionComment(comment: String) {
        val solution = userSolution?.solution
        solution?.commentary = comment
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
                if (checkTaskDeadline()) {
                    viewState.updateStatus("Просрочено")
                    viewState.setColorStatus(R.color.colorRed)
                }
            }
        }
    }

    private fun checkTaskDeadline(): Boolean {
        val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val solutionUploadingDate = userSolution?.solution?.uploading_date ?: 0
        val dateTask = formatter.parse(taskDeadline)
        val dateSolution = Date(solutionUploadingDate * 1000)

        Log.d("dateTask TAG", taskDeadline)
        Log.d("dateSolution TAG", formatter.format(dateSolution))

        return dateSolution.after(dateTask) && taskDeadline != formatter.format(dateSolution)
    }

    private fun getUsername(userSolution: UserSolutionModel?) = "${userSolution?.user?.last_name} ${userSolution?.user?.first_name}"

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
        val fileName = userSolution?.solution?.solution_file_link.toString()
        val ref = storageRef.child(fileName)

        ref.downloadUrl
                .addOnSuccessListener {
                    viewState.downloadFile(fileName, ".pdf", it.toString())
                }
                .addOnFailureListener {
                }
    }

    fun onCreateDialog() {
        dialogsRepository
                .createDialog(userSolution?.solution?.userId.orEmpty())
                .compose(PresentationObservableTransformer())
                .subscribe({ dialogId ->
                    router.openDialogScreen(dialogId, getUsername(userSolution))
                }, {
                    it.printStackTrace()
                })
                .disposeWhenDestroy()

    }
}