package com.itis.android.mvpapp.presentation.ui.auth.login

import com.arellomobile.mvp.InjectViewState
import com.itis.android.mvpapp.model.User
import com.itis.android.mvpapp.data.repository.AuthRepository
import com.itis.android.mvpapp.router.MainRouter
import com.itis.android.mvpapp.presentation.base.BasePresenter
import com.itis.android.mvpapp.presentation.ui.auth.AuthQualifier
import com.itis.android.mvpapp.presentation.utils.validation.EmailValidator
import com.itis.android.mvpapp.presentation.utils.validation.PasswordValidator
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class LoginPresenter
@Inject constructor(
) : BasePresenter<LoginView>() {

    @Inject
    @field:AuthQualifier
    lateinit var loginRouter: Router

    @Inject
    lateinit var emailValidator: EmailValidator

    @Inject
    lateinit var passwordValidator: PasswordValidator

    @Inject
    lateinit var authRepository: AuthRepository

    fun login(email: String, password: String) {
        if (!validateForm(email, password)) {
            return
        }
        val user = User(email = email, password = password)

    }

    private fun validateForm(email: String, password: String): Boolean {
        return isEmailValid(email) && isPasswordValid(password)
    }

    private fun isEmailValid(email: String): Boolean {
        return if (!emailValidator.isValid(email)) {
            viewState.showEmailError(true)
            false
        } else {
            viewState.showEmailError(false)
            true
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        return if (!passwordValidator.isValid(password)) {
            viewState.showPasswordError(true)
            false
        } else {
            viewState.showPasswordError(false)
            true
        }
    }
}