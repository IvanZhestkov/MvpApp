package com.itis.android.mvpapp.presentation.ui.teacher.groups.tasks

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.itis.android.mvpapp.R
import com.itis.android.mvpapp.presentation.adapter.TasksAdapter
import com.itis.android.mvpapp.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_tasks.*
import javax.inject.Inject
import javax.inject.Provider
import android.support.v7.widget.DividerItemDecoration
import com.itis.android.mvpapp.presentation.model.GroupModel
import com.itis.android.mvpapp.presentation.model.TaskModel
import java.lang.IllegalArgumentException

class TasksFragment : BaseFragment(), TasksView {

    companion object {
        private const val KEY_GROUP = "KEY_GROUP"

        fun getInstance(group: GroupModel): TasksFragment {
            return TasksFragment().also {
                it.arguments = Bundle().apply {
                    putSerializable(KEY_GROUP, group)
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

    fun getGroup(): GroupModel = (arguments?.getSerializable(KEY_GROUP) as? GroupModel)
        ?: throw IllegalArgumentException("tasks is null")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
    }

    private fun initList() {
        adapter.onItemClickListener = { task ->
            presenter.openGroupTaskScreen(task)
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