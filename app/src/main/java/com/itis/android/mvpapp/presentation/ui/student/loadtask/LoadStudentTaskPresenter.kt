package com.itis.android.mvpapp.presentation.ui.student.loadtask

import com.arellomobile.mvp.InjectViewState
import com.google.firebase.storage.FirebaseStorage
import com.itis.android.mvpapp.data.repository.StudentRepository
import com.itis.android.mvpapp.data.repository.TeacherRepository
import com.itis.android.mvpapp.presentation.base.BasePresenter
import com.itis.android.mvpapp.presentation.model.TaskModel
import javax.inject.Inject

@InjectViewState
class LoadStudentTaskPresenter
@Inject constructor() : BasePresenter<LoadStudentTaskView>() {

    @Inject
    lateinit var teacherRepository: TeacherRepository



    private var filePath: String? = null

    fun init(filepath: String) {
        this.filePath = filepath
    }

    fun setProfessor(uid: String) {
        teacherRepository.getTeacherByUID(uid)
            .subscribe { it ->
                viewState.setProfersor("${it.first_name} ${it.middle_name}")
            }.disposeWhenDestroy()
    }
}