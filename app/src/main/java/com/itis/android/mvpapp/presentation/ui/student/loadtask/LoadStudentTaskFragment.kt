package com.itis.android.mvpapp.presentation.ui.student.loadtask

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.firebase.storage.FirebaseStorage
import com.itis.android.mvpapp.R
import com.itis.android.mvpapp.presentation.base.BaseFragment
import com.itis.android.mvpapp.presentation.util.extensions.putInitParams
import kotlinx.android.synthetic.main.fragment_student_task.*
import javax.inject.Inject
import javax.inject.Provider

class LoadStudentTaskFragment : BaseFragment(), LoadStudentTaskView {

    companion object {
        fun getInstance(initParams: TaskModelInitParam): LoadStudentTaskFragment {
            return LoadStudentTaskFragment().also {
                it.putInitParams(initParams)
            }
        }
    }

    override fun showProgress() {
    }

    override fun hideProgress() {
    }

    override val mainContentLayout = R.layout.fragment_student_task

    override val enableBackArrow = true

    override val toolbarTitle = R.string.zadanie

    override val menu: Int?
        get() = null

    @InjectPresenter
    lateinit var presenter: LoadStudentTaskPresenter

    @Inject
    lateinit var presenterProvider: Provider<LoadStudentTaskPresenter>

    @Inject
    lateinit var firebaseStorage: FirebaseStorage

    @ProvidePresenter
    fun providePresenter(): LoadStudentTaskPresenter {
        return presenterProvider.get().apply {
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAllData()

        val taskModelInitParam = arguments?.getParcelable<TaskModelInitParam>(".init.params")

        btn_task_download.setOnClickListener {
            val storageRef = firebaseStorage.reference
            val ref = taskModelInitParam?.filePath.let { storageRef.child(it!!) }

            ref.downloadUrl.addOnSuccessListener {
                downloadFile(
                    arguments?.getParcelable<TaskModelInitParam>(".init.params")?.filePath.toString(),
                    ".pdf",
                    it.toString()
                )
            }.addOnFailureListener {
            }
        }
        tv_teacher_name.setOnClickListener { presenter.openProfileForStudent(taskModelInitParam!!.professorId!!) }
    }

    override fun setAllData() {

        val taskModelInitParam = arguments?.getParcelable<TaskModelInitParam>(".init.params")

        tv_task_name.text = taskModelInitParam!!.disciplineName
        tv_deadline_description.text = taskModelInitParam.expiration_date
        tv_task_description_text.text = taskModelInitParam.description

        presenter.setProfessor(taskModelInitParam.professorId!!)
    }

    override fun downloadFile(fileName: String, fileExtension: String, url: String) {
        val downloadManager = baseActivity.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadUri = Uri.parse(url)

        val request = DownloadManager.Request(downloadUri)

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalFilesDir(
            baseActivity,
            Environment.DIRECTORY_DOWNLOADS,
            fileName + fileExtension
        )

        downloadManager.enqueue(request)
    }

    override fun setProfersor(name: String) {
        tv_teacher_name.text = name
    }
}