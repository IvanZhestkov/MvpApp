package com.itis.android.mvpapp.presentation.ui.teacher.grouptask

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.support.v7.widget.RecyclerView
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.itis.android.mvpapp.R
import com.itis.android.mvpapp.presentation.model.table.GroupTaskColumnHeader
import com.itis.android.mvpapp.presentation.adapter.GroupTaskTableAdapter
import com.itis.android.mvpapp.presentation.adapter.tableClickListener.TableClickListenerAdapter
import com.itis.android.mvpapp.presentation.base.BaseFragment
import com.itis.android.mvpapp.presentation.model.UserSolutionModel
import com.itis.android.mvpapp.presentation.model.table.GroupTaskCell
import com.itis.android.mvpapp.presentation.model.table.GroupTaskRowHeader
import com.itis.android.mvpapp.presentation.util.extensions.extractInitParams
import com.itis.android.mvpapp.presentation.util.extensions.hide
import com.itis.android.mvpapp.presentation.util.extensions.putInitParams
import com.itis.android.mvpapp.presentation.util.extensions.show
import com.itis.android.mvpapp.router.initparams.GroupTaskInitParams
import kotlinx.android.synthetic.main.fragment_group_task.*
import kotlinx.android.synthetic.main.layout_progress_error.*
import javax.inject.Inject
import javax.inject.Provider

class GroupTaskFragment : BaseFragment(), GroupTaskView {

    private lateinit var userSolutions: List<UserSolutionModel>

    companion object {
        fun getInstance(initParams: GroupTaskInitParams): GroupTaskFragment {
            return GroupTaskFragment().also {
                it.putInitParams(initParams)
            }
        }
    }

    override val mainContentLayout = R.layout.fragment_group_task

    override val enableBackArrow = true

    override val toolbarTitle = R.string.toolbar_task

    override val menu: Int?
        get() = null

    @InjectPresenter
    lateinit var presenter: GroupTaskPresenter

    @Inject
    lateinit var presenterProvider: Provider<GroupTaskPresenter>

    var adapter: GroupTaskTableAdapter? = null

    @ProvidePresenter
    fun providePresenter(): GroupTaskPresenter {
        return presenterProvider.get().apply {
            init(extractInitParams<GroupTaskInitParams>().task)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTable()
        initActionView()
    }

    override fun showTaskName(name: String) {
        tv_task_name.text = name
    }

    override fun showTaskDescription(description: String) {
        tv_task_description.text = description
    }

    override fun showTableSolutions(solutions: List<UserSolutionModel>) {
        userSolutions = solutions

        val columnItems = mutableListOf(
                GroupTaskColumnHeader("Решение"),
                GroupTaskColumnHeader("Оценка")
        )

        val rowItems: MutableList<GroupTaskRowHeader> = mutableListOf()

        val cellItems: MutableList<List<GroupTaskCell>> = mutableListOf()

        solutions.forEach { solution ->
            rowItems.add(GroupTaskRowHeader(solution))
            cellItems.add(mutableListOf(GroupTaskCell(solution.solution), GroupTaskCell(solution.solution)))
        }

        adapter?.setAllItems(columnItems, rowItems, cellItems)
    }

    override fun downloadFile(fileName: String, fileExtension: String, url: String) {
        val downloadManager = baseActivity.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadUri = Uri.parse(url)

        val request = DownloadManager.Request(downloadUri)

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalFilesDir(baseActivity, Environment.DIRECTORY_DOWNLOADS, fileName + fileExtension)

        downloadManager.enqueue(request)
    }

    override fun showTable() {
        container_table.show()
    }

    override fun hideTable() {
        container_table.visibility = View.GONE
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

    private fun initTable() {
        context?.let { adapter = GroupTaskTableAdapter(it) }
        table_group_task.adapter = adapter
        table_group_task.tableViewListener = object : TableClickListenerAdapter() {
            override fun onCellClicked(cellView: RecyclerView.ViewHolder, column: Int, row: Int) {
                when (column) {
                    GroupTaskTableAdapter.COLUMN_ANSWER -> {
                        val item = (cellView as GroupTaskTableAdapter.CellAnswerViewHolder).cellItem
                        if (item?.solution != null) {
                            presenter.openTaskSolutionScreen(userSolutions[row])
                        }
                    }
                }
            }
        }
    }

    private fun initActionView() {
        btn_task_download.setOnClickListener {
            presenter.dowloadTask()
        }
    }
}