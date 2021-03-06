package com.itis.android.mvpapp.presentation.ui.auth.login

import com.itis.android.mvpapp.presentation.ui.FragmentScope
import com.itis.android.mvpapp.presentation.utils.validation.EmailValidator
import com.itis.android.mvpapp.presentation.utils.validation.PasswordValidator
import dagger.Module
import dagger.Provides

@Module
class LoginModule {

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