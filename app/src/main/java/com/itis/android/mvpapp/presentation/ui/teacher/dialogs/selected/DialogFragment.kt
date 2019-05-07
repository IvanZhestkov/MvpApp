package com.itis.android.mvpapp.presentation.ui.teacher.dialogs.selected

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.itis.android.mvpapp.R
import com.itis.android.mvpapp.presentation.adapter.DialogAdapter
import com.itis.android.mvpapp.presentation.base.BaseFragment
import com.itis.android.mvpapp.presentation.model.DialogModel
import com.itis.android.mvpapp.presentation.model.TextMessageModel
import com.itis.android.mvpapp.presentation.ui.teacher.TeacherActivity
import kotlinx.android.synthetic.main.fragment_dialog.*
import javax.inject.Inject
import javax.inject.Provider

class DialogFragment : BaseFragment(), DialogView {

    companion object {
        private const val KEY_DIALOG = "KEY_DIALOG"

        fun getInstance(dialog: DialogModel) =  DialogFragment().also {
            it.arguments = Bundle().apply {
                putSerializable(KEY_DIALOG, dialog)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (baseActivity as? TeacherActivity)?.setBottomBarEnabled(false)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter.items = initMessages()
        rv_messages.adapter = adapter
        rv_messages.layoutManager = LinearLayoutManager(baseActivity)
    }

    private fun initMessages(): List<TextMessageModel> {
        return arrayListOf(
                TextMessageModel("Привет", "11:12", "staff", null),
                TextMessageModel("Привет", "11:12", null, "staff"),
                TextMessageModel(null, "Вчера", "staff", null),
                TextMessageModel("Как дела?", "11:12", "staff", null),
                TextMessageModel("Норм?", "11:12", null, "staff")
        )
    }
}