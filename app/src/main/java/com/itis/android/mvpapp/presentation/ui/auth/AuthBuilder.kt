package com.itis.android.mvpapp.presentation.ui.auth

import com.itis.android.mvpapp.presentation.ui.auth.login.LoginFragment
import com.itis.android.mvpapp.presentation.ui.auth.login.LoginModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AuthBuilder {

    @ContributesAndroidInjector(modules = [LoginModule::class])
    abstract fun buildLoginFragment(): LoginFragment
}