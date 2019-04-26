package com.itis.android.mvpapp.presentation.ui.main.grouplist

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.itis.android.mvpapp.R
import com.itis.android.mvpapp.presentation.model.Group
import com.itis.android.mvpapp.presentation.adapter.GroupListViewPagerAdapter
import com.itis.android.mvpapp.presentation.base.BaseFragment
import com.itis.android.mvpapp.presentation.model.TaskModel
import kotlinx.android.synthetic.main.dialog_error.view.*
import kotlinx.android.synthetic.main.fragment_group_list.*
import kotlinx.android.synthetic.main.layout_progress_error.*
import javax.inject.Inject
import javax.inject.Provider

class GroupListFragment : BaseFragment(), GroupListView {

    private lateinit var adapter: GroupListViewPagerAdapter

    companion object {
        fun getInstance() = GroupListFragment()
    }

    override val mainContentLayout = R.layout.fragment_group_list

    override val enableBackArrow = false

    override val toolbarTitle = R.string.toolbar_task

    override val menu: Int?
        get() = null

    @InjectPresenter
    lateinit var presenter: GroupListPresenter

    @Inject
    lateinit var presenterProvider: Provider<GroupListPresenter>

    @ProvidePresenter
    fun providePresenter(): GroupListPresenter = presenterProvider.get()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initActionView()
    }

    private fun initActionView() {
        btn_add_task.setOnClickListener {
            presenter.openLoadTaskScreen(view_pager_tasks.currentItem)
        }
    }

    override fun setupViewPager(tasks: List<TaskModel>) {
        adapter = GroupListViewPagerAdapter(childFragmentManager)
        adapter.groups = tasks
                .asSequence()
                .map { it.groupNumber ?: "" }
                .filter { it.isNotEmpty() }
                .distinct()
                .sorted()
                .toMutableList()

        adapter.tasks = tasks.toMutableList()

        view_pager_tasks.adapter = adapter
        tabGroup.setupWithViewPager(view_pager_tasks)
    }

    override fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress.visibility = View.GONE
    }

    override fun showRetry(errorText: String) {
        retry.visibility = View.VISIBLE

        text_retry.text = errorText
        btn_retry.setOnClickListener { presenter.onRetry() }
    }

    override fun hideRetry() {
        retry.visibility = View.GONE
    }

    override fun showTabs() {
        tabGroup.visibility = View.VISIBLE
    }

    override fun hideTabs() {
        tabGroup.visibility = View.GONE
    }
}