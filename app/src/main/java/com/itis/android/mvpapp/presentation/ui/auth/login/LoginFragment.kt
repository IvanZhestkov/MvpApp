package com.itis.android.mvpapp.presentation.ui.auth.login

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.itis.android.mvpapp.R
import com.itis.android.mvpapp.model.User
import com.itis.android.mvpapp.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.login_fragment.*
import javax.inject.Inject
import javax.inject.Provider

class LoginFragment : BaseFragment(), LoginView {

    @InjectPresenter
    lateinit var presenter: LoginPresenter

    @Inject
    lateinit var presenterProvider: Provider<LoginPresenter>

    @ProvidePresenter
    fun providePresenter(): LoginPresenter {
        return presenterProvider.get()
    }

    override val mainContentLayout: Int
        get() = R.layout.login_fragment

    override val enableBackArrow: Boolean
        get() = false

    override fun onCreateToolbarTitle(): Int {
        return R.string.screen_name_login
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        baseActivity.fragmentOnScreen(this)

        initActionView()
    }

    private fun initActionView() {
        btn_login.setOnClickListener {
            val email = edt_email.text.toString().trim()
            val password = edt_password.text.toString().trim()
            presenter.login(email, password)
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

    override fun startLogin(user: User) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBackPressed() {
    }

    companion object {
        fun newInstance() = LoginFragment()
    }
}