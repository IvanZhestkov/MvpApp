package com.itis.android.mvpapp.presentation.ui.main.profile

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.itis.android.mvpapp.data.repository.TeacherRepository
import com.itis.android.mvpapp.presentation.base.BasePresenter
import com.itis.android.mvpapp.presentation.rx.transformer.PresentationObservableTransformer
import com.itis.android.mvpapp.router.MainRouter
import javax.inject.Inject
import com.itis.android.mvpapp.presentation.rx.transformer.PresentationSingleTransformer

@InjectViewState
class ProfilePresenter
@Inject constructor() : BasePresenter<ProfileView>() {

    @Inject
    lateinit var teacherRepository: TeacherRepository

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        update()
    }

    fun onLogout() {
        firebaseAuth.signOut()
    }

    fun onRetry() {
        update()
    }

    private fun update() {
        teacherRepository
                .getTeacherInfoObservable()
                .compose(PresentationObservableTransformer())
                .doOnSubscribe {
                    viewState.showProgress()
                    viewState.hideRetry()
                }
                .doAfterTerminate {
                    viewState.hideProgress()
                }
                .subscribe({
                    viewState.showProfile(it)
                }, {
                    viewState.showRetry("Ошибка")
                })
                .disposeWhenDestroy()
    }
}