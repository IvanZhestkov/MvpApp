package com.itis.android.mvpapp.presentation.ui.student.loadtask

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.itis.android.mvpapp.R
import com.itis.android.mvpapp.data.pojo.TaskSolutionItem
import com.itis.android.mvpapp.data.repository.TaskSolutionRepository
import com.itis.android.mvpapp.data.repository.TeacherRepository
import com.itis.android.mvpapp.presentation.base.BasePresenter
import com.itis.android.mvpapp.presentation.model.FileModel
import com.itis.android.mvpapp.presentation.model.UploadTaskSolutionModel
import com.itis.android.mvpapp.presentation.rx.transformer.PresentationSingleTransformer
import com.itis.android.mvpapp.router.MainRouter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@InjectViewState
class LoadStudentTaskPresenter
@Inject constructor() : BasePresenter<LoadStudentTaskView>() {

    @Inject
    lateinit var router: MainRouter

    @Inject
    lateinit var teacherRepository: TeacherRepository

    @Inject
    lateinit var taskSolutionRepository: TaskSolutionRepository

    private var professorName: String? = null

    private var taskSolutionFile: FileModel? = null

    private var disciplineId: String? = null

    private var taskId: String? = null

    private var taskDeadline: String? = null

    private lateinit var solution: TaskSolutionItem

    fun init(disciplineId: String, taskId: String, taskDeadline: String?) {
        this.disciplineId = disciplineId
        this.taskId = taskId
        this.taskDeadline = taskDeadline
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadSolution()
    }

    private fun loadSolution() {
        taskSolutionRepository
                .getUserTaskSolution(disciplineId, taskId)
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
                        viewState.hideSolution()
                    } else {
                        solution = it[0]
                        viewState.hideLoadButton()
                        viewState.showSolution(solution)
                        viewState.showCommentary(solution.commentary.toString())
                        showSolutionStatus(solution.status.toString())
                    }
                }, {
                    viewState.showRetry("Ошибка. Проверьте подключение к интернету!")
                }).disposeWhenDestroy()
    }

    private fun showSolutionStatus(status: String) {
        when (status) {
            "accepted" -> {
                viewState.showStatus("Принято")
                viewState.setColorStatus(R.color.colorGreen)
            }
            "rejected" -> {
                viewState.showStatus("Отклонено")
                viewState.setColorStatus(R.color.colorRed)
            }
            else -> {
                viewState.showStatus("Не проверено")
                viewState.setColorStatus(R.color.colorYellow)
                if (checkTaskDeadline()) {
                    viewState.showStatus("Просрочено")
                    viewState.setColorStatus(R.color.colorRed)
                }
            }
        }
    }

    private fun checkTaskDeadline(): Boolean {
        val formatter = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val solutionUploadingDate = solution.uploading_date ?: 0
        val dateTask = formatter.parse(taskDeadline)
        val dateSolution = Date(solutionUploadingDate * 1000)

        return dateSolution.after(dateTask) && taskDeadline != formatter.format(dateSolution)
    }

    fun setProfessor(uid: String) {
        teacherRepository.getTeacherByUID(uid)
            .subscribe { it ->
                viewState.setProfersor("${it.first_name} ${it.middle_name}")
                professorName = "${it.last_name} ${it.first_name}"
            }.disposeWhenDestroy()
    }

    fun openProfileForStudent(professorId: String) {
        router.openProfileScreenForStudent(professorId)
    }

    fun onFileChange(file: FileModel) {
        taskSolutionFile = file
        if (taskSolutionFile != null) {
            addSolution()
            loadSolution()
        }
    }

    fun onFileChoose() {
        viewState.openFileChoose()
    }

    fun addSolution() {
        taskSolutionRepository
                .addTaskSolution(
                        UploadTaskSolutionModel(
                                disciplineId,
                                taskId,
                                taskSolutionFile?.file
                        )
                )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { viewState.showWaitDialog() }
                .subscribe({
                    viewState.hideWaitDialog()
                }, {
                    viewState.showErrorDialog("Ошибка при создании таска")
                }).disposeWhenDestroy()
    }

    fun onRetry() {
        loadSolution()
    }
}