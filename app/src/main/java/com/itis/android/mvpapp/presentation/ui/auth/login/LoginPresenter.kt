package com.itis.android.mvpapp.presentation.ui.auth.login

import com.arellomobile.mvp.InjectViewState
import com.google.firebase.auth.FirebaseAuth
import com.itis.android.mvpapp.data.repository.LoginRepository
import com.itis.android.mvpapp.data.util.CredentialStorage
import com.itis.android.mvpapp.presentation.base.BasePresenter
import com.itis.android.mvpapp.presentation.model.UserRole
import com.itis.android.mvpapp.presentation.rx.transformer.PresentationCompletableTransformer
import com.itis.android.mvpapp.presentation.rx.transformer.PresentationObservableTransformer
import com.itis.android.mvpapp.presentation.rx.transformer.PresentationSingleTransformer
import com.itis.android.mvpapp.presentation.utils.validation.Validator
import com.itis.android.mvpapp.router.AuthRouter
import javax.inject.Inject

@InjectViewState
class LoginPresenter
@Inject constructor() : BasePresenter<LoginView>() {

    @Inject
    lateinit var loginRepository: LoginRepository

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    fun login(email: String, password: String) {
        if (!validateForm(email, password)) {
            return
        }

        loginRepository
                .login(email, password)
                .compose(PresentationSingleTransformer())
                .doOnSubscribe {
                    viewState.showWaitDialog()
                }
                .doAfterTerminate {
                    viewState.hideWaitDialog()
                }
                .subscribe({ user ->
                    when (user.role) {
                        UserRole.PROFESSOR -> viewState.openMainScreen()
                        else -> {
                        }
                    }
                }, {
                    viewState.showErrorDialog("Неправильный логин или пароль!")
                })
                .disposeWhenDestroy()
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