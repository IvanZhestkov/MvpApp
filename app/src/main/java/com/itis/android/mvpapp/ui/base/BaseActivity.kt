package com.itis.android.mvpapp.ui.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import com.arellomobile.mvp.MvpAppCompatActivity
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.app_activity_container.*
import javax.inject.Inject

abstract class BaseActivity : MvpAppCompatActivity(), HasSupportFragmentInjector,
    BaseView {

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    @get:LayoutRes
    protected abstract val mainContentLayout: Int

    protected abstract val enableBackArrow: Boolean

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(mainContentLayout)

        setSupportActionBar(toolbar)
        setBackArrow(enableBackArrow)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentInjector
    }

    override fun showProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun fragmentOnScreen(fragment: BaseFragment) {
        setToolbarTitle(fragment.createToolbarTitle(this))
    }

    private fun setToolbarTitle(title: String) {
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar_title.text = title
    }

    fun setBackArrow(enable: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(enable)
        supportActionBar?.setDisplayShowHomeEnabled(enable)
    }
}