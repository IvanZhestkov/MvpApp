package com.itis.android.mvpapp.presentation.ui.main

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.itis.android.mvpapp.data.util.CredentialStorage
import com.itis.android.mvpapp.model.User
import com.itis.android.mvpapp.model.UserRole
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

    private var auth: FirebaseAuth? = null

    private var user: FirebaseUser? = null

    private var database = FirebaseDatabase.getInstance()

    private var myRef = database.getReference("users")

    private var isProfessor: Boolean = false

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        checkAuth()
    }

    private fun checkAuth() {
        auth = FirebaseAuth.getInstance()
        user = auth?.currentUser

        if (user == null) {
            viewState.startSignIn()
        } else {
            checkIsProfessor()
            onGroups()
        }
    }

    private fun checkIsProfessor() {
        user?.uid?.let { myRef.child(it) }
            ?.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val user = dataSnapshot.getValue(User::class.java)
                    Log.d("TAG checkIsProfessor: ", "${user?.role}")
                    if (user?.role == UserRole.PROFESSOR) {
                        isProfessor = true
                    }
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
        if (isProfessor) {
            mainRouter.openProfileScreen()
        }
    }
}