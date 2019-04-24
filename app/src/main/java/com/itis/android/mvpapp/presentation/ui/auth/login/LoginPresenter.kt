package com.itis.android.mvpapp.presentation.ui.auth.login

import com.arellomobile.mvp.InjectViewState
import com.google.firebase.auth.FirebaseAuth
import com.itis.android.mvpapp.data.repository.AuthRepository
import com.itis.android.mvpapp.data.util.CredentialStorage
import com.itis.android.mvpapp.presentation.base.BasePresenter
import com.itis.android.mvpapp.presentation.utils.validation.Validator
import com.itis.android.mvpapp.router.AuthRouter
import javax.inject.Inject

@InjectViewState
class LoginPresenter
@Inject constructor() : BasePresenter<LoginView>() {

    @Inject
    lateinit var loginRouter: AuthRouter

    @Inject
    lateinit var authRepository: AuthRepository

    @Inject
    lateinit var preferences: CredentialStorage

    private var auth: FirebaseAuth? = null

    fun login(email: String, password: String) {
        if (!validateForm(email, password)) {
            return
        }

        auth = FirebaseAuth.getInstance()

        viewState.showWaitDialog()
        auth?.signInWithEmailAndPassword(email, password)
                ?.addOnCompleteListener {
                    if (!it.isSuccessful) {
                        viewState.showErrorDialog("Неправильный логин или пароль!")
                    } else {
                        viewState.openMainScreen()
                    }
                    viewState.hideWaitDialog()
                }
    }

    private fun validateForm(email: String, password: String): Boolean {
        return isEmailValid(email) && isPasswordValid(password)
    }

    private fun isEmailValid(email: String): Boolean {
        return if (!Validator.isEmailValid(email)) {
            viewState.showEmailError(true)
            false
        } else {
            viewState.showEmailError(false)
            true
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        return if (!Validator.isPasswordValid(password)) {
            viewState.showPasswordError(true)
            false
        } else {
            viewState.showPasswordError(false)
            true
        }
    }
}