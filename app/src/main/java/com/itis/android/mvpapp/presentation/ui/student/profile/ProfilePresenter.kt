package com.itis.android.mvpapp.presentation.ui.student.profile 

import com.arellomobile.mvp.InjectViewState
import com.google.firebase.auth.FirebaseAuth
import com.itis.android.mvpapp.data.repository.StudentRepository
import com.itis.android.mvpapp.data.repository.TeacherRepository
import com.itis.android.mvpapp.data.util.CredentialStorage
import com.itis.android.mvpapp.presentation.base.BasePresenter
import com.itis.android.mvpapp.presentation.rx.transformer.PresentationObservableTransformer
import javax.inject.Inject

@InjectViewState
class ProfilePresenter @Inject constructor() : BasePresenter<ProfileView>() {

    @Inject
    lateinit var studentRepository: StudentRepository

    @Inject
    lateinit var credentialStorage: CredentialStorage

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadProfile()
    }

    fun onLogout() {
        credentialStorage.clear()
        firebaseAuth.signOut()
    }

    fun onRetry() {
        loadProfile()
    }

    private fun loadProfile() {
        studentRepository
            .getStudentInfoObservable()
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