package com.itis.android.mvpapp.presentation.ui.student.profile

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.itis.android.mvpapp.R
import com.itis.android.mvpapp.presentation.adapter.DisciplineAdapter
import com.itis.android.mvpapp.presentation.base.BaseFragment
import com.itis.android.mvpapp.presentation.model.StudentInfoModel
import com.itis.android.mvpapp.presentation.model.TeacherInfoModel
import com.itis.android.mvpapp.presentation.ui.auth.AuthActivity
import kotlinx.android.synthetic.main.fragment_student_profile.*
import kotlinx.android.synthetic.main.layout_progress_error.*
import javax.inject.Inject
import javax.inject.Provider

class ProfileFragment : BaseFragment(), ProfileView {

    companion object {
        fun getInstance() = ProfileFragment()
    }

    override val mainContentLayout: Int
        get() = R.layout.fragment_student_profile

    override val enableBackArrow: Boolean
        get() = false

    override val toolbarTitle: Int?
        get() = R.string.screen_name_profile

    override val menu: Int?
        get() = null

    @InjectPresenter
    lateinit var presenter: ProfilePresenter

    @Inject
    lateinit var presenterProvider: Provider<ProfilePresenter>

    @Inject
    lateinit var adapter: DisciplineAdapter

    @ProvidePresenter
    fun providePresenter(): ProfilePresenter {
        return presenterProvider.get()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initActionViews()
    }

    override fun showProfile(studentInfoModel: StudentInfoModel) {
        //createList()
        tv_name.text = getString(
            R.string.test_name,
            studentInfoModel.firstName, studentInfoModel.lastName, studentInfoModel.middleName
        )
        tv_birthday.text = studentInfoModel.birthDate
        tv_email.text = studentInfoModel.email
        tv_phone.text = studentInfoModel.phone
        tv_group.text = studentInfoModel.groupId.toString()
        tv_average_ball.text = studentInfoModel.averageScore.toString()
    }

    private fun initActionViews() {
        btn_logout.setOnClickListener {
            presenter.onLogout()
            val intent = Intent(baseActivity, AuthActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
    }

    override fun showProgress() {
        progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progress.visibility = View.GONE
    }

    override fun showRetry(errorText: String) {
        text_retry.text = errorText
        btn_retry.setOnClickListener { presenter.onRetry() }

        progress_error.visibility = View.VISIBLE
    }

    override fun hideRetry() {
        progress_error.visibility = View.GONE
    }
}