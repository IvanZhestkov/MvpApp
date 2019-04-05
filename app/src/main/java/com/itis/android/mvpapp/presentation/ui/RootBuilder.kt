package com.itis.android.mvpapp.presentation.ui

import com.itis.android.mvpapp.presentation.ui.auth.AuthActivity
import com.itis.android.mvpapp.presentation.ui.auth.AuthModule
import com.itis.android.mvpapp.presentation.ui.main.MainActivity
import com.itis.android.mvpapp.presentation.ui.main.MainModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class RootBuilder {

    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun buildMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [AuthModule::class])
    abstract fun buildAuthActivity(): AuthActivity
}