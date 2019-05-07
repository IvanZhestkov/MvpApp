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
import com.itis.android.mvpapp.presentation.model.TextMessageModel
import kotlinx.android.synthetic.main.fragment_dialog_list.*
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
        adapter.onChatClick = { onDialog(it) }
        adapter.items = initDialogs()
        rv_dialogs.adapter = adapter
        rv_dialogs.layoutManager = LinearLayoutManager(baseActivity)
    }

    private fun initDialogs(): List<DialogModel> {
        return arrayListOf(
                DialogModel("Student 1", R.drawable.profile_image,
                        TextMessageModel(text = "lastMessage", to = "staff",
                                dateSend = "12:12")),
                DialogModel("Student 2", R.drawable.profile_image,
                        TextMessageModel(text = "lastMessage", dateSend = "20:23")),
                DialogModel("Student 3", R.drawable.profile_image,
                        TextMessageModel(text = "lastMessage", to = "staff",
                                dateSend = "19:12")),
                DialogModel("Student 4", R.drawable.profile_image,
                        TextMessageModel(text = "lastMessage", dateSend = "15:00")),
                DialogModel("Student 5", R.drawable.profile_image,
                        TextMessageModel(text = "lastMessage", to = "staff",
                                dateSend = "18:12"))
        ).shuffled()
    }

    private fun onDialog(dialog: DialogModel) {
       presenter.onDialog(dialog)
    }
}