package com.itis.android.mvpapp.presentation.ui.auth

import android.os.Bundle
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.itis.android.mvpapp.R
import com.itis.android.mvpapp.presentation.base.BaseActivity
import ru.terrakok.cicerone.Navigator
import javax.inject.Inject
import javax.inject.Provider

class AuthActivity: BaseActivity(), AuthView {

    @InjectPresenter
    lateinit var presenter: AuthPresenter

    @Inject
    lateinit var presenterProvider: Provider<AuthPresenter>

    @Inject
    lateinit var navigator: Navigator

    @ProvidePresenter
    fun providePresenter(): AuthPresenter = presenterProvider.get()

    override val mainContentLayout: Int
        get() = R.layout.app_activity_container

    override val enableBackArrow: Boolean
        get() = false

    val fragmentContainer: Int =  R.id.main_wrapper

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
}