package com.itis.android.mvpapp.presentation.ui.main.test

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.itis.android.mvpapp.R
import com.itis.android.mvpapp.router.initparams.TestInitParams
import com.itis.android.mvpapp.presentation.base.BaseFragment
import com.itis.android.mvpapp.presentation.utils.extensions.extractInitParams
import com.itis.android.mvpapp.presentation.utils.extensions.putInitParams
import kotlinx.android.synthetic.main.layout_toolbar.*
import kotlinx.android.synthetic.main.fragment_test.*
import javax.inject.Inject
import javax.inject.Provider

class TestFragment : BaseFragment(), TestView {

    override val toolbarTitle: Int? = R.string.app_name

    override val mainContentLayout: Int = R.layout.fragment_test

    override val enableBackArrow: Boolean = false

    @InjectPresenter
    lateinit var presenter: TestPresenter

    @Inject
    lateinit var presenterProvider: Provider<TestPresenter>

    // получение данных
    @ProvidePresenter
    fun providePresenter(): TestPresenter {
        return presenterProvider.get().apply {
            init(extractInitParams<TestInitParams>().data)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_exit.setOnClickListener {}
    }

    // передача данных
    companion object {
        fun newInstance(initParams: TestInitParams): TestFragment {
            return TestFragment().also {
                it.putInitParams(initParams)
            }
        }
    }
}