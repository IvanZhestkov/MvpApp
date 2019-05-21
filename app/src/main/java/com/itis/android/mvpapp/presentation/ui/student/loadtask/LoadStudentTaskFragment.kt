package com.itis.android.mvpapp.presentation.ui.student.loadtask

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.support.v4.content.ContextCompat
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.firebase.storage.FirebaseStorage
import com.itis.android.mvpapp.R
import com.itis.android.mvpapp.data.pojo.TaskSolutionItem
import com.itis.android.mvpapp.presentation.base.BaseFragment
import com.itis.android.mvpapp.presentation.model.FileModel
import com.itis.android.mvpapp.presentation.util.extensions.*
import kotlinx.android.synthetic.main.fragment_student_task.*
import kotlinx.android.synthetic.main.fragment_student_task.btn_task_download
import kotlinx.android.synthetic.main.fragment_student_task.tv_task_name
import kotlinx.android.synthetic.main.layout_progress_error.*
import javax.inject.Inject
import javax.inject.Provider

class LoadStudentTaskFragment : BaseFragment(), LoadStudentTaskView {

    companion object {
        private const val REQUEST_CODE_PICK_FILE = 111

        fun getInstance(initParams: TaskModelInitParam): LoadStudentTaskFragment {
            return LoadStudentTaskFragment().also {
                it.putInitParams(initParams)
            }
        }
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
            val disciplineId = extractInitParams<TaskModelInitParam>().disciplineId.orEmpty()
            val taskId = extractInitParams<TaskModelInitParam>().taskId.orEmpty()
            val taskDeadline = extractInitParams<TaskModelInitParam>().expiration_date.orEmpty()
            init(disciplineId, taskId, taskDeadline)
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

        initActionView()
    }

    private fun initActionView() {
        btn_task_solution_send.setOnClickListener { presenter.onFileChoose() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_PICK_FILE) {
            data?.data?.let { uri ->
                val file = context?.contentResolver?.openInputStream(uri)?.readBytes()
                val fileName = uri.getFilename(context)
                val fileType = context?.contentResolver?.getType(uri)

                presenter.onFileChange(FileModel(file, fileName, fileType))
            }
        }
    }

    override fun showCommentary(text: String) {
        tv_comment.text = text
    }

    override fun showStatus(status: String) {
        tv_status.text = status
    }

    override fun setColorStatus(color: Int) {
        tv_status.setTextColor(ContextCompat.getColor(baseActivity, color))
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

    override fun hideLoadButton() {
        btn_task_solution_send.hide()
    }

    override fun showSolution(solution: TaskSolutionItem) {
        second_rl.show()
    }

    override fun hideSolution() {
        second_rl.hide()
    }

    override fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress.visibility = View.GONE
    }

    override fun showRetry(errorText: String) {
        text_retry.text = errorText
        btn_retry.setOnClickListener { presenter.onRetry() }

        progress_error.visibility = View.VISIBLE
    }

    override fun hideRetry() {
        progress_error.visibility = View.GONE
    }

    override fun setProfersor(name: String) {
        tv_teacher_name.text = name
    }

    override fun openFileChoose() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/*"
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(intent, REQUEST_CODE_PICK_FILE)
    }
}