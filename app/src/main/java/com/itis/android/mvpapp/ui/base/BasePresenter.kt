package com.itis.android.mvpapp.ui.base

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenter<V : MvpView> : MvpPresenter<V>() {
    private val compositeDisposable = CompositeDisposable()

    protected fun unsubscribeOnDestroy(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    open fun onBackPressed() {}
}