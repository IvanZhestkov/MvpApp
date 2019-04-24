package com.itis.android.mvpapp.presentation.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.MenuItem
import com.arellomobile.mvp.MvpAppCompatActivity
import com.itis.android.mvpapp.presentation.dialog.ErrorDialog
import com.itis.android.mvpapp.presentation.dialog.WaitDialog
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class BaseActivity : MvpAppCompatActivity(), HasSupportFragmentInjector,
        BaseView {

    companion object {
        private const val ERROR_DIALOG_TAG = "ERROR_DIALOG_TAG"
        private const val WAIT_DIALOG_TAG = "WAIT_DIALOG_TAG"
    }

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    @get:LayoutRes
    protected abstract val mainContentLayout: Int

    protected abstract val enableBackArrow: Boolean

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(mainContentLayout)

        setBackArrow(enableBackArrow)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentInjector
    }

    fun setBackArrow(enable: Boolean) {
        supportActionBar?.setDisplayHomeAsUpEnabled(enable)
        supportActionBar?.setDisplayShowHomeEnabled(enable)
    }

    override fun hideWaitDialog() {
        val dialog = supportFragmentManager.findFragmentByTag(WAIT_DIALOG_TAG) as? WaitDialog
        dialog?.dismiss()
    }

    override fun showErrorDialog(text: String) {
        ErrorDialog.getInstance(text).show(supportFragmentManager, ERROR_DIALOG_TAG)
    }

    override fun showErrorDialog(text: Int) {
        showErrorDialog(getString(text))
    }

    override fun showWaitDialog() {
        WaitDialog.getInstance().show(supportFragmentManager, WAIT_DIALOG_TAG)
    }
}