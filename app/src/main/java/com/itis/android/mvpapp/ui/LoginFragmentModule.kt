package com.itis.android.mvpapp.ui

import com.itis.android.mvpapp.di.scope.FragmentScope
import com.itis.android.mvpapp.utils.validation.EmailValidator
import com.itis.android.mvpapp.utils.validation.PasswordValidator
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
class LoginFragmentModule {

    @FragmentScope
    @Provides
    fun emailValidator(): EmailValidator {
        return EmailValidator()
    }

    @FragmentScope
    @Provides
    fun passwordValidator(): PasswordValidator {
        return PasswordValidator()
    }
}