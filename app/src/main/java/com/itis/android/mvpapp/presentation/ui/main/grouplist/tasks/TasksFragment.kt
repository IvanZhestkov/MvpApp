package com.itis.android.mvpapp.presentation.ui.main.grouplist.tasks

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.itis.android.mvpapp.R
import com.itis.android.mvpapp.data.pojo.TaskItem
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
import android.support.v7.widget.RecyclerView
import com.itis.android.mvpapp.presentation.model.TaskModel
import java.lang.IllegalArgumentException

class TasksFragment : BaseFragment(), TasksView {

    companion object {
        private const val KEY_TASKS = "KEY_TASKS"

        fun getInstance(tasks: ArrayList<TaskModel>): TasksFragment {
            return TasksFragment().also {
                it.arguments = Bundle().apply {
                    putSerializable(KEY_TASKS, tasks)
                }
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

    @Inject
    lateinit var adapter: TasksAdapter

    @ProvidePresenter
    fun providePresenter(): TasksPresenter {
        return presenterProvider.get()
    }

    fun getTasks(): ArrayList<TaskModel> = (arguments?.getSerializable(KEY_TASKS) as? ArrayList<TaskModel>)
        ?: throw IllegalArgumentException("tasks is null")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
    }

    private fun initList() {
        adapter.onItemClickListener = { id ->
            baseActivity.toast("TaskItem position: $id", Toast.LENGTH_SHORT)
            presenter.openGroupTaskScreen()
        }

        rv_tasks.adapter = adapter
        rv_tasks.layoutManager = LinearLayoutManager(context)
        rv_tasks.addItemDecoration(DividerItemDecoration(this.context, LinearLayoutManager.VERTICAL))
    }

    override fun setTasks(items: List<TaskModel>) {
        adapter.items = items.toMutableList()
    }
}