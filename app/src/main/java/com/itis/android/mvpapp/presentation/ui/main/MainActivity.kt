package com.itis.android.mvpapp.presentation.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.firebase.FirebaseApp
import com.itis.android.mvpapp.R
import com.itis.android.mvpapp.presentation.base.BaseActivity
import com.itis.android.mvpapp.presentation.ui.auth.AuthActivity
import kotlinx.android.synthetic.main.app_activity_container.*
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import javax.inject.Inject
import javax.inject.Provider

class MainActivity : BaseActivity(), MainView {

    @InjectPresenter
    lateinit var presenter: MainPresenter

    @Inject
    lateinit var presenterProvider: Provider<MainPresenter>

    @Inject
    lateinit var navigator: Navigator

    @ProvidePresenter
    fun providePresenter(): MainPresenter = presenterProvider.get()

    override val mainContentLayout: Int
        get() = R.layout.app_activity_container

    override val enableBackArrow: Boolean
        get() = true

    val fragmentContainer: Int = R.id.main_wrapper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        menu_bottom_bar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.main_menu_groups -> presenter.onGroups()
                R.id.main_menu_messages -> presenter.onMessages()
                else -> presenter.onProfile()
            }
            true
        }
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
        val intent = Intent(this, AuthActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    override fun signedIn() {
        presenter.openProfileScreen()
    }
}
