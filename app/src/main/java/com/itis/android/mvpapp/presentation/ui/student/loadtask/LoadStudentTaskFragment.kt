package com.itis.android.mvpapp.presentation.ui.student.loadtask

import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.itis.android.mvpapp.R
import com.itis.android.mvpapp.presentation.base.BaseFragment
import javax.inject.Inject
import javax.inject.Provider

class LoadStudentTaskFragment: BaseFragment(), LoadStudentTaskView {
    override fun showProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

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