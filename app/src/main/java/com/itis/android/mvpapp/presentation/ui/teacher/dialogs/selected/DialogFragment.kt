package com.itis.android.mvpapp.presentation.ui.teacher.dialogs.selected

import android.os.Bundle
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

        fun getInstance(dialogId: String) = DialogFragment().also {
            it.arguments = Bundle().apply {
                putString(KEY_DIALOG_ID, dialogId)
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
    fun providePresenter(): DialogPresenter = presenterProvider.get()

    private val itemDecoration = DialogItemDecoration()

    fun getDialogId() = arguments?.getString(KEY_DIALOG_ID)
        ?: throw IllegalArgumentException("dialog id is null")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (baseActivity as? TeacherActivity)?.setBottomBarEnabled(false)
        (baseActivity as? TeacherActivity)?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        val layoutManager = LinearLayoutManager(requireContext()).also {
            it.stackFromEnd = true
        }
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
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
        })

        rv_messages.adapter = adapter.also {
            it.onDataChangeListener = { items ->
                presenter.onDataChange(items)
            }
        }
        rv_messages.layoutManager = layoutManager
        rv_messages.addItemDecoration(itemDecoration)

        et_message_text.addTextChangedListener { presenter.onMessageChange(it.trim()) }

        btn_send_msg.setOnClickListener { presenter.onAddMessage() }
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