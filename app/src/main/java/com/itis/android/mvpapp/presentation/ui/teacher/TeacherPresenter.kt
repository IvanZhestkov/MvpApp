package com.itis.android.mvpapp.presentation.ui.teacher

import com.arellomobile.mvp.InjectViewState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.itis.android.mvpapp.data.util.CredentialStorage
import com.itis.android.mvpapp.router.MainRouter
import com.itis.android.mvpapp.presentation.base.BasePresenter
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Inject

@InjectViewState
class TeacherPresenter
@Inject constructor() : BasePresenter<TeacherView>() {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var mainRouter: MainRouter

    @Inject
    lateinit var preferences: CredentialStorage

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    @Inject
    lateinit var firebaseDB: FirebaseDatabase

    private var firebaseUser: FirebaseUser? = null

    private lateinit var myRef: DatabaseReference

    private var isProfessor: Boolean = false

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        checkAuth()
    }

    private fun checkAuth() {
        firebaseUser = firebaseAuth.currentUser

        if (firebaseUser == null) {
            viewState.startSignIn()
        } else {
            viewState.signedIn()
        }
    }

    fun setNavigator(navigator: Navigator) {
        navigatorHolder.setNavigator(navigator)
    }

    fun removeNavigator() {
        navigatorHolder.removeNavigator()
    }

    fun onGroups() {
        mainRouter.openGroupListScreen()
    }

    fun onMessages() {
        mainRouter.openDialogListScreen()
    }

    fun onProfile() {
        mainRouter.openProfileScreen()
    }
}