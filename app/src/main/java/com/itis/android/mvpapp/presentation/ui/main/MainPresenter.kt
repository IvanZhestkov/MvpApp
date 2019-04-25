package com.itis.android.mvpapp.presentation.ui.main

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.itis.android.mvpapp.data.util.CredentialStorage
import com.itis.android.mvpapp.presentation.model.User
import com.itis.android.mvpapp.presentation.model.UserRole
import com.itis.android.mvpapp.router.MainRouter
import com.itis.android.mvpapp.presentation.base.BasePresenter
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Inject

@InjectViewState
class MainPresenter
@Inject constructor() : BasePresenter<MainView>() {

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
        //myRef = firebaseDB.getReference("users")
        firebaseUser = firebaseAuth.currentUser

        if (firebaseUser == null) {
            viewState.startSignIn()
        } else {
            viewState.signedIn()
        }
    }

    private fun checkIsProfessor() {
        firebaseUser?.uid.let { myRef.child(it.toString()) }
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val user = dataSnapshot.getValue(User::class.java)
                        Log.d("TAG checkIsProfessor: ", "${user?.role}")
                        if (user?.role == UserRole.PROFESSOR) {
                            isProfessor = true
                        }
                        onGroups()
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }
                })
        Log.d("TAG checkIsProfessor: ", "$isProfessor")
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
        mainRouter.openMessagesScreen()
    }

    fun onProfile() {
        mainRouter.openProfileScreen()
    }
}