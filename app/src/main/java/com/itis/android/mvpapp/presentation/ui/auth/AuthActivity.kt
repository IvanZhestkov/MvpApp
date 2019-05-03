package com.itis.android.mvpapp.presentation.ui.auth

import android.content.Intent
import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.itis.android.mvpapp.R
import com.itis.android.mvpapp.presentation.base.BaseActivity
import com.itis.android.mvpapp.presentation.ui.teacher.TeacherActivity
import ru.terrakok.cicerone.Navigator
import javax.inject.Inject
import javax.inject.Provider

class AuthActivity : BaseActivity(), AuthView {

    @InjectPresenter
    lateinit var presenter: AuthPresenter

    @Inject
    lateinit var presenterProvider: Provider<AuthPresenter>

    @Inject
    lateinit var navigator: Navigator

    @ProvidePresenter
    fun providePresenter(): AuthPresenter = presenterProvider.get()

    override val mainContentLayout: Int
        get() = R.layout.activity_auth

    override val enableBackArrow: Boolean
        get() = false

    val fragmentContainer: Int = R.id.auth_wrapper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResumeFragments() {
        presenter.setNavigator(navigator)
        super.onResumeFragments()
    }

    override fun onPause() {
        presenter.removeNavigator()
        super.onPause()
    }

    override fun startSignIn() {
        presenter.openLoginScreen()
    }

    override fun openTeacherScreen() {
        val intent = Intent(this, TeacherActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    override fun openStudentScreen() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}