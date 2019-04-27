package com.itis.android.mvpapp.presentation.ui.teacher.messages

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.itis.android.mvpapp.R
import com.itis.android.mvpapp.presentation.base.BaseFragment
import javax.inject.Inject
import javax.inject.Provider

class MessagesFragment : BaseFragment(), MessagesView {

    companion object {
        fun getInstance() = MessagesFragment()
    }

    override val mainContentLayout = R.layout.fragment_messages

    override val enableBackArrow = false

    override val toolbarTitle = R.string.birthday_title

    override val menu: Int?
        get() = null

    @InjectPresenter
    lateinit var presenter: MessagesPresenter

    @Inject
    lateinit var presenterProvider: Provider<MessagesPresenter>

    @ProvidePresenter
    fun providePresenter(): MessagesPresenter = presenterProvider.get()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}