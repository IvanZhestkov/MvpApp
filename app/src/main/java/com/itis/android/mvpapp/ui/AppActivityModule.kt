package com.itis.android.mvpapp.ui

import com.itis.android.mvpapp.di.scope.FragmentScope
import com.itis.android.mvpapp.ui.login.LoginFragment
import com.itis.android.mvpapp.ui.test.TestFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface AppActivityModule {

    @FragmentScope
    @ContributesAndroidInjector
    fun testFragmentInjector(): TestFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [LoginFragmentModule::class])
    fun loginFragmentInjector(): LoginFragment
}