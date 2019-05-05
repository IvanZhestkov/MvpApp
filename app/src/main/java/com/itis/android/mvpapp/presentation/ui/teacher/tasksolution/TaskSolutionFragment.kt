package com.itis.android.mvpapp.presentation.ui.teacher.tasksolution

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.itis.android.mvpapp.R
import com.itis.android.mvpapp.presentation.base.BaseFragment
import com.itis.android.mvpapp.presentation.model.User
import com.itis.android.mvpapp.presentation.util.extensions.extractInitParams
import com.itis.android.mvpapp.presentation.util.extensions.putInitParams
import com.itis.android.mvpapp.router.initparams.TaskSolutionInitParams
import kotlinx.android.synthetic.main.fragment_task_solution.*
import javax.inject.Inject
import javax.inject.Provider

class TaskSolutionFragment : BaseFragment(), TaskSolutionView, View.OnClickListener {

    companion object {
        fun getInstance(initParams: TaskSolutionInitParams): TaskSolutionFragment {
            return TaskSolutionFragment().also {
                it.putInitParams(initParams)
            }
        }
    }

    override val mainContentLayout = R.layout.fragment_task_solution

    override val enableBackArrow = true

    override val toolbarTitle = R.string.solution

    override val menu: Int?
        get() = null

    @InjectPresenter
    lateinit var presenter: TaskSolutionPresenter

    @Inject
    lateinit var presenterProvider: Provider<TaskSolutionPresenter>

    @ProvidePresenter
    fun providePresenter(): TaskSolutionPresenter {
        return presenterProvider.get().apply {
            init(extractInitParams<TaskSolutionInitParams>().userSolution)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initActionView()
    }

    override fun showStudentName(user: User) {
        tv_task_solution_stud.text = "${user.last_name} ${user.first_name}"
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btn_task_solution_confirm -> {
                presenter.updateSolutionStatus("accepted")
            }
            R.id.btn_task_solution_reject -> {
                presenter.updateSolutionStatus("rejected")
            }
        }
    }

    private fun initActionView() {
        btn_task_solution_confirm.setOnClickListener(this)
        btn_task_solution_reject.setOnClickListener(this)
    }
}