package com.itis.android.mvpapp

import com.itis.android.mvpapp.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class MvpApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).create(this)
    }

    override fun onCreate() {
        super.onCreate()
    }
}