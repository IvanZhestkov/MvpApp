package com.itis.android.mvpapp.presentation.ui.teacher.profile

import com.arellomobile.mvp.InjectViewState
import com.google.firebase.auth.FirebaseAuth
import com.itis.android.mvpapp.data.repository.DialogsRepository
import com.itis.android.mvpapp.data.repository.TasksRepository
import com.itis.android.mvpapp.data.repository.TeacherRepository
import com.itis.android.mvpapp.data.util.CredentialStorage
import com.itis.android.mvpapp.presentation.base.BasePresenter
import com.itis.android.mvpapp.presentation.model.TaskModel
import com.itis.android.mvpapp.presentation.rx.transformer.PresentationObservableTransformer
import com.itis.android.mvpapp.router.MainRouter
import javax.inject.Inject

@InjectViewState
class ProfilePresenter
@Inject constructor() : BasePresenter<ProfileView>() {

    @Inject
    lateinit var router: MainRouter

    @Inject
    lateinit var teacherRepository: TeacherRepository

    @Inject
    lateinit var dialogsRepository: DialogsRepository

    @Inject
    lateinit var credentialStorage: CredentialStorage

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    private var userId: String? = null

    private var userName: String? = null

    fun init(userId: String) {
        this.userId = userId
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadProfile()
        checkUserRole()
    }

    fun onLogout() {
        credentialStorage.clear()
        firebaseAuth.signOut()
    }

    fun onRetry() {
        loadProfile()
    }

    private fun loadProfile() {
        teacherRepository
                .getTeacherInfoObservable(userId.orEmpty())
                .compose(PresentationObservableTransformer())
                .doOnSubscribe {
                    viewState.showProgress()
                    viewState.hideRetry()
                }
                .doAfterTerminate {
                    viewState.hideProgress()
                }
                .subscribe({
                    userName = "${it.lastName} ${it.firstName}"
                    viewState.showUserPhoto(it.photo.toString())
                    viewState.showProfile(it)
                }, {
                    viewState.showRetry("Ошибка")
                })
                .disposeWhenDestroy()
    }

    private fun checkUserRole() {
        checkStateButtons(credentialStorage.getUserRole())
        enableBackArrow(credentialStorage.getUserRole())
    }

    private fun checkStateButtons(userRole: String?) {
        when(userRole) {
            "STUDENT" -> {
                viewState.showButtonChat()
                viewState.hideButtonLogout()
            }
            else -> {
                viewState.hideButtonChat()
                viewState.showButtonLogout()
            }
        }
    }

    private fun enableBackArrow(userRole: String?) {
        when(userRole) {
            "STUDENT" -> {
                viewState.showBackArrow()
            }
            else -> {
                viewState.hideBackArrow()
            }
        }
    }

    fun onCreateDialog() {
        dialogsRepository
                .createDialog(userId.orEmpty())
                .compose(PresentationObservableTransformer())
                .subscribe({ dialogId ->
                    router.openDialogScreen(dialogId, userName.toString())
                }, {
                    it.printStackTrace()
                })
                .disposeWhenDestroy()
    }
}