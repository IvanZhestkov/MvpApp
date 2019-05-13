package com.itis.android.mvpapp.presentation.ui.teacher.dialogs.selected

import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.WindowManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.itis.android.mvpapp.R
import com.itis.android.mvpapp.presentation.adapter.DialogAdapter
import com.itis.android.mvpapp.presentation.base.BaseFragment
import com.itis.android.mvpapp.presentation.ui.teacher.TeacherActivity
import com.itis.android.mvpapp.presentation.util.extensions.addTextChangedListener
import com.itis.android.mvpapp.presentation.util.itemdecoration.DialogItemDecoration
import kotlinx.android.synthetic.main.fragment_dialog.*
import java.lang.IllegalArgumentException
import javax.inject.Inject
import javax.inject.Provider

class DialogFragment : BaseFragment(), DialogView {

    companion object {
        private const val KEY_DIALOG_ID = "KEY_DIALOG_ID"
        private const val KEY_DIALOG_USERNAME = "KEY_DIALOG_USERNAME"

        fun getInstance(dialogId: String, username: String) = DialogFragment().also {
            it.arguments = Bundle().apply {
                putString(KEY_DIALOG_ID, dialogId)
                putString(KEY_DIALOG_USERNAME, username)
            }
        }
    }

    override val toolbarTitle: Int? = R.string.toolbar_dialog

    override val enableBackArrow: Boolean = true

    override val mainContentLayout: Int = R.layout.fragment_dialog

    override val menu: Int? = null

    @InjectPresenter
    lateinit var presenter: DialogPresenter

    @Inject
    lateinit var presenterProvider: Provider<DialogPresenter>

    @Inject
    lateinit var adapter: DialogAdapter

    @ProvidePresenter
    fun providePresenter(): DialogPresenter {
        return presenterProvider.get().apply {
            init(arguments?.getString(KEY_DIALOG_USERNAME).toString())
        }
    }

    private val itemDecoration = DialogItemDecoration()

    private var adapterObserver: RecyclerView.AdapterDataObserver? = null

    fun getDialogId() = arguments?.getString(KEY_DIALOG_ID)
        ?: throw IllegalArgumentException("dialog id is null")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (baseActivity as? TeacherActivity)?.setBottomBarEnabled(false)
        (baseActivity as? TeacherActivity)?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        initRecyclerView()

        et_message_text.addTextChangedListener { presenter.onMessageChange(it.trim()) }

        btn_send_msg.setOnClickListener { presenter.onAddMessage() }
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext()).also { it.stackFromEnd = true }

        adapterObserver = object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                val friendlyMessageCount = adapter.itemCount
                val lastVisiblePosition = layoutManager.findLastCompletelyVisibleItemPosition()
                // If the recycler view is initially being loaded or the user is at the bottom of the list, scroll
                // to the bottom of the list to show the newly added message.
                if (lastVisiblePosition == -1 || positionStart >= friendlyMessageCount - 1 && lastVisiblePosition == positionStart - 1) {
                    rv_messages.scrollToPosition(positionStart)
                }
            }
        }
        adapterObserver?.let { adapter.registerAdapterDataObserver(it) }


        rv_messages.adapter = adapter.also {
            it.onDataChangeListener = { items ->
                presenter.onDataChange(items)
            }
        }
        rv_messages.layoutManager = layoutManager
        rv_messages.addItemDecoration(itemDecoration)
    }

    override fun onDestroyView() {
        adapterObserver?.let { adapter.unregisterAdapterDataObserver(it) }
        super.onDestroyView()
    }

    override fun startListeningAdapter() {
        adapter.startListening()
    }

    override fun stopListeningAdapter() {
        adapter.stopListening()
    }

    override fun setButtonEnabled(enabled: Boolean) {
        btn_send_msg.isEnabled = enabled
    }

    override fun clearMessageField() {
        et_message_text.text = null
    }

    override fun showToolbarTitle(username: String) {
        setToolbarTitle(username)
    }

    override fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress.visibility = View.GONE
    }

    override fun setItemDecorationItems(items: MutableList<String>) {
        itemDecoration.data = items
    }
}