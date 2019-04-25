package com.itis.android.mvpapp.presentation.ui.main.grouplist.tasks

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.itis.android.mvpapp.R
import com.itis.android.mvpapp.presentation.model.Task
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

    override val enableBackArrow = false

    override val toolbarTitle = R.string.toolbar_task

    override val menu: Int?
        get() = null

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
    }

    private fun initList() {
        rv_tasks.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = TasksAdapter { id ->
                run {
                    baseActivity.toast("Task position: $id", Toast.LENGTH_SHORT)
                    presenter.openGroupTaskScreen()
                }
            }
            val dividerItemDecoration = DividerItemDecoration(this.context,
                    (layoutManager as LinearLayoutManager).orientation)
            addItemDecoration(dividerItemDecoration)
        }

        /*rv_tasks.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 || dy < 0 && btn_add_task.isShown)
                    btn_add_task.hide()
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    btn_add_task.show()
                }
                super.onScrollStateChanged(recyclerView, newState)
            }
        })*/
    }

    override fun showTasks() {
        val tasks = ArrayList<Task>()
        tasks.add(Task("16.04", "Управление проектами", "Здесь описание задания"))
        tasks.add(Task("16.04", "Управление проектами", "Здесь описание задания"))
        tasks.add(Task("16.04", "Управление проектами", "Здесь описание задания"))
        tasks.add(Task("16.04", "Управление проектами", "Здесь описание задания"))
        tasks.add(Task("16.04", "Управление проектами", "Здесь описание задания"))
        tasks.add(Task("16.04", "Управление проектами", "Здесь описание задания"))
        tasks.add(Task("16.04", "Управление проектами", "Здесь описание задания"))
        tasks.add(Task("16.04", "Управление проектами", "Здесь описание задания"))
        tasks.add(Task("16.04", "Управление проектами", "Здесь описание задания"))

        (rv_tasks.adapter as TasksAdapter).addItems(tasks)
    }
}