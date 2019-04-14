package com.itis.android.mvpapp.presentation.base

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.v7.widget.Toolbar
import android.view.*
import android.widget.TextView
import com.arellomobile.mvp.MvpAppCompatFragment
import com.itis.android.mvpapp.R
import com.itis.android.mvpapp.presentation.ui.main.MainActivity
import dagger.android.support.AndroidSupportInjection

abstract class BaseFragment : MvpAppCompatFragment(), BaseView {

    protected abstract val mainContentLayout: Int

    protected abstract val enableBackArrow: Boolean

    protected abstract val toolbarTitle: Int?

    protected val baseActivity
        get() = activity as BaseActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(mainContentLayout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar).also {it.title = getString(R.string.empty)}
        toolbar?.findViewById<TextView>(R.id.toolbar_title)?.text = getString(toolbarTitle ?: R.string.app_name)

        baseActivity.setSupportActionBar(toolbar)
        baseActivity.setBackArrow(enableBackArrow)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun showWaitDialog() {
        (activity as? BaseActivity)?.showWaitDialog()
    }

    override fun hideWaitDialog() {
        (activity as? BaseActivity)?.hideWaitDialog()
    }

    override fun showErrorDialog(text: Int) {
        (activity as? BaseActivity)?.showErrorDialog(text)
    }

    override fun showErrorDialog(text: String) {
        (activity as? BaseActivity)?.showErrorDialog(text)
    }

    override fun hideKeyboard() {
        (activity as? BaseActivity)?.hideKeyboard()
    }
}