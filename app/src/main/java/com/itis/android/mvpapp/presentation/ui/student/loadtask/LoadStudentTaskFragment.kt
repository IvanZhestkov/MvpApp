package com.itis.android.mvpapp.presentation.ui.student.loadtask

import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.itis.android.mvpapp.R
import com.itis.android.mvpapp.presentation.base.BaseFragment
import com.itis.android.mvpapp.presentation.utils.extensions.extractInitParams
import javax.inject.Inject
import javax.inject.Provider

class LoadStudentTaskFragment: BaseFragment(), LoadStudentTaskView {

    override val mainContentLayout = R.layout.fragment_load_task_student

    override val enableBackArrow = true

    override val toolbarTitle = R.string.downloading

    override val menu: Int?
        get() = null

    @InjectPresenter
    lateinit var presenter: LoadStudentTaskPresenter

    @Inject
    lateinit var presenterProvider: Provider<LoadStudentTaskPresenter>

    @ProvidePresenter
    fun providePresenter(): LoadStudentTaskPresenter {
        return presenterProvider.get()
    }



}