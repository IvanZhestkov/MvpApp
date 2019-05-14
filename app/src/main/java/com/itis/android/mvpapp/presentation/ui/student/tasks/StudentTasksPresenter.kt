package com.itis.android.mvpapp.presentation.ui.student.tasks

import com.arellomobile.mvp.InjectViewState
import com.itis.android.mvpapp.data.repository.GroupsRepository
import com.itis.android.mvpapp.data.repository.StudentRepository
import com.itis.android.mvpapp.data.repository.TasksRepository
import com.itis.android.mvpapp.presentation.base.BasePresenter
import com.itis.android.mvpapp.presentation.model.TaskModel
import com.itis.android.mvpapp.presentation.rx.transformer.PresentationObservableTransformer
import com.itis.android.mvpapp.presentation.rx.transformer.PresentationSingleTransformer
import com.itis.android.mvpapp.presentation.ui.student.loadtask.TaskModelInitParam
import com.itis.android.mvpapp.router.MainRouter
import javax.inject.Inject

@InjectViewState
class StudentTasksPresenter @Inject constructor() : BasePresenter<StudentTasksView>() {

    @Inject
    lateinit var router: MainRouter

    @Inject
    lateinit var tasksRepository: TasksRepository

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        tasksRepository.getTasksForStudent()
            .subscribe { it ->
                viewState.setTasks(it)
            }
            .disposeWhenDestroy()
    }

    fun onTaskClick(taskModel: TaskModel) {
        router.openLoadStudentTaskScreen(
            TaskModelInitParam(
                expiration_date = taskModel.expiration_date,
                description = taskModel.description,
                name = taskModel.name,
                filePath = taskModel.filePath,
                taskId = taskModel.taskId,
                disciplineId = taskModel.disciplineId,
                groupNumber = taskModel.groupNumber,
                disciplineName = taskModel.disciplineName,
                professorId = taskModel.professorId
            )
        )
    }
}