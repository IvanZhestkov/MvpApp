package com.itis.android.mvpapp.presentation.ui.main.test

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.itis.android.mvpapp.R
import com.itis.android.mvpapp.router.initparams.TestInitParams
import com.itis.android.mvpapp.presentation.base.BaseFragment
import com.itis.android.mvpapp.presentation.utils.extensions.extractInitParams
import com.itis.android.mvpapp.presentation.utils.extensions.putInitParams
import kotlinx.android.synthetic.main.test_fragment.*
import javax.inject.Inject
import javax.inject.Provider

class TestFragment : BaseFragment(), TestView {

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

    override val mainContentLayout: Int
        get() = R.layout.test_fragment

    override val enableBackArrow: Boolean
        get() = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_exit.setOnClickListener { onBackPressed() }
    }

    override fun onCreateToolbarTitle(): Int {
        return R.string.app_name
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
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