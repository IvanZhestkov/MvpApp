package com.itis.android.mvpapp

import com.crashlytics.android.Crashlytics
import com.google.firebase.FirebaseApp
import com.itis.android.mvpapp.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import io.fabric.sdk.android.Fabric

class MvpApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).create(this)
    }

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        Fabric.with(this, Crashlytics())
    }
}