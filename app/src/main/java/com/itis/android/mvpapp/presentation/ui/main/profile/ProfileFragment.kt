package com.itis.android.mvpapp.presentation.ui.main.profile

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.itis.android.mvpapp.R
import com.itis.android.mvpapp.model.Discipline
import com.itis.android.mvpapp.presentation.adapter.DisciplineAdapter
import com.itis.android.mvpapp.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_teacher_profile.*
import javax.inject.Inject
import javax.inject.Provider

class ProfileFragment : BaseFragment(), ProfileView {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    override val mainContentLayout: Int
        get() = R.layout.fragment_teacher_profile

    override val enableBackArrow: Boolean
        get() = true

    override val toolbarTitle: Int?
        get() = R.string.screen_name_profile

    @InjectPresenter
    lateinit var presenter: ProfilePresenter

    @Inject
    lateinit var presenterProvider: Provider<ProfilePresenter>

    @ProvidePresenter
    fun providePresenter(): ProfilePresenter {
        return presenterProvider.get()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initActionViews()
        initList()
        createList()
    }

    private fun initActionViews() {
        btn_open_tasks.setOnClickListener {
            presenter.openGroupTaskScreen()
        }
    }

    private fun initList() {
        rv_disciplines.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = DisciplineAdapter()
            isNestedScrollingEnabled = false
        }
    }

    private fun createList() {
        val disciplines = ArrayList<Discipline>()
        disciplines.add(Discipline("Информационная безопасность", listOf("11-501 - 11-508", "15-603 - 15-605", "8-305")))
        disciplines.add(Discipline("Управление проектами", listOf("11-501 - 11-508", "15-603 - 15-605", "8-305")))
        disciplines.add(Discipline("База данных", listOf("11-501 - 11-508", "15-603 - 15-605", "8-305")))

        (rv_disciplines.adapter as DisciplineAdapter).addItems(disciplines)
    }
}