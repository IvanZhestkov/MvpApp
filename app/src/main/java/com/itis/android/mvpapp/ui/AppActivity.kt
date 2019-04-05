package com.itis.android.mvpapp.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.Menu
import android.view.MenuItem
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.itis.android.mvpapp.R
import com.itis.android.mvpapp.ui.base.BaseActivity
import kotlinx.android.synthetic.main.app_activity_container.*
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import javax.inject.Inject
import javax.inject.Provider

class AppActivity : BaseActivity() {

    @InjectPresenter
    lateinit var presenter: AppPresenter

    @Inject
    lateinit var presenterProvider: Provider<AppPresenter>

    @ProvidePresenter
    fun providePresenter(): AppPresenter = presenterProvider.get()

    override val mainContentLayout: Int
        get() = R.layout.app_activity_container

    override val enableBackArrow: Boolean
        get() = false

    private val navigator: Navigator by lazy {
        object : SupportAppNavigator(this, supportFragmentManager, R.id.main_wrapper) {
            override fun setupFragmentTransaction(
                    command: Command?,
                    currentFragment: Fragment?,
                    nextFragment: Fragment?,
                    fragmentTransaction: FragmentTransaction
            ) {
                //fix incorrect order lifecycle callback of MainFlowFragment
                fragmentTransaction.setReorderingAllowed(true)
            }
        }
    }

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
