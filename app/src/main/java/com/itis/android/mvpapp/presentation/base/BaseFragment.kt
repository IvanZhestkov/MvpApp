package com.itis.android.mvpapp.presentation.base

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.view.*
import com.arellomobile.mvp.MvpAppCompatFragment
import com.itis.android.mvpapp.presentation.ui.main.MainActivity
import dagger.android.support.AndroidSupportInjection

abstract class BaseFragment : MvpAppCompatFragment(), BaseView {

    @get:LayoutRes
    protected abstract val mainContentLayout: Int

    protected abstract val enableBackArrow: Boolean

    protected val baseActivity
        get() = activity as BaseActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(mainContentLayout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        baseActivity.setBackArrow(enableBackArrow)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun showProgress() {
        (activity as? MainActivity)?.showProgress()
    }

    override fun hideProgress() {
        (activity as? MainActivity)?.hideProgress()
    }

    fun createToolbarTitle(context: Context): String {
        return context.getString(onCreateToolbarTitle())
    }

    @StringRes
    abstract fun onCreateToolbarTitle(): Int

    abstract fun onBackPressed()
}