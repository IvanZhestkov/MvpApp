package com.itis.android.mvpapp.presentation.ui.main

import com.arellomobile.mvp.MvpView

interface MainView: MvpView {
    fun startSignIn()

    fun signedIn()
}