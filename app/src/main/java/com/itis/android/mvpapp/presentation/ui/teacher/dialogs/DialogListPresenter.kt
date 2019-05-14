package com.itis.android.mvpapp.presentation.ui.teacher.dialogs

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.database.SnapshotParser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.itis.android.mvpapp.data.network.pojo.firebase.response.MessageItem
import com.itis.android.mvpapp.data.repository.DialogsRepository
import com.itis.android.mvpapp.data.repository.MessagesRepository
import com.itis.android.mvpapp.data.util.CredentialStorage
import com.itis.android.mvpapp.presentation.base.BasePresenter
import com.itis.android.mvpapp.presentation.model.DialogModel
import com.itis.android.mvpapp.presentation.model.MessageModel
import com.itis.android.mvpapp.presentation.model.MessageModelMapper
import com.itis.android.mvpapp.presentation.rx.transformer.PresentationObservableTransformer
import com.itis.android.mvpapp.presentation.rx.transformer.PresentationSingleTransformer
import com.itis.android.mvpapp.presentation.ui.teacher.dialogs.DialogListView
import com.itis.android.mvpapp.router.MainRouter
import javax.inject.Inject

@InjectViewState
class DialogListPresenter @Inject constructor() : BasePresenter<DialogListView>() {

    @Inject
    lateinit var router: MainRouter

    @Inject
    lateinit var dialogsRepository: DialogsRepository

    @Inject
    lateinit var credentialStorage: CredentialStorage

    @Inject
    lateinit var firebaseDb: FirebaseDatabase

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        update()
    }

    fun onDialog(dialog: DialogModel) {
        router.openDialogScreen(dialog.dialogId.orEmpty(), dialog.username.orEmpty())
    }

    fun onRetry() {
        update()
    }

    private fun update() {
        Log.d("DOALOG TAG", credentialStorage.getUserRole().orEmpty())
        val child = when (credentialStorage.getUserRole().orEmpty()) {
            "PROFESSOR" -> "professor_id"
            else -> "student_id"
        }
        dialogsRepository
                .getDialogs(child)
                .toList()
                .compose(PresentationSingleTransformer())
                .doOnSubscribe {
                    viewState.showProgress()
                    viewState.hideRetry()
                }.doAfterTerminate { viewState.hideProgress() }
                .subscribe({
                    if (it.isEmpty()) {
                        viewState.showEmptyState()
                    } else {
                        viewState.setDialogs(it)
                    }
                }, {
                    viewState.showRetry("Ошибка при загрузке данных")
                })
                .disposeWhenDestroy()
    }
}