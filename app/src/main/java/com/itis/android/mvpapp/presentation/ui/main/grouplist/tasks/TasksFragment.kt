package com.itis.android.mvpapp.presentation.ui.main.grouplist.tasks

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.itis.android.mvpapp.R
import com.itis.android.mvpapp.model.Task
import com.itis.android.mvpapp.presentation.adapter.TasksAdapter
import com.itis.android.mvpapp.presentation.base.BaseFragment
import com.itis.android.mvpapp.presentation.utils.extensions.extractInitParams
import com.itis.android.mvpapp.presentation.utils.extensions.putInitParams
import com.itis.android.mvpapp.presentation.utils.extensions.toast
import com.itis.android.mvpapp.router.initparams.TasksInitParams
import kotlinx.android.synthetic.main.fragment_tasks.*
import javax.inject.Inject
import javax.inject.Provider
import android.support.v7.widget.DividerItemDecoration

class TasksFragment : BaseFragment(), TasksView {

    companion object {
        fun getInstance(initParams: TasksInitParams): TasksFragment {
            return TasksFragment().also {
                it.putInitParams(initParams)
            }
        }
    }

    override val mainContentLayout = R.layout.fragment_tasks

    override val enableBackArrow = true

    override val toolbarTitle = R.string.toolbar_task

    @InjectPresenter
    lateinit var presenter: TasksPresenter

    @Inject
    lateinit var presenterProvider: Provider<TasksPresenter>

    @ProvidePresenter
    fun providePresenter(): TasksPresenter {
        return presenterProvider.get().apply {
            init(extractInitParams<TasksInitParams>().groupId)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
        createList()
    }

    private fun initList() {
        rv_tasks.apply {
            layoutManager = LinearLayoutManager(activity)
            isNestedScrollingEnabled = false
            adapter = TasksAdapter { id ->
                run {
                    baseActivity.toast("Task position: $id", Toast.LENGTH_SHORT)
                }
            }
            val dividerItemDecoration = DividerItemDecoration(this.context,
                    (layoutManager as LinearLayoutManager).orientation)
            addItemDecoration(dividerItemDecoration)
        }
    }

    private fun createList() {
        val tasks = ArrayList<Task>()
        tasks.add(Task("16.04", "Управление проектами", "Здесь описание задания"))
        tasks.add(Task("16.04", "Управление проектами", "Здесь описание задания"))
        tasks.add(Task("16.04", "Управление проектами", "Здесь описание задания"))
        tasks.add(Task("16.04", "Управление проектами", "Здесь описание задания"))
        tasks.add(Task("16.04", "Управление проектами", "Здесь описание задания"))
        tasks.add(Task("16.04", "Управление проектами", "Здесь описание задания"))

        (rv_tasks.adapter as TasksAdapter).addItems(tasks)
    }
}