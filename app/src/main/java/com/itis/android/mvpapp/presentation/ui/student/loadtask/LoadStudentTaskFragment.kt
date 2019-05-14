package com.itis.android.mvpapp.presentation.ui.student.loadtask

import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.itis.android.mvpapp.R
import com.itis.android.mvpapp.presentation.base.BaseFragment
import com.itis.android.mvpapp.presentation.model.TaskModel
import com.itis.android.mvpapp.presentation.util.extensions.putInitParams
import javax.inject.Inject
import javax.inject.Provider

class LoadStudentTaskFragment : BaseFragment(), LoadStudentTaskView {

    companion object {
        fun getInstance(initParams: TaskModelInitParam): LoadStudentTaskFragment {
            return LoadStudentTaskFragment().also {
                it.putInitParams(initParams)
            }
        }
    }

    override fun showProgress() {
    }

    override fun hideProgress() {
    }

    override val mainContentLayout = R.layout.fragment_student_task

    override val enableBackArrow = true

    override val toolbarTitle = R.string.zadanie

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