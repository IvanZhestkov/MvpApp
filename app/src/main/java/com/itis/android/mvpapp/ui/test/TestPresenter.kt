package com.itis.android.mvpapp.ui.test

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.itis.android.mvpapp.router.MainRouter
import com.itis.android.mvpapp.ui.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class TestPresenter
@Inject constructor(
        private val testRouter: MainRouter
) : BasePresenter<TestView>() {

    private var data: String? = ""

    // сохранение данных
    fun init(id: String?) {
        this.data = id
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Log.d("TEST FRAGMENT: ", data)
        testRouter.goBack()
    }
}