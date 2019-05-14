package com.itis.android.mvpapp.presentation.ui.student.tasks

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.itis.android.mvpapp.R
import com.itis.android.mvpapp.presentation.adapter.TasksAdapter
import com.itis.android.mvpapp.presentation.base.BaseFragment
import com.itis.android.mvpapp.presentation.model.TaskModel
import kotlinx.android.synthetic.main.fragment_student_tasks.*
import com.itis.android.mvpapp.presentation.ui.student.loadtask.TaskModelInitParam
import com.itis.android.mvpapp.presentation.util.extensions.extractInitParams
import kotlinx.android.synthetic.main.fragment_tasks.*
import javax.inject.Inject
import javax.inject.Provider

class StudentTasksFragment : BaseFragment(), StudentTasksView {

    override fun setTasks(items: List<TaskModel>) {
        adapter.items = items.toMutableList()
    }

    override fun showEmptyState() {
        text_no_available_tasks.visibility = View.GONE
    }

    override fun hideEmptyState() {
        text_no_available_tasks.visibility = View.VISIBLE
    }

    override val mainContentLayout = R.layout.fragment_student_tasks

    override val enableBackArrow = false

    override val toolbarTitle = R.string.Tasks_list

    override val menu: Int?
        get() = null

    @InjectPresenter
    lateinit var presenter: StudentTasksPresenter

    @Inject
    lateinit var presenterProvider: Provider<StudentTasksPresenter>

    @Inject
    lateinit var adapter: TasksAdapter

    @ProvidePresenter
    fun providePresenter(): StudentTasksPresenter {
        return presenterProvider.get()
    }

    companion object {
        fun getInstance() = StudentTasksFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
    }

    private fun initList() {
        rv_tasks.adapter = adapter
        rv_tasks.layoutManager = LinearLayoutManager(context)
        rv_tasks.addItemDecoration(DividerItemDecoration(this.context, LinearLayoutManager.VERTICAL))
        adapter.onItemClickListener = { id ->
            presenter.onTaskClick(id)
        }
    }
}