package com.itis.android.mvpapp.presentation.ui.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.itis.android.mvpapp.R
import com.itis.android.mvpapp.presentation.base.BaseActivity
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import javax.inject.Inject
import javax.inject.Provider

class MainActivity : BaseActivity() {

    @InjectPresenter
    lateinit var presenter: MainPresenter

    @Inject
    lateinit var presenterProvider: Provider<MainPresenter>

    @ProvidePresenter
    fun providePresenter(): MainPresenter = presenterProvider.get()

    override val mainContentLayout: Int
        get() = R.layout.app_activity_container

    override val enableBackArrow: Boolean
        get() = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
