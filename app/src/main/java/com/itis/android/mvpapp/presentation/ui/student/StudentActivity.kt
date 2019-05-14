package com.itis.android.mvpapp.presentation.ui.student

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.itis.android.mvpapp.R
import com.itis.android.mvpapp.presentation.base.BaseActivity
import com.itis.android.mvpapp.presentation.ui.auth.AuthActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.terrakok.cicerone.Navigator
import javax.inject.Inject
import javax.inject.Provider

class StudentActivity : BaseActivity(), StudentView {

    @InjectPresenter
    lateinit var presenter: StudentPresenter

    @Inject
    lateinit var presenterProvider: Provider<StudentPresenter>

    @Inject
    lateinit var navigator: Navigator

    @ProvidePresenter
    fun providePresenter(): StudentPresenter = presenterProvider.get()

    override val mainContentLayout: Int
        get() = R.layout.activity_main

    override val enableBackArrow: Boolean
        get() = true

    val fragmentContainer: Int = R.id.main_wrapper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        menu_bottom_bar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.main_menu_groups -> presenter.onTasks()
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

    fun setBottomBarEnabled(isEnabled: Boolean) {
        menu_bottom_bar.visibility = if (isEnabled) View.VISIBLE else View.GONE
    }

}