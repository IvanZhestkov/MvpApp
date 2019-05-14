package com.itis.android.mvpapp.presentation.ui.auth

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.itis.android.mvpapp.data.repository.UserRepository
import com.itis.android.mvpapp.data.util.CredentialStorage
import com.itis.android.mvpapp.presentation.base.BasePresenter
import com.itis.android.mvpapp.presentation.model.UserRole
import com.itis.android.mvpapp.presentation.rx.transformer.PresentationSingleTransformer
import com.itis.android.mvpapp.router.AuthRouter
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Inject

@InjectViewState
class AuthPresenter
@Inject constructor() : BasePresenter<AuthView>() {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var authRouter: AuthRouter

    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var credentialStorage: CredentialStorage

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    private var firebaseUser: FirebaseUser? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        checkAuth()
    }

    private fun checkAuth() {
        firebaseUser = firebaseAuth.currentUser

        if (firebaseUser == null) {
            viewState.startSignIn()
        } else {
            userRepository
                    .getUser()
                    .compose(PresentationSingleTransformer())
                    .doOnSubscribe {
                        viewState.showProgress()
                        viewState.hideRetry()
                    }
                    .doAfterTerminate {
                        viewState.hideProgress()
                    }
                    .subscribe({ user ->
                        credentialStorage.saveUserRole(user.role?.name)
                        when (user.role) {
                            UserRole.PROFESSOR -> viewState.openTeacherScreen()
                            UserRole.STUDENT -> viewState.openStudentScreen()
                            else -> {
                                viewState.startSignIn()
                            }
                        }
                    }, {
                        viewState.showRetry("Ошибка входа. Проверьте подключение к интернету!")
                    })
                    .disposeWhenDestroy()
        }
    }

    fun onRetry() {
        checkAuth()
    }

    fun openLoginScreen() {
        authRouter.openLoginScreen()
    }

    fun setNavigator(navigator: Navigator) {
        navigatorHolder.setNavigator(navigator)
    }

    fun removeNavigator() {
        navigatorHolder.removeNavigator()
    }
}