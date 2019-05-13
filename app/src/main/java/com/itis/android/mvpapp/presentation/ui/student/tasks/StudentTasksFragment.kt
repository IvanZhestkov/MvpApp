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
import com.itis.android.mvpapp.presentation.ui.teacher.groups.tasks.TasksPresenter
import kotlinx.android.synthetic.main.fragment_tasks.*
import javax.inject.Inject
import javax.inject.Provider

class StudentTasksFragment: BaseFragment(),StudentTasksView {

    override val mainContentLayout = R.layout.fragment_tasks

    override val enableBackArrow = false

    override val toolbarTitle = R.string.toolbar_task

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
    }

    private fun initList() {
        adapter.onItemClickListener = { task ->
            //presenter.openGroupTaskScreen(task)
        }

        rv_tasks.adapter = adapter
        rv_tasks.layoutManager = LinearLayoutManager(context)
        rv_tasks.addItemDecoration(DividerItemDecoration(this.context, LinearLayoutManager.VERTICAL))
    }

    override fun setTasks(items: List<TaskModel>) {
        adapter.items = items.toMutableList()
    }

    override fun hideEmptyState() {
        text_no_available_tasks.visibility = View.GONE
    }

    override fun showEmptyState() {
        text_no_available_tasks.visibility = View.VISIBLE
    }

}