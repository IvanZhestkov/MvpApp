package com.itis.android.mvpapp.presentation.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.itis.android.mvpapp.R
import com.itis.android.mvpapp.presentation.base.BaseFragment
import com.itis.android.mvpapp.presentation.ui.teacher.TeacherActivity
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject
import javax.inject.Provider

class LoginFragment : BaseFragment(), LoginView {

    companion object {
        fun newInstance() = LoginFragment()
    }

    override val toolbarTitle: Int? = R.string.screen_name_login

    override val mainContentLayout = R.layout.fragment_login

    override val enableBackArrow = false

    override val menu: Int?
        get() = null

    @InjectPresenter
    lateinit var presenter: LoginPresenter

    @Inject
    lateinit var presenterProvider: Provider<LoginPresenter>

    @ProvidePresenter
    fun providePresenter(): LoginPresenter {
        return presenterProvider.get()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initActionView()
    }

    private fun initActionView() {
        btn_login.setOnClickListener {
            val email = edt_email.text.toString().trim()
            val password = edt_password.text.toString().trim()
            presenter.login(email, password)
        }
        professor_account1.setOnClickListener {
            edt_email.setText(professor_account1.text)
            edt_password.setText("agkpfu123")
        }
        professor_account2.setOnClickListener {
            edt_email.setText(professor_account2.text)
            edt_password.setText("makpfu123")
        }
        student_account.setOnClickListener {
            edt_email.setText(student_account.text)
            edt_password.setText("rbkpfu123")
        }
    }

    override fun showEmailError(hasError: Boolean) {
        if (hasError) {
            ti_email.error = getString(R.string.enter_correct_name)
        } else {
            ti_email.error = null
        }
    }

    override fun showPasswordError(hasError: Boolean) {
        if (hasError) {
            ti_password.error = getString(R.string.enter_correct_password)
        } else {
            ti_password.error = null
        }
    }

    override fun openMainScreen() {
        val intent = Intent(baseActivity, TeacherActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }
}