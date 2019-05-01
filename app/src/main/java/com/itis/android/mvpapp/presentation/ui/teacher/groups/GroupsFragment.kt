package com.itis.android.mvpapp.presentation.ui.teacher.groups

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.itis.android.mvpapp.R
import com.itis.android.mvpapp.presentation.adapter.GroupListViewPagerAdapter
import com.itis.android.mvpapp.presentation.base.BaseFragment
import com.itis.android.mvpapp.presentation.model.GroupModel
import kotlinx.android.synthetic.main.fragment_groups.*
import kotlinx.android.synthetic.main.layout_progress_error.*
import javax.inject.Inject
import javax.inject.Provider

class GroupsFragment : BaseFragment(), GroupsView {

    private lateinit var adapter: GroupListViewPagerAdapter

    companion object {
        fun getInstance() = GroupsFragment()
    }

    override val mainContentLayout = R.layout.fragment_groups

    override val enableBackArrow = false

    override val toolbarTitle = R.string.toolbar_task

    override val menu: Int?
        get() = null

    @InjectPresenter
    lateinit var presenter: GroupsPresenter

    @Inject
    lateinit var presenterProvider: Provider<GroupsPresenter>

    @ProvidePresenter
    fun providePresenter(): GroupsPresenter = presenterProvider.get()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initActionView()
    }

    private fun initActionView() {
        btn_add_task.setOnClickListener {
            presenter.openLoadTaskScreen(adapter.groups[view_pager_tasks.currentItem].name ?: "")
        }
    }

    override fun setupViewPager(groups: MutableList<GroupModel>) {
        adapter = GroupListViewPagerAdapter(childFragmentManager)
        adapter.groups = groups

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

    override fun showFAB() {
        btn_add_task.show()
    }

    override fun hideFAB() {
        btn_add_task.hide()
    }
}