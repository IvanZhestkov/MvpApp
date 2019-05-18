package com.itis.android.mvpapp.presentation.ui.student.profile

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.itis.android.mvpapp.R
import com.itis.android.mvpapp.presentation.adapter.DisciplineAdapter
import com.itis.android.mvpapp.presentation.base.BaseFragment
import com.itis.android.mvpapp.presentation.model.StudentInfoModel
import com.itis.android.mvpapp.presentation.ui.auth.AuthActivity
import com.itis.android.mvpapp.presentation.util.extensions.hide
import com.itis.android.mvpapp.presentation.util.extensions.show
import kotlinx.android.synthetic.main.fragment_student_profile.*
import kotlinx.android.synthetic.main.layout_progress_error.*
import javax.inject.Inject
import javax.inject.Provider

class ProfileFragment : BaseFragment(), ProfileView {

    companion object {
        private const val KEY_USER_ID = "KEY_USER_ID"

        fun getInstance(userId: String) = ProfileFragment().also {
            it.arguments = Bundle().apply {
                putString(KEY_USER_ID, userId)
            }
        }
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
        return presenterProvider.get().apply {
            init(arguments?.getString(KEY_USER_ID).toString())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initActionViews()
    }

    override fun showUserPhoto(url: String) {
        Glide.with(baseActivity)
                .load(url)
                .apply(RequestOptions()
                        .placeholder(ColorDrawable(Color.BLACK))
                        .error(ColorDrawable(Color.RED))
                        .centerCrop())
                .into(civ_profile)
    }

    override fun showProfile(studentInfoModel: StudentInfoModel) {
        tv_name.text = getString(
            R.string.test_name,
            studentInfoModel.firstName, studentInfoModel.lastName, studentInfoModel.middleName
        )
        tv_birthday.text = studentInfoModel.birthDate
        tv_email.text = studentInfoModel.email
        tv_phone.text = studentInfoModel.phone
        tv_group.text = studentInfoModel.groupId
        tv_average_ball.text = studentInfoModel.averageScore
    }

    override fun showButtonLogout() {
        btn_logout.show()
    }

    override fun hideButtonLogout() {
        btn_logout.hide()
    }

    override fun showButtonChat() {
        btn_open_chat.show()
    }

    override fun hideButtonChat() {
        btn_open_chat.hide()
    }

    override fun showBackArrow() {
        baseActivity.setBackArrow(true)
    }

    override fun hideBackArrow() {
        baseActivity.setBackArrow(false)
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

    private fun initActionViews() {
        btn_logout.setOnClickListener {
            presenter.onLogout()
            val intent = Intent(baseActivity, AuthActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
        btn_open_chat.setOnClickListener {
            presenter.onCreateDialog()
        }
    }
}