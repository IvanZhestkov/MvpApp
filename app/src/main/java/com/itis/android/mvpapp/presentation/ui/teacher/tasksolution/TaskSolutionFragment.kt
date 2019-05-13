package com.itis.android.mvpapp.presentation.ui.teacher.tasksolution

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.support.v4.content.ContextCompat
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.itis.android.mvpapp.R
import com.itis.android.mvpapp.presentation.base.BaseFragment
import com.itis.android.mvpapp.data.network.pojo.firebase.response.UserItem
import com.itis.android.mvpapp.presentation.util.extensions.extractInitParams
import com.itis.android.mvpapp.presentation.util.extensions.hide
import com.itis.android.mvpapp.presentation.util.extensions.putInitParams
import com.itis.android.mvpapp.presentation.util.extensions.show
import com.itis.android.mvpapp.router.initparams.TaskSolutionInitParams
import kotlinx.android.synthetic.main.fragment_task_solution.*
import javax.inject.Inject
import javax.inject.Provider

class TaskSolutionFragment : BaseFragment(), TaskSolutionView, View.OnClickListener {

    companion object {
        fun getInstance(initParams: TaskSolutionInitParams): TaskSolutionFragment {
            return TaskSolutionFragment().also {
                it.putInitParams(initParams)
            }
        }
    }

    override val mainContentLayout = R.layout.fragment_task_solution

    override val enableBackArrow = true

    override val toolbarTitle = R.string.solution

    override val menu: Int?
        get() = null

    @InjectPresenter
    lateinit var presenter: TaskSolutionPresenter

    @Inject
    lateinit var presenterProvider: Provider<TaskSolutionPresenter>

    @ProvidePresenter
    fun providePresenter(): TaskSolutionPresenter {
        return presenterProvider.get().apply {
            init(extractInitParams<TaskSolutionInitParams>().userSolution,
                    extractInitParams<TaskSolutionInitParams>().taskDeadline)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initActionView()
    }

    override fun showStudentName(username: String) {
        tv_task_solution_stud.text = username
    }

    override fun downloadFile(fileName: String, fileExtension: String, url: String) {
        val downloadManager = baseActivity.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadUri = Uri.parse(url)

        val request = DownloadManager.Request(downloadUri)

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalFilesDir(baseActivity, DIRECTORY_DOWNLOADS, fileName + fileExtension)

        downloadManager.enqueue(request)
    }

    override fun updateStatus(status: String) {
        tv_task_solution_status.text = status
    }

    override fun setColorStatus(color: Int) {
        tv_task_solution_status.setTextColor(ContextCompat.getColor(baseActivity, color))
    }

    override fun showButtons() {
        btn_task_solution_confirm.show()
        btn_task_solution_reject.show()
    }

    override fun hideButtons() {
        btn_task_solution_confirm.hide()
        btn_task_solution_reject.hide()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_task_solution_confirm -> {
                presenter.updateSolutionStatus("accepted", edt_comment_field.text.toString())
            }
            R.id.btn_task_solution_reject -> {
                presenter.updateSolutionStatus("rejected", edt_comment_field.text.toString())
            }
            R.id.btn_task_solution_download -> {
                presenter.downloadTaskSolution()
            }
            R.id.tv_task_solution_stud -> {
                presenter.onCreateDialog()
            }
        }
    }

    private fun initActionView() {
        btn_task_solution_confirm.setOnClickListener(this)
        btn_task_solution_reject.setOnClickListener(this)
        btn_task_solution_download.setOnClickListener(this)
        tv_task_solution_stud.setOnClickListener(this)
    }
}