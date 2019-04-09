package com.itis.android.mvpapp.presentation.base

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenter<V : MvpView> : MvpPresenter<V>() {

    private val destroyDisposable = CompositeDisposable()

    fun Disposable.disposeWhenDestroy() {
        destroyDisposable.add(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (!destroyDisposable.isDisposed) {
            destroyDisposable.dispose()
        }
    }

    open fun onBackPressed() {}
}