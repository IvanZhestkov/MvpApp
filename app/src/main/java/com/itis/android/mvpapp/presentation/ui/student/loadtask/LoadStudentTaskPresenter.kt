package com.itis.android.mvpapp.presentation.ui.student.loadtask

import com.arellomobile.mvp.InjectViewState
import com.google.firebase.storage.FirebaseStorage
import com.itis.android.mvpapp.data.repository.DialogsRepository
import com.itis.android.mvpapp.data.repository.StudentRepository
import com.itis.android.mvpapp.data.repository.TeacherRepository
import com.itis.android.mvpapp.presentation.base.BasePresenter
import com.itis.android.mvpapp.presentation.model.TaskModel
import com.itis.android.mvpapp.presentation.rx.transformer.PresentationObservableTransformer
import com.itis.android.mvpapp.router.MainRouter
import javax.inject.Inject

@InjectViewState
class LoadStudentTaskPresenter
@Inject constructor() : BasePresenter<LoadStudentTaskView>() {

    @Inject
    lateinit var teacherRepository: TeacherRepository

    @Inject
    lateinit var dialogsRepository: DialogsRepository

    private var professorName: String? = null

    @Inject
    lateinit var router: MainRouter

    fun setProfessor(uid: String) {
        teacherRepository.getTeacherByUID(uid)
            .subscribe { it ->
                viewState.setProfersor("${it.first_name} ${it.middle_name}")
                professorName = "${it.last_name} ${it.first_name}"
            }.disposeWhenDestroy()
    }

    fun onCreateDialog(professorId: String) {
        dialogsRepository
            .createDialog(professorId)
            .compose(PresentationObservableTransformer())
            .subscribe({ dialogId ->
                router.openDialogScreen(dialogId, professorName!!)
            }, {
                it.printStackTrace()
            })
            .disposeWhenDestroy()

    }

}