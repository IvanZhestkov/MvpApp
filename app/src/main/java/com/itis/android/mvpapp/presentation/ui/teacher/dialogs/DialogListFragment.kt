package com.itis.android.mvpapp.presentation.ui.teacher.dialogs

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.itis.android.mvpapp.R
import com.itis.android.mvpapp.presentation.adapter.DialogListAdapter
import com.itis.android.mvpapp.presentation.base.BaseFragment
import com.itis.android.mvpapp.presentation.model.DialogModel
import kotlinx.android.synthetic.main.fragment_dialog_list.*
import kotlinx.android.synthetic.main.layout_progress_error.*
import javax.inject.Inject
import javax.inject.Provider

class DialogListFragment : BaseFragment(), DialogListView {

    companion object {
        fun getInstance() = DialogListFragment()
    }

    override val enableBackArrow: Boolean = false

    override val mainContentLayout: Int = R.layout.fragment_dialog_list

    override val menu: Int? = null

    override val toolbarTitle: Int? = R.string.toolbar_dialog_list

    @InjectPresenter
    lateinit var presenter: DialogListPresenter

    @Inject
    lateinit var presenterProvider: Provider<DialogListPresenter>

    @Inject
    lateinit var adapter: DialogListAdapter

    @ProvidePresenter
    fun providePresenter(): DialogListPresenter = presenterProvider.get()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter.onChatClick = { presenter.onDialog(it) }
        rv_dialogs.adapter = adapter
        rv_dialogs.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress.visibility = View.GONE
    }

    override fun showRetry(errorText: String) {
        progress_error.visibility = View.VISIBLE

        text_retry.text = errorText
        btn_retry.setOnClickListener { presenter.onRetry() }
    }

    override fun hideRetry() {
        progress_error.visibility = View.GONE
    }

    override fun setDialogs(dialogs: List<DialogModel>) {
        adapter.items = dialogs
    }
}